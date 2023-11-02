package lvum.com;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Downloader {

    public boolean download(String url, Path path, String file) {
        try {
            // Create a URL object.
            URL murl = new URL(url + "/" + file);

            // Open a connection to the URL.
            URLConnection connection = murl.openConnection();

            // Get the input stream for the JAR file.
            InputStream inputStream = connection.getInputStream();

            // Save the JAR file to disk.
            Files.copy(inputStream, Paths.get(path.toString(), file));

            // Close the input stream.
            inputStream.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
