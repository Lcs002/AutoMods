package lvum.com;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lvum.com.definition.YMLModsDefinitions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class YAMLParser {

    public <T> T parse(String url, Class<T> valueType) throws IOException {
        T result = null;

        // Create a URL object.
        URL murl = new URL(url);

        // Open a connection to the URL.
        URLConnection connection = murl.openConnection();

        // Get the input stream for the file.
        InputStream inputStream = connection.getInputStream();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        result = mapper.readValue(inputStream, valueType);

        // Close the input stream.
        inputStream.close();

        return result;
    }
}
