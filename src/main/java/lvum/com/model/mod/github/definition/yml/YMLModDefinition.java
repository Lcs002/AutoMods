package lvum.com.model.mod.github.definition.yml;

import lvum.com.model.mod.github.definition.ModContext;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.definition.ModVersionDefinition;

public class YMLModDefinition implements ModDefinition {
    private String modID;
    private String name;
    private String description;
    private Boolean optional;
    private ModContext[] modContexts;
    private ModVersionDefinition[] modVersionDefinitions;

    @Override
    public String getModID() { return modID; }
    @Override
    public void setModID(String value) { this.modID = value; }

    @Override
    public String getName() { return name; }
    @Override
    public void setName(String value) { this.name = value; }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String value) {
        description = value;
    }

    @Override
    public Boolean getOptional() { return optional; }
    @Override
    public void setOptional(Boolean value) { this.optional = value; }

    @Override
    public ModContext[] getContexts() { return modContexts; }
    @Override
    public void setContexts(ModContext[] value) { this.modContexts = value; }

    @Override
    public ModVersionDefinition[] getVersions() { return modVersionDefinitions; }
    @Override
    public void setVersions(ModVersionDefinition[] value) { this.modVersionDefinitions = value; }
}