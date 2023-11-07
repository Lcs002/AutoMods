package lvum.com.app.model.mod_definition;

public interface ModDefinition {
    String getModID();
    void setModID(String value);

    String getName();
    void setName(String value);

    String getDescription();
    void setDescription(String value);

    Boolean getOptional();
    void setOptional(Boolean value);

    ModDefinitionContext[] getContexts();
    void setContexts(ModDefinitionContext[] value);

    ModDefinitionVersion[] getVersions();
    void setVersions(ModDefinitionVersion[] value);
}
