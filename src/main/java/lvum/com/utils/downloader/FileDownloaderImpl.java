package lvum.com.utils.downloader;

import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.model.mod_definition.github.yml.YMLModDefinitionVersion;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDownloaderImpl implements FileDownloader {
    private static final Logger LOGGER = Logger.getLogger(FileDownloaderImpl.class.getName());
    private String DB_URL = "https://github.com/Lcs002/AutoModsDB/raw/main/database";


    @Override
    public FileDownloadResult download(String file, String destination, boolean cleanup) {
        if (cleanup) cleanup(destination);
        return download(file, destination);
    }

    @Override
    public FileDownloadResult download(List<String> files, String destination, boolean cleanup) {
        if (cleanup) cleanup(destination);

        FileDownloadResult result = new FileDownloadResultImpl();
        for (String file : files) {
            FileDownloadResult r = download(file, destination);
            result.getModsDownloaded().addAll(r.getModsDownloaded());
            result.getModsWithErrors().addAll(r.getModsWithErrors());
        }
        return result;
    }

    private FileDownloadResult download(String file, String destination) {
        FileDownloadResult result = new FileDownloadResultImpl();
        try {
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
        } catch (MalformedURLException e) {
            LOGGER.info("[ERROR]: " + e);
            result.addError(file);
            return result;
        } catch (IOException e) {
            LOGGER.info("[ERROR]: " + e);
            result.addError(file);
            return result;
        }
        LOGGER.info("[DOWNLOADED]: " + file);
        result.addDownload(file);
        return result;
    }

    private void cleanup(String destination) {
        File folder = new File(destination);
        File[] files = folder.listFiles(new JARFilter());
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if (files[i].delete()) LOGGER.log(Level.INFO, "[DELETION]: " + fileName);
            else LOGGER.log(Level.INFO, "[ERROR]: Could not delete file '" + fileName + "'");
        }
    }

    private class JARFilter implements java.io.FileFilter {
        @Override
        public boolean accept(File f) {
            return f.getName().endsWith(".jar");
        }
    }
}
