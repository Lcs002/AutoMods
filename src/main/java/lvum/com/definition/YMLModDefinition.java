package lvum.com.definition;

public class YMLModDefinition {
    private String modID;
    private String name;
    private YMLVersionDefinition[] versions;

    public String getModID() { return modID; }
    public void setModID(String value) { this.modID = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public YMLVersionDefinition[] getVersions() { return versions; }
    public void setVersions(YMLVersionDefinition[] value) { this.versions = value; }
}