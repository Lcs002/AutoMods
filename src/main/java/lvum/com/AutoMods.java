package lvum.com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lvum.com.definition.YMLDependencyDefinition;
import lvum.com.definition.YMLModDefinition;
import lvum.com.definition.YMLModsDefinitions;
import lvum.com.definition.YMLVersionDefinition;
import lvum.com.reference.YMLModReference;
import lvum.com.reference.YMLModsReferences;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class AutoMods
{
    private static final Logger LOGGER = Logger.getLogger(AutoMods.class.getName());
    private static final String DB_JAR = "https://github.com/Lcs002/AutoModsDB/tree/main/database";
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

    public static void main( String[] args ) {
        int resp = JOptionPane.showConfirmDialog(null, INITIAL_MESSAGE,
                MESSAGE_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (resp == 0) {
            AutoMods app = new AutoMods();
            app.start();
        }
    }


    public AutoMods() {
        LOGGER.addHandler(new ConsoleHandler());
    }


    private void start() {
        LOGGER.log(Level.INFO, "[STARTED] AutoMods");
        Path path = getAppdataPath();
        if (path == null) return;
        YMLModsReferences modsReferences = getModsReferences();
        if (modsReferences == null) return;
        YMLModsDefinitions modsDefinitions = getModsDefinitions();
        if (modsDefinitions == null) return;
        downloadMods(path, modsReferences, modsDefinitions);
        LOGGER.log(Level.INFO, "[ENDED] AutoMods");
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

    private YMLModsReferences getModsReferences() {
        LOGGER.log(Level.INFO, "[STARTED] Load Mods References");
        YMLModsReferences modsReferences = null;

        try {
            // Create a URL object.
            URL url = new URL(YML_REFERENCES);

            // Open a connection to the URL.
            URLConnection connection = url.openConnection();

            // Get the input stream for the file.
            InputStream inputStream = connection.getInputStream();

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();
            modsReferences = mapper.readValue(inputStream, YMLModsReferences.class);

            // Close the input stream.
            inputStream.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LOGGER.log(Level.INFO, "[ENDED] Load Mods References");
        return modsReferences;
    }

    private YMLModsDefinitions getModsDefinitions() {
        LOGGER.log(Level.INFO, "[STARTED] Load Mods Definitions");

        YMLModsDefinitions modsDefinitions;

        try {
            // Create a URL object.
            URL url = new URL(YML_DEFINITIONS);

            // Open a connection to the URL.
            URLConnection connection = url.openConnection();

            // Get the input stream for the file.
            InputStream inputStream = connection.getInputStream();

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();
            modsDefinitions = mapper.readValue(inputStream, YMLModsDefinitions.class);

            // Close the input stream.
            inputStream.close();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "[ENDED] Load Mods Definitions");
        return modsDefinitions;
    }

    private boolean downloadMods(Path path, YMLModsReferences modsReferences,
                                 YMLModsDefinitions modsDefinitions) {
        LOGGER.log(Level.INFO, "[STARTED] Mods Download");

        List<String> errorMods = new ArrayList<>();
        List<String> downloadedMods = new ArrayList<>();

        for (YMLModReference modReference : modsReferences.getMods()) {
            LOGGER.log(Level.INFO, "    Found Mod Reference: " + modReference.getModid());

            if (!downloadedMods.contains(modReference.getModid())) {
                YMLModDefinition modDefinition = getModDefinition(modReference.getModid(), modsDefinitions);
                YMLVersionDefinition versionDefinition = getVersionDefinition(modReference.getVersion(), modDefinition);

                if (versionDefinition != null) {
                    if (download(path, versionDefinition.getFile(), errorMods))
                        downloadedMods.add(modReference.getModid());
                    else errorMods.add(versionDefinition.getFile());

                    if (versionDefinition.getDependencies() != null)
                        for (YMLDependencyDefinition dependencyDefinition : versionDefinition.getDependencies()) {
                            if (!downloadedMods.contains(dependencyDefinition.getModID())) {
                                YMLModDefinition dependencyModDefinition =
                                        getModDefinition(dependencyDefinition.getModID(), modsDefinitions);
                                YMLVersionDefinition dependencyVersionDefinition =
                                        getVersionDefinition(dependencyDefinition.getVersion(), dependencyModDefinition);

                                if (dependencyVersionDefinition != null) {
                                    if(download(path, dependencyVersionDefinition.getFile(), errorMods))
                                        downloadedMods.add(dependencyDefinition.getModID());
                                    else errorMods.add(dependencyDefinition.getModID());
                                }
                            }
                        }
                }
            }
        }

        if (!errorMods.isEmpty()) {
            String s = "";
            for (String errorFile : errorMods) s += "\n    " + errorFile;
            JOptionPane.showConfirmDialog(null, String.format(ERROR_MESSAGE, s,
                    MESSAGE_TITLE, JOptionPane.OK_OPTION));
        }

        String s = "";
        for (String downloadedMod : downloadedMods) s += "\n    " + downloadedMod;
        JOptionPane.showConfirmDialog(null, String.format(SUCCESS_MESSAGE, s,
                MESSAGE_TITLE, JOptionPane.OK_OPTION));

        LOGGER.log(Level.INFO, "[ENDED] Mods Download");
        return true;
    }

    private boolean download(Path path, String file, List<String> errorFiles) {
        LOGGER.log(Level.INFO, "    [STARTED] Downloading File: " + file);
        try {
            // Create a URL object.
            URL url = new URL(DB_JAR + "/" + file);

            // Open a connection to the URL.
            URLConnection connection = url.openConnection();

            // Get the input stream for the JAR file.
            InputStream inputStream = connection.getInputStream();

            // Save the JAR file to disk.
            Files.copy(inputStream, Paths.get(path.toString(), file));

            // Close the input stream.
            inputStream.close();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "    [ERROR] Downloading File: " + file);
            return false;
        }
        LOGGER.log(Level.INFO, "    [ENDED] Downloading File: " + file);
        return true;
    }

    private YMLVersionDefinition getVersionDefinition(String version, YMLModDefinition modDefinition) {
        Optional<YMLVersionDefinition> versionDefinition;
        if (modDefinition == null) return null;
        versionDefinition = Arrays.stream(modDefinition.getVersions())
                    .filter(x -> Objects.equals(x.getVersion(), version)).findFirst();

        if (versionDefinition.isPresent()) LOGGER.log(Level.INFO, "    Version for Definition '"
                + modDefinition.getModID() + "' is: " + versionDefinition.get());
        else LOGGER.log(Level.INFO, "    Version for Definition '"
                + modDefinition.getModID() + "' is: " + null);
        return versionDefinition.orElse(null);
    }

    private YMLModDefinition getModDefinition(String mod, YMLModsDefinitions modsDefinition) {
        Optional<YMLModDefinition> modDefinition = Optional.empty();
        modDefinition = modsDefinition.getMods().stream().filter(x -> Objects.equals(x.getModID(), mod)).findFirst();
        LOGGER.log(Level.INFO, "    Found Definition for modID '" + mod + "'");
        return modDefinition.orElse(null);
    }
}
