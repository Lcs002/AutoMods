package lvum.com.model.mod;

import lvum.com.model.mod.github.TargetedMod;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.reference.ModReference;

import java.util.List;

public interface ModRepository {

    void updateRepository();
    List<ModDefinition> getDefinitions();
    List<ModReference> getReferences();
    ModDownloadResult download(TargetedMod targetedMod);
    ModDownloadResult download(List<TargetedMod> targetedMods);
}
