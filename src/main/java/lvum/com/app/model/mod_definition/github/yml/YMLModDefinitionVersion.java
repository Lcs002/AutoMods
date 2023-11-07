package lvum.com.app.model.mod_definition.github.yml;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lvum.com.app.model.mod_definition.ModDefinitionVersion;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, defaultImpl = YMLModDefinitionVersionImpl.class)
public interface YMLModDefinitionVersion extends ModDefinitionVersion {
    String getVersion();
    void setVersion(String value);

    String getFile();
    void setFile(String value);

    YMLModDefinitionDependency[] getDependencies();
    void setDependencies(YMLModDefinitionDependency[] value);
}
