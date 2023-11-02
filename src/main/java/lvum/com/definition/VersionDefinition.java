package lvum.com.definition;

public interface VersionDefinition {
    String getVersion();
    void setVersion(String value);

    String getFile();
    void setFile(String value);

    YMLDependencyDefinition[] getDependencies();
    void setDependencies(YMLDependencyDefinition[] value);
}
