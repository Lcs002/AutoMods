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
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private ArrayList<ModDefinition> definitions = new ArrayList<>();

    @Override
    public void updateRepository() {
        YMLDefinitions ymlDefinitions;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            ymlDefinitions = parser.parse(YML_DEFINITIONS, YMLDefinitions.class, mapper);
        } catch (MalformedURLException e) {
            LOGGER.log(Level.INFO, "[ERROR]: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "[ERROR]: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "[DATABASE] Updated");

        // Save the parsed YML to a List of Defined YMLDefinitions
        this.definitions = new ArrayList<>(List.of(ymlDefinitions.getMods()));
    }

    @Override
    public ModDefinition getOne(String modID) {
        Optional<ModDefinition> modDefinition = definitions.stream()
                .filter(x -> x.getModID().equals(modID)).findFirst();
        return modDefinition.orElse(null);
    }

    @Override
    public List<ModDefinition> getMultiple(List<String> modsID) {
        List<ModDefinition> modsDefinition = new ArrayList<>();
        for (String modID : modsID) {
            ModDefinition modDefinition = getOne(modID);
            if (modDefinition == null) throw new IllegalArgumentException();
            modsDefinition.add(modDefinition);
        }
        return modsDefinition;
    }

    @Override
    public List<ModDefinition> getAll() {
        return (List<ModDefinition>) definitions.clone();
    }

}
