package lvum.com.model.mod.github.definition.yml;

import lvum.com.model.mod.github.definition.ModDependencyDefinition;

public class YMLModDependencyDefinition implements ModDependencyDefinition {
    private String modID;
    private String version;

    public String getModID() { return modID; }
    public void setModID(String value) { this.modID = value; }

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }
}