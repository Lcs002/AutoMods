package lvum.com.app.model.mod_reference.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.github.yml.YMLDefinitions;
import lvum.com.app.model.mod_reference.ModReference;
import lvum.com.app.model.mod_reference.ModReferenceRepository;
import lvum.com.app.model.mod_reference.github.yml.YMLModReference;
import lvum.com.app.model.mod_reference.github.yml.YMLReferences;
import lvum.com.utils.parser.YAMLParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GithubModReferenceRepository implements ModReferenceRepository {
    private static final Logger LOGGER = Logger.getLogger(GithubModReferenceRepository.class.getName());
    private static final String YML_REFERENCES = "https://raw.githubusercontent.com/Lcs002/AutoModsDB/main/references.yml";
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private ArrayList<ModReference> references = new ArrayList<>();

    @Override
    public void updateRepository() {

        YMLReferences ymlReferences;

        try {
            // Try to parse the YML and save it to a class
            YAMLParser parser = new YAMLParser();
            ymlReferences = parser.parse(YML_REFERENCES, YMLReferences.class, mapper);
        } catch (MalformedURLException e) {
            LOGGER.log(Level.INFO, "[ERROR]: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "[ERROR]: " + e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "[DATABASE]: Updated");

        // Save the parsed YML to a List of Defined YMLDefinitions
        this.references = new ArrayList<>(List.of(ymlReferences.getMods()));
    }

    @Override
    public ModReference getOne(String modID) {
        Optional<ModReference> modReference = references.stream()
                .filter(x -> x.getModID().equals(modID)).findFirst();
        if (modReference.isEmpty())
            throw new IllegalArgumentException("[ERROR]: " + modID + " not found at " + this.getClass().getName());
        return modReference.orElse(null);
    }

    @Override
    public List<ModReference> getMultiple(List<String> modsID) {
        List<ModReference> modsReference = new ArrayList<>();
        for (String modID : modsID) {
            ModReference modDefinition = getOne(modID);
            if (modDefinition == null) throw new IllegalArgumentException();
            modsReference.add(modDefinition);
        }
        return modsReference;
    }

    @Override
    public List<ModReference> getAll() {
        return (List<ModReference>) references.clone();
    }
}
