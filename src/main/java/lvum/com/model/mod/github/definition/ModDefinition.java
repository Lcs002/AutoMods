package lvum.com.model.mod.github.definition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.model.mod.github.definition.yml.YMLModDefinition;

@JsonDeserialize(as = YMLModDefinition.class)
public interface ModDefinition {

    String getModID();
    void setModID(String value);

    String getName();
    void setName(String value);

    String getDescription();
    void setDescription(String value);

    Boolean getOptional();
    void setOptional(Boolean value);

    ModContext[] getContexts();
    void setContexts(ModContext[] value);

    ModVersionDefinition[] getVersions();
    void setVersions(ModVersionDefinition[] value);
}
