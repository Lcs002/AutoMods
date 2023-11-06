package lvum.com.app.model.mod_definition.github.yml;

public class YMLModDefinitionVersionImpl implements YMLModDefinitionVersion {
    private String version;
    private String file;
    private YMLModDefinitionDependency[] dependencies;

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }

    public String getFile() { return file; }
    public void setFile(String value) { this.file = value; }

    public YMLModDefinitionDependency[] getDependencies() { return dependencies; }
    public void setDependencies(YMLModDefinitionDependency[] value) { this.dependencies = value; }
}
