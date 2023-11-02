package lvum.com.definition;

public class YMLModDefinition implements ModDefinition {
    private String modID;
    private String name;
    private Boolean optional;
    private YMLContextDefinition[] contexts;
    private YMLVersionDefinition[] versions;

    @Override
    public String getModID() { return modID; }
    @Override
    public void setModID(String value) { this.modID = value; }

    @Override
    public String getName() { return name; }
    @Override
    public void setName(String value) { this.name = value; }

    @Override
    public Boolean getOptional() { return optional; }
    @Override
    public void setOptional(Boolean value) { this.optional = value; }

    @Override
    public YMLContextDefinition[] getContexts() { return contexts; }
    @Override
    public void setContexts(YMLContextDefinition[] value) { this.contexts = value; }

    @Override
    public YMLVersionDefinition[] getVersions() { return versions; }
    @Override
    public void setVersions(YMLVersionDefinition[] value) { this.versions = value; }
}