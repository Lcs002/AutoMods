package lvum.com;

import lvum.com.definition.*;
import lvum.com.reference.ModReference;
import lvum.com.reference.YMLModsReferences;
import lvum.com.ui.AutoModsUI;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class AutoMods
{
    private static final Logger LOGGER = Logger.getLogger(AutoMods.class.getName());
    private static final String DB_JAR = "https://github.com/Lcs002/AutoModsDB/raw/main/database";
    private static final String YML_DEFINITIONS = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/definitions.yml";
    private static final String YML_REFERENCES = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/references.yml";
    private static final String MESSAGE_TITLE = "AutoMods - CaposMods";
    private static final String INITIAL_MESSAGE =
            """
            Do you wish do download the latest necessary Mods for 'caposingenieros.aternos.me:23447'?
            All files will be downloaded to Appdata/Roaming/.minecraft/mods.
            No local files will be removed, moved or changed during this process.
            """;

    private static final String SUCCESS_MESSAGE =
            """
            Download Complete!
            The following Mods were installed:%s       
            """;
    private static final String ERROR_MESSAGE =
            """
            The following Mods couldn't be downloaded:%s
            Make sure they are not already on 'mods' folder.
            """;
    private final AutoModsUI autoModsUI;

    private ModDefinition[] modsDefinitions;
    private ModReference[] modsReferences;


    public static void main( String[] args ) {
        AutoMods autoMods = new AutoMods();
        autoMods.start();
    }


    public AutoMods() {
        autoModsUI = new AutoModsUI(this);
    }

    public void start() {
        autoModsUI.setVisible(true);
    }

    private Path getAppdataPath() {
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

    private void updateModsReferences() {
        LOGGER.log(Level.INFO, "[STARTED] Load Mods References");
        YMLModsReferences ymlModsReferences = null;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            ymlModsReferences = parser.parse(YML_REFERENCES, YMLModsReferences.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Save the parsed YML to a List of Defined Mods
        LOGGER.log(Level.INFO, "[ENDED] Load Mods References");
        modsReferences = ymlModsReferences.getMods();
    }

    private void updateModsDefinitions() {
        LOGGER.log(Level.INFO, "[STARTED] Load Mods Definitions");

        YMLModsDefinitions ymlModsDefinitions;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            ymlModsDefinitions = parser.parse(YML_DEFINITIONS, YMLModsDefinitions.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "[ENDED] Load Mods Definitions");
        // Save the parsed YML to a List of Defined Mods
        modsDefinitions = ymlModsDefinitions.getMods();
    }

    private List<Mod> getMods(ModDefinition[] modsDefinitions,
                          ModReference[] modsReferences) {
        List<Mod> mods = new ArrayList<>();
        List<String> included = new ArrayList<>();

        // For each Mod referenced...
        for (ModReference modReference : modsReferences) {
            // If the mod is not already included...
            if (!included.contains(modReference.getModid())) {
                // Include the mod
                included.add(modReference.getModid());
                // Find the Mod Definition
                ModDefinition modDefinition = getModDefinition(modReference.getModid(), modsDefinitions);
                // TODO Handle null modDefinition
                // Get its target version from the Reference
                String targetVersion;
                if (modReference.getVersion() != null) targetVersion = modReference.getVersion();
                // TODO Handle null versions
                // Or get the latest version
                else targetVersion = Arrays.stream(modDefinition.getVersions()).findFirst().get().getVersion();
                // Add the definition and target version of the mod
                mods.add(new Mod(modDefinition, targetVersion));
            }
        }
        return mods;
    }

    private List<Mod> getModsDependencies(List<Mod> mods) {
        List<Mod> dependencyMods = new ArrayList<>();
        List<String> included = new ArrayList<>();

        // For each mod
        for (Mod mod : mods) {
            // Get the target version definition of the mod (the version that will be downloaded)
            VersionDefinition versionDefinition = getVersionDefinition(mod.getVersion(), mod);
            // If the version does not have any dependencies, go to next mod
            if (versionDefinition.getDependencies() != null)
                // For each Mod Dependency...
                for (DependencyDefinition modDependency : versionDefinition.getDependencies())
                    // If the dependency is not already included for download...
                    if (!included.contains(modDependency.getModID())) {
                        // Add the dependency in the included list
                        included.add(modDependency.getModID());
                        // Find the Dependency Definition
                        ModDefinition dependencyDefinition =
                                getModDefinition(modDependency.getModID(), modsDefinitions);
                        // TODO Handle null modDefinition
                        // Get the Dependency Version from the Mod
                        String targetVersion = modDependency.getVersion();
                        // Add the Dependency Definition and its target version
                        // TODO Hanle null versions
                        dependencyMods.add(new Mod(dependencyDefinition, targetVersion));
                    }
        }
        return dependencyMods;
    }

    private DownloadResult downloadMods(Path path, List<Mod> mods) {
        LOGGER.log(Level.INFO, "[STARTED] Mods Download");

        DownloadResult result = new DownloadResult();

        for (Mod mod : mods) {
            LOGGER.log(Level.INFO, "    [1/3] Found Mod: " + mod.getModID());
            if (!result.getModsDownloaded().contains(mod.getModID())) {
                VersionDefinition versionDefinition = getVersionDefinition(mod.getVersion(), mod);

                if (versionDefinition != null) {
                    LOGGER.log(Level.INFO, "    [2/3] Downloading Mod: " + mod.getModID());
                    if (download(path, versionDefinition.getFile())) {
                        result.addDownload(mod.getModID());
                        LOGGER.log(Level.INFO, "    [3/3] Downloaded Mod: " + mod.getModID());
                    }
                    else result.addError(versionDefinition.getFile());
                }
            }
        }
        LOGGER.log(Level.INFO, "[ENDED] Mods Download");
        return result;
    }

    private boolean download(Path path, String file) {
        Downloader downloader = new Downloader();
        return downloader.download(DB_JAR, path, file);
    }

    private VersionDefinition getVersionDefinition(String version, ModDefinition modDefinition) {
        Optional<YMLVersionDefinition> versionDefinition;
        if (modDefinition == null) return null;
        if (version != null) versionDefinition = Arrays.stream(modDefinition.getVersions())
                    .filter(x -> Objects.equals(x.getVersion(), version)).findFirst();
        else versionDefinition = Arrays.stream(modDefinition.getVersions()).findFirst();

        if (versionDefinition.isPresent()) LOGGER.log(Level.INFO, "    Version for Definition '"
                + modDefinition.getModID() + "' is: " + versionDefinition.get());
        else LOGGER.log(Level.INFO, "    Version for Definition '"
                + modDefinition.getModID() + "' is: " + null);
        return versionDefinition.orElse(null);
    }

    private ModDefinition getModDefinition(String mod, ModDefinition[] modsDefinition) {
        Optional<ModDefinition> modDefinition = Optional.empty();
        modDefinition = Arrays.stream(modsDefinition).filter(x -> Objects.equals(x.getModID(), mod)).findFirst();
        LOGGER.log(Level.INFO, "    Found Definition for modID '" + mod + "'");
        return modDefinition.orElse(null);
    }

    public void updateMods() {
        updateModsReferences();
        updateModsDefinitions();
        List<Mod> mods = getMods(modsDefinitions, modsReferences);
        autoModsUI.updateMods(mods);
    }

    public void downloadMods(List<Mod> mods) {
        Path path = getAppdataPath();
        if (path == null) return;
        List<Mod> dependencies = getModsDependencies(mods);
        mods.addAll(dependencies);
        downloadMods(path, mods);
    }
}
