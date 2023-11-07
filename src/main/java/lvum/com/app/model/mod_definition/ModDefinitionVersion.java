package lvum.com.app.model.mod_definition;

public interface ModDefinitionVersion {
    String getVersion();
    void setVersion(String value);

    String getFile();
    void setFile(String value);

    ModDefinitionDependency[] getDependencies();
    void setDependencies(ModDefinitionDependency[] value);
}
