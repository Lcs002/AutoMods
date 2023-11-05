package lvum.com.model.mod.github;

import lvum.com.model.mod.ModData;
import lvum.com.model.mod.github.definition.ModContext;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.definition.ModVersionDefinition;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class GithubModData implements ModData {
    private String modID;
    private String name;
    private String description;
    private Boolean optional;
    private ModContext[] modContexts;
    private ModVersionDefinition[] modVersionDefinitions;
    private String targetVersion;

    public GithubModData(ModDefinition modDefinition, String targetVersion) {
        this.modID = modDefinition.getModID();
        this.name = modDefinition.getName();
        this.optional = modDefinition.getOptional();
        this.modContexts = modDefinition.getContexts();
        this.modVersionDefinitions = modDefinition.getVersions();
        this.targetVersion = targetVersion;
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
    public ModVersionDefinition[] getVersionDefinitions() {
        return modVersionDefinitions;
    }
    @Override
    public void setVersions(ModVersionDefinition[] value) {
        modVersionDefinitions = value;
    }

    @Override
    public ModVersionDefinition getTargetVersionDefinition() {
        Optional<ModVersionDefinition> versionDefinition =
                Arrays.stream(modVersionDefinitions)
                        .filter(x -> Objects.equals(x.getVersion(), targetVersion)).findFirst();
        return versionDefinition.orElse(null);
    }

    @Override
    public String getVersion() {
        return targetVersion;
    }

    @Override
    public void setVersion(String value) {
        targetVersion = value;
    }
}
