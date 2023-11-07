package lvum.com.app.model.mod_definition.github.yml;

import lvum.com.app.model.mod_definition.ModDefinitionDependency;

public class YMLModDefinitionVersionImpl implements YMLModDefinitionVersion {
    private String version;
    private String file;
    private YMLModDefinitionDependency[] dependencies;

    @Override
    public String getVersion() { return version; }
    @Override
    public void setVersion(String value) { this.version = value; }

    @Override
    public String getFile() { return file; }
    @Override
    public void setFile(String value) { this.file = value; }

    @Override
    public YMLModDefinitionDependency[] getDependencies() { return dependencies; }
    @Override
    @com.fasterxml.jackson.annotation.JsonSetter
    public void setDependencies(YMLModDefinitionDependency[] value) { this.dependencies = value; }
    @Override
    public void setDependencies(ModDefinitionDependency[] value) {
        this.dependencies = (YMLModDefinitionDependency[]) value;
    }
}
