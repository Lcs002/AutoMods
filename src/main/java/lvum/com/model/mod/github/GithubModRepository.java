package lvum.com.model.mod.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lvum.com.model.mod.*;
import lvum.com.model.mod.github.definition.*;
import lvum.com.model.mod.github.reference.GithubModDownloadResult;
import lvum.com.model.mod.github.reference.ModReference;
import lvum.com.model.mod.github.reference.References;
import lvum.com.model.mod.github.utils.YAMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GithubModRepository implements ModRepository {
    private static final Logger LOGGER = Logger.getLogger(GithubModRepository.class.getName());
    private static final String DB_JAR = "https://github.com/Lcs002/AutoModsDB/raw/main/database";
    private static final String YML_DEFINITIONS = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/definitions.yml";
    private static final String YML_REFERENCES = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/references.yml";
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private ArrayList<ModDefinition> definitions = new ArrayList<>();
    private ArrayList<ModReference> references = new ArrayList<>();


    @Override
    public void updateRepository() {
        updateModsDefinitions();
        updateModsReferences();
    }

    @Override
    public ModData getModData(String modID) {
        ModReference modReference = getModReference(modID);
        return getModData(modReference);
    }

    @Override
    public List<ModData> getModsData() {
        return getMainMods();
    }

    @Override
    public ModDownloadResult download(List<ModDownloadTarget> modDownloadTargets, String destFolderPath) {
        List<String> files = new ArrayList<>();
        getFiles(modDownloadTargets, files);
        return downloadAll(files, destFolderPath);
    }

    @Override
    public ModDownloadResult download(ModDownloadTarget modDownloadTarget, String destFolderPath) {
        return download(modDownloadTarget, destFolderPath);
    }

    private void updateModsReferences() {
        LOGGER.log(Level.INFO, "[STARTED] Update Definitions References");
        References references = null;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            references = parser.parse(YML_REFERENCES, References.class, mapper);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Save the parsed YML to a List of Defined Definitions
        LOGGER.log(Level.INFO, "[ENDED] Update Definitions References");
        this.references = new ArrayList<>(List.of(references.getMods()));
    }

    private void updateModsDefinitions() {
        LOGGER.log(Level.INFO, "[STARTED] Update Definitions Definitions");
        Definitions definitions;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            definitions = parser.parse(YML_DEFINITIONS, Definitions.class, mapper);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "[ENDED] Update Definitions Definitions");
        // Save the parsed YML to a List of Defined Definitions
        this.definitions = new ArrayList<>(List.of(definitions.getMods()));
    }

    private ModDefinition getModDefinition(String modID) {
        Optional<ModDefinition> modDefinition = definitions.stream().filter(x -> x.getModID().equals(modID)).findFirst();
        return modDefinition.orElse(null);
    }

    private ModReference getModReference(String modID) {
        Optional<ModReference> modReference = references.stream().filter(x -> x.getModID().equals(modID)).findFirst();
        return modReference.orElse(null);
    }

    private List<ModData> getMainMods() {
        List<ModData> modsData = new ArrayList<>();
        List<String> included = new ArrayList<>();

        // For each ModData referenced...
        for (ModReference modReference : references) {
            // If the mod is not already included...
            if (!included.contains(modReference.getModID())) {
                // Include the mod
                included.add(modReference.getModID());
                // Find the ModData Definition
                ModData modData = getModData(modReference);
                // Add to the list
                modsData.add(modData);
            }
        }
        return modsData;
    }

    private ModData getModData(ModReference modReference) {
        ModDefinition modDefinition = getModDefinition(modReference.getModID());
        ModData modData = new GithubModData(modDefinition, modReference.getVersion());
        String targetVersion;
        if (modData.getTargetVersionDefinition() != null) targetVersion = modData.getTargetVersionDefinition().getVersion();
        else targetVersion = modDefinition.getVersions()[0].getVersion();
        return new GithubModData(modDefinition, targetVersion);
    }

    private ModVersionDefinition getModVersionDefinition(ModData modData) {
        Optional<ModVersionDefinition> modVersionDefinition = Arrays.stream(modData.getVersionDefinitions())
                .filter(x -> x.getVersion().equals(modData.getVersion())).findFirst();
        return modVersionDefinition.orElse(null);
    }

    private void getFiles(List<ModDownloadTarget> modDownloadTargets, List<String> files) {
        for (ModDownloadTarget modDownloadTarget : modDownloadTargets) {
            ModDefinition modDefinition = getModDefinition(modDownloadTarget.getModID());
            ModData modData = new GithubModData(modDefinition, modDownloadTarget.getModVersion());
            ModVersionDefinition modVersionDefinition = getModVersionDefinition(modData);
            if (!files.contains(modVersionDefinition.getFile())) files.add(modVersionDefinition.getFile());
            if (modVersionDefinition.getDependencies() != null)
                getDependencyFiles(modVersionDefinition.getDependencies(), files);
        }
    }

    private void getDependencyFiles(ModDependencyDefinition[] dependencyDefinitions, List<String> files) {
        for (ModDependencyDefinition modDependencyDefinition : dependencyDefinitions) {
            ModDefinition modDefinition = getModDefinition(modDependencyDefinition.getModID());
            ModData modData = new GithubModData(modDefinition, modDependencyDefinition.getVersion());
            ModVersionDefinition modVersionDefinition = getModVersionDefinition(modData);
            if (!files.contains(modVersionDefinition.getFile())) files.add(modVersionDefinition.getFile());
        }
    }

    private ModDownloadResult downloadAll(List<String> files, String path) {
        ModDownloadResult modDownloadResult = new GithubModDownloadResult();
        for (String file : files)
            if (!files.contains(file))  {
                if (download(file, path)) modDownloadResult.addDownload(file);
                else modDownloadResult.addError(file);
            }

        return modDownloadResult;
    }

    private boolean download(String file, String destFolderPath){
        try {
            URL murl = new URL(DB_JAR + "/" + file);
            // Open a connection to the URL.
            URLConnection connection = murl.openConnection();
            // Get the input stream for the JAR file.
            InputStream inputStream = connection.getInputStream();
            // Save the JAR file to disk.
            Files.copy(inputStream, Paths.get(destFolderPath, file));
            // Close the input stream.
            inputStream.close();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
            return false;
        }
        return true;
    }
}
