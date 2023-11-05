package lvum.com.model.mod.github.reference.yml;

import lvum.com.model.mod.github.reference.ModReference;

public class YMLModReference implements ModReference {
    private String modID;
    private String version;

    @Override
    public String getModID() { return modID; }
    @Override
    public void setModID(String value) { this.modID = value; }

    @Override
    public String getVersion() { return version; }
    @Override
    public void setVersion(String value) { this.version = value; }
}