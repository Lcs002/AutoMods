package lvum.com.definition;

public class YMLVersionDefinition implements VersionDefinition {
    private String version;
    private String file;
    private YMLDependencyDefinition[] dependencies;

    @Override
    public String getVersion() { return version; }
    @Override
    public void setVersion(String value) { this.version = value; }

    @Override
    public String getFile() { return file; }
    @Override
    public void setFile(String value) { this.file = value; }

    @Override
    public YMLDependencyDefinition[] getDependencies() { return dependencies; }
    @Override
    public void setDependencies(YMLDependencyDefinition[] value) { this.dependencies = value; }
}
