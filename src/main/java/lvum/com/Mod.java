package lvum.com;

import lvum.com.definition.ModDefinition;
import lvum.com.definition.YMLContextDefinition;
import lvum.com.definition.YMLVersionDefinition;

public class Mod implements ModDefinition {
    private String modID;
    private String name;
    private Boolean optional;
    private YMLContextDefinition[] contexts;
    private YMLVersionDefinition[] versions;
    private String version;

    public Mod(ModDefinition modDefinition, String version) {
        this.modID = modDefinition.getModID();
        this.name = modDefinition.getName();
        this.optional = modDefinition.getOptional();
        this.contexts = modDefinition.getContexts();
        this.versions = modDefinition.getVersions();
        this.version = version;
    }

    @Override
    public String getModID() {
        return modID;
    }

    @Override
    public void setModID(String value) {
        modID = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
        name = value;
    }

    @Override
    public Boolean getOptional() {
        return optional;
    }

    @Override
    public void setOptional(Boolean value) {
        optional = value;
    }

    @Override
    public YMLContextDefinition[] getContexts() {
        return contexts;
    }

    @Override
    public void setContexts(YMLContextDefinition[] value) {
        contexts = value;
    }

    @Override
    public YMLVersionDefinition[] getVersions() {
        return versions;
    }

    @Override
    public void setVersions(YMLVersionDefinition[] value) {
        versions = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String value) {
        version = value;
    }
}
