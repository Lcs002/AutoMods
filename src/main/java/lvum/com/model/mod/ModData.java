package lvum.com.model.mod;

import lvum.com.model.mod.github.definition.ModContext;
import lvum.com.model.mod.github.definition.ModVersionDefinition;

public interface ModData {
    String getModID();
    void setModID(String value);

    String getName();
    void setName(String value);

    String getDescription();
    void setDescription(String value);

    Boolean getOptional();
    void setOptional(Boolean value);

    ModContext[] getContexts();
    void setContexts(ModContext[] value);

    ModVersionDefinition[] getVersionDefinitions();
    void setVersions(ModVersionDefinition[] value);

    ModVersionDefinition getTargetVersionDefinition();

    String getVersion();
    void setVersion(String value);
}
