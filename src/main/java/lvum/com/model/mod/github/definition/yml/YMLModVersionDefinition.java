package lvum.com.model.mod.github.definition.yml;

import lvum.com.model.mod.github.definition.ModDependencyDefinition;
import lvum.com.model.mod.github.definition.ModVersionDefinition;

public class YMLModVersionDefinition implements ModVersionDefinition {
    private String version;
    private String file;
    private ModDependencyDefinition[] dependencies;

    public String getVersion() { return version; }
    public void setVersion(String value) { this.version = value; }

    public String getFile() { return file; }
    public void setFile(String value) { this.file = value; }

    public ModDependencyDefinition[] getDependencies() { return dependencies; }
    public void setDependencies(ModDependencyDefinition[] value) { this.dependencies = value; }
}
