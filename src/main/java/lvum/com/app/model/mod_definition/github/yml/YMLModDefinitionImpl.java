package lvum.com.app.model.mod_definition.github.yml;

import lvum.com.app.model.mod_definition.ModDefinitionContext;

public class YMLModDefinitionImpl implements YMLModDefinition {
    private String modID;
    private String name;
    private String description;
    private Boolean optional;
    private ModDefinitionContext[] modDefinitionContexts;
    private YMLModDefinitionVersion[] YMLModDefinitionVersions;

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
    public ModDefinitionContext[] getContexts() { return modDefinitionContexts; }
    @Override
    public void setContexts(ModDefinitionContext[] value) { this.modDefinitionContexts = value; }

    @Override
    public YMLModDefinitionVersion[] getVersions() { return YMLModDefinitionVersions; }
    @Override
    public void setVersions(YMLModDefinitionVersion[] value) { this.YMLModDefinitionVersions = value; }
}