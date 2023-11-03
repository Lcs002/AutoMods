package lvum.com.controller.mod;

import lvum.com.model.mod.github.TargetedMod;

import java.util.List;

public interface ModController {
    void updateMods();
    void downloadMods(List<TargetedMod> targetedMods);
}
