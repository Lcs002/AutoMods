package lvum.com.model.mod.github.definition;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lvum.com.model.mod.github.definition.yml.YMLModVersionDefinition;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, defaultImpl = YMLModVersionDefinition.class)
public interface ModVersionDefinition {
    String getVersion();
    void setVersion(String value);

    String getFile();
    void setFile(String value);

    ModDependencyDefinition[] getDependencies();
    void setDependencies(ModDependencyDefinition[] value);
}
