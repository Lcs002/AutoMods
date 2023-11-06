package lvum.com.app.model.mod_definition.github.yml;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, defaultImpl = YMLModDefinitionVersionImpl.class)
public interface YMLModDefinitionVersion {
    String getVersion();
    void setVersion(String value);

    String getFile();
    void setFile(String value);

    YMLModDefinitionDependency[] getDependencies();
    void setDependencies(YMLModDefinitionDependency[] value);
}
