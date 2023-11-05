package lvum.com.controller.mod;

import lvum.com.model.mod.ModDownloadTarget;
import lvum.com.model.mod.ModRepository;
import lvum.com.view.AutoModsView;

import java.nio.file.Path;
import java.util.List;

public interface ModController {
    void updateMods();
    void downloadMods(List<ModDownloadTarget> modData);
    void setModsFolderPath(Path path);
    Path getModsFolderPath();
    void setDeleteIfNotTarget(boolean value);
    void setRepository(ModRepository modRepository);
    void setView(AutoModsView autoModsView);
}
