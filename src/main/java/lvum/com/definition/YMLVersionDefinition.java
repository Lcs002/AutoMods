package lvum.com.definition;

public class YMLVersionDefinition {
    private String version;
    private String file;
    private YMLDependencyDefinition[] dependencies;

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }

    public String getFile() { return file; }
    public void setFile(String value) { this.file = value; }

    public YMLDependencyDefinition[] getDependencies() { return dependencies; }
    public void setDependencies(YMLDependencyDefinition[] value) { this.dependencies = value; }
}
