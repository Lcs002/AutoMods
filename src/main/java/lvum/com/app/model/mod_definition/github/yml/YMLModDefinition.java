package lvum.com.app.model.mod_definition.github.yml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.app.model.mod_definition.ModDefinitionVersion;

@JsonDeserialize(as = YMLModDefinitionImpl.class)
public interface YMLModDefinition extends ModDefinition {
    String getModID();
    void setModID(String value);

    String getName();
    void setName(String value);

    String getDescription();
    void setDescription(String value);

    Boolean getOptional();
    void setOptional(Boolean value);

    ModDefinitionContext[] getContexts();
    void setContexts(ModDefinitionContext[] value);

    YMLModDefinitionVersion[] getVersions();
    void setVersions(YMLModDefinitionVersion[] value);
}
