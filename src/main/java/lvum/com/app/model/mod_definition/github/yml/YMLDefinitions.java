package lvum.com.app.model.mod_definition.github.yml;

public class YMLDefinitions {
    private String version;
    private YMLModDefinition[] mods;

    public YMLModDefinition[] getMods() {
        return mods;
    }
    public void setMods(YMLModDefinition[] value) {
        mods = value;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String value) {
        version = value;
    }
}
