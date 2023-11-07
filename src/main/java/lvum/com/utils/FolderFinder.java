package lvum.com.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class FolderFinder {
    private static final Logger LOGGER = Logger.getLogger(FolderFinder.class.getName());
    public static String getAppDataMincraftModsFolder() {
        String folderPath = System.getenv("APPDATA");
        Path path = Paths.get(folderPath + "/.minecraft/mods");
        if (!Files.exists(path)) {
            LOGGER.log(INFO, "Could not find folder: " + path);
            throw new IllegalArgumentException();
        }
        return path.toString();
    }
}
