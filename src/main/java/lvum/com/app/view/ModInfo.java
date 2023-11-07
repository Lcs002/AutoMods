package lvum.com.app.view;

import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.app.model.mod_definition.ModDefinitionVersion;
import lvum.com.app.model.mod_reference.ModReference;

import java.util.Arrays;
import java.util.Optional;

public class ModInfo implements ModDefinition, ModReference {
    private final ModDefinition modDefinition;
    private String version;

    public ModInfo(ModDefinition modDefinition, String version) {
        this.modDefinition = modDefinition;
        this.version = version;
    }

    public ModDefinitionVersion getTargetVersion() {
        Optional<ModDefinitionVersion> modDefinitionVersion = Arrays.stream(getVersions())
                .filter(x -> x.getVersion().equals(getVersion())).findFirst();
        return modDefinitionVersion.orElse(null);
    }

    @Override
    public String getModID() {
        return modDefinition.getModID();
    }

    @Override
    public void setModID(String value) {
        modDefinition.setModID(value);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String value) {
        version = value;
    }

    @Override
    public String getName() {
        return modDefinition.getName();
    }

    @Override
    public void setName(String value) {
        modDefinition.setName(value);
    }

    @Override
    public String getDescription() {
        return modDefinition.getDescription();
    }

    @Override
    public void setDescription(String value) {
        modDefinition.setDescription(value);
    }

    @Override
    public Boolean getOptional() {
        return modDefinition.getOptional();
    }

    @Override
    public void setOptional(Boolean value) {
        modDefinition.setOptional(value);
    }

    @Override
    public ModDefinitionContext[] getContexts() {
        return modDefinition.getContexts();
    }

    @Override
    public void setContexts(ModDefinitionContext[] value) {
        modDefinition.setContexts(value);
    }

    @Override
    public ModDefinitionVersion[] getVersions() {
        return modDefinition.getVersions();
    }

    @Override
    public void setVersions(ModDefinitionVersion[] value) {
        modDefinition.setVersions(value);
    }
}
