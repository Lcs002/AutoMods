package lvum.com.app.model.mod_definition.github.yml;

public class YMLModDefinitionDependencyImpl implements YMLModDefinitionDependency {
    private String modID;
    private String version;

    public String getModID() { return modID; }
    public void setModID(String value) { this.modID = value; }

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }
}