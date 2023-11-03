package lvum.com.model.mod.github.definition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.model.mod.github.definition.yml.YMLModDependencyDefinition;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, defaultImpl = YMLModDependencyDefinition.class)
@JsonDeserialize(as = YMLModDependencyDefinition.class)
public interface ModDependencyDefinition {
    String getModID();
    void setModID(String value);

    String getVersion();
    void setVersion(String value);
}
