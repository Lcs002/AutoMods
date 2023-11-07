package lvum.com.app.model.mod_definition.github.yml;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lvum.com.app.model.mod_definition.ModDefinitionDependency;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, defaultImpl = YMLModDefinitionDependencyImpl.class)
@JsonDeserialize(as = YMLModDefinitionDependencyImpl.class)
public interface YMLModDefinitionDependency extends ModDefinitionDependency {
    String getModID();
    void setModID(String value);

    String getVersion();
    void setVersion(String value);
}
