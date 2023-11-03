package lvum.com.model.mod.github.definition;

public class Definitions {
    private String version;
    private ModDefinition[] mods;

    public ModDefinition[] getMods() {
        return mods;
    }
    public void setMods(ModDefinition[] value) {
        mods = value;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String value) {
        version = value;
    }
}
