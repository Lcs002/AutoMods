package lvum.com.model.mod.github.reference.yml;

import lvum.com.model.mod.github.reference.ModReference;

public class YMLModReference implements ModReference {
    private String modid;
    private String version;

    @Override
    public String getModid() { return modid; }
    @Override
    public void setModid(String value) { this.modid = value; }

    @Override
    public String getVersion() { return version; }
    @Override
    public void setVersion(String value) { this.version = value; }
}