package lvum.com.utils.downloader;

import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.model.mod_definition.github.yml.YMLModDefinitionVersion;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDownloaderImpl extends FileDownloader {
    private static final Logger LOGGER = Logger.getLogger(FileDownloaderImpl.class.getName());
    private String DB_URL = "https://github.com/Lcs002/AutoModsDB/raw/main/database";

    public FileDownloaderImpl(ModDefinitionRepository repository) {
        super(repository);
    }

    @Override
    public FileDownloadResult download(String modID, String version, String destination, boolean cleanup) {
        FileDownloadResult result = new FileDownloadResultImpl();
        ModDefinition ModDefinition = repository.getModData(modID);
        try {
            // Version to download
            YMLModDefinitionVersion versionDefinition = ModDefinition.getTargetVersionDefinition();
            // File to download
            String file = versionDefinition.getFile();
            // Get url to file
            URL murl = new URL(DB_URL + "/" + file);
            // Open a connection to the URL.
            URLConnection connection = murl.openConnection();
            // Get the input stream for the JAR file.
            InputStream inputStream = connection.getInputStream();
            // Save the JAR file to disk.
            Files.copy(inputStream, Paths.get(destination, file));
            // Close the input stream.
            inputStream.close();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
            result.addError(modID);
            return result;
        }
        result.addDownload(modID);
        return result;
    }

    @Override
    public FileDownloadResult download(List<String> modsID, String destination, boolean cleanup) {
        FileDownloadResult result = new FileDownloadResultImpl();
        for (String modID : modsID) {
            FileDownloadResult r = download(modID, destination, cleanup);
            result.getModsDownloaded().addAll(r.getModsDownloaded());
            result.getModsWithErrors().addAll(r.getModsWithErrors());
        }
        return result;
    }
}
