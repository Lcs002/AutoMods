package lvum.com.app.view;


import lvum.com.app.AutoMods;
import lvum.com.app.model.mod_definition.ModDefinition;

import java.util.List;

public interface AutoModsView {
    void updateMods(List<ModInfo> modInfo);
    void showCompletedDownload();
}
