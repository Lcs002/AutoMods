package lvum.com.model.mod.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lvum.com.model.mod.ModDownloadResult;
import lvum.com.model.mod.ModRepository;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.definition.Definitions;
import lvum.com.model.mod.github.reference.ModReference;
import lvum.com.model.mod.github.reference.References;
import lvum.com.utils.YAMLParser;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
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
        this.references = new ArrayList<>(List.of((ModReference[]) references.getMods()));
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
        this.definitions = new ArrayList<>(List.of((ModDefinition[]) definitions.getMods()));
    }


    @Override
    public List<ModDefinition> getDefinitions() {
        return (List<ModDefinition>) definitions.clone();
    }

    @Override
    public List<ModReference> getReferences() {
        return (List<ModReference>) references.clone();
    }

    @Override
    public ModDownloadResult download(TargetedMod targetedMod) {
        ModDownloadResult modDownloadResult = new ModDownloadResult();
        try {
            // Create a URL object.
            URL murl = new URL(DB_JAR + "/" + targetedMod.getTargetVersion().getFile());

            // Open a connection to the URL.
            URLConnection connection = murl.openConnection();

            // Get the input stream for the JAR file.
            InputStream inputStream = connection.getInputStream();

            // Get path to mods folder
            Path modsFolder = getModsFolder();

            // Save the JAR file to disk.
            Files.copy(inputStream, Paths.get(modsFolder.toString(), targetedMod.getTargetVersion().getFile()));

            // Close the input stream.
            inputStream.close();
        } catch (Exception e) {
            modDownloadResult.addError(targetedMod.getModID());
            return modDownloadResult;
        }
        modDownloadResult.addDownload(targetedMod.getModID());
        return modDownloadResult;
    }

    @Override
    public ModDownloadResult download(List<TargetedMod> targetedMods) {
        ModDownloadResult modDownloadResult = new ModDownloadResult();
        for (TargetedMod targetedMod : targetedMods) {
            ModDownloadResult result = download(targetedMod);
            modDownloadResult.getModsDownloaded().addAll(result.getModsDownloaded());
            modDownloadResult.getModsWithErrors().addAll(result.getModsWithErrors());
        }
        return modDownloadResult;
    }

    private Path getModsFolder() {
        LOGGER.log(Level.INFO, "[STARTED] Find Appdata Folder");
        String folderPath = System.getenv("APPDATA");
        Path path = Paths.get(folderPath + "/.minecraft/mods");
        if (!Files.exists(path)) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "ERROR: Folder '" + path + "' not found",
                    "Folder not found", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        LOGGER.log(Level.INFO, "[ENDED] Find Appdata Folder");
        return path;
    }
}
