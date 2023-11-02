package lvum.com.definition;

public interface ModDefinition {

    String getModID();
    void setModID(String value);

    String getName();
    void setName(String value);

    Boolean getOptional();
    void setOptional(Boolean value);

    YMLContextDefinition[] getContexts();
    void setContexts(YMLContextDefinition[] value);

    YMLVersionDefinition[] getVersions();
    void setVersions(YMLVersionDefinition[] value);
}
