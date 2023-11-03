package lvum.com.model.mod.github;

import lvum.com.model.mod.github.definition.ModContext;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.definition.ModVersionDefinition;

public class TargetedMod implements ModDefinition {
    private String modID;
    private String name;
    private String description;
    private Boolean optional;
    private ModContext[] modContexts;
    private ModVersionDefinition[] modVersionDefinitions;
    private ModVersionDefinition targetModVersionDefinition;

    public TargetedMod(ModDefinition modDefinition, ModVersionDefinition targetModVersionDefinition) {
        this.modID = modDefinition.getModID();
        this.name = modDefinition.getName();
        this.optional = modDefinition.getOptional();
        this.modContexts = modDefinition.getContexts();
        this.modVersionDefinitions = modDefinition.getVersions();
        this.targetModVersionDefinition = targetModVersionDefinition;
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
    public String getDescription() {
        return description;
    }
    @Override
    public void setDescription(String value) {
        description = value;
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
    public ModContext[] getContexts() {
        return modContexts;
    }
    @Override
    public void setContexts(ModContext[] value) {
        modContexts = value;
    }

    @Override
    public ModVersionDefinition[] getVersions() {
        return modVersionDefinitions;
    }
    @Override
    public void setVersions(ModVersionDefinition[] value) {
        modVersionDefinitions = value;
    }

    public ModVersionDefinition getTargetVersion() {
        return targetModVersionDefinition;
    }
    public void setTargetVersion(ModVersionDefinition value) {
        targetModVersionDefinition = value;
    }
}
