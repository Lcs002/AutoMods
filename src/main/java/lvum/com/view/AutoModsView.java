package lvum.com.view;


import lvum.com.app.AutoMods;
import lvum.com.model.mod.ModData;

import java.util.List;

public interface AutoModsView {
    void updateMods(List<ModData> modData);
    void showCompletedDownload();
    void setAutoMods(AutoMods autoMods);
}
