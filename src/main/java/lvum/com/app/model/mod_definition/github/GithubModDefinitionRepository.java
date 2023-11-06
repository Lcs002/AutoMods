package lvum.com.app.model.mod_definition.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.utils.downloader.FileDownloader;
import lvum.com.app.model.mod_definition.github.yml.YMLDefinitions;
import lvum.com.app.model.mod_definition.github.yml.YMLModDefinition;
import lvum.com.utils.parser.YAMLParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GithubModDefinitionRepository implements ModDefinitionRepository {
    private static final Logger LOGGER = Logger.getLogger(GithubModDefinitionRepository.class.getName());
    private static final String YML_DEFINITIONS = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/definitions.yml";
    // private static final String YML_REFERENCES = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/references.yml";
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private ArrayList<YMLModDefinition> definitions = new ArrayList<>();

    @Override
    public void updateRepository() {
        LOGGER.log(Level.INFO, "[STARTED] Update YMLDefinitions YMLDefinitions");
        YMLDefinitions YMLDefinitions;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            YMLDefinitions = parser.parse(YML_DEFINITIONS, YMLDefinitions.class, mapper);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "[ENDED] Update YMLDefinitions YMLDefinitions");
        // Save the parsed YML to a List of Defined YMLDefinitions
        this.definitions = new ArrayList<>(List.of(YMLDefinitions.getMods()));
    }

    @Override
    public ModDefinition getModData(String modID) {
        return null;
    }

    @Override
    public List<ModDefinition> getModsData(List<String> modsID) {
        return null;
    }

    @Override
    public FileDownloader getDownloader() {
        return null;
    }
}
