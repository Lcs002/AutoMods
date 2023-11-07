package lvum.com.app.controller.mod;

import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.utils.downloader.FileDownloader;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.view.AutoModsView;

import java.util.List;

public interface ModController {
    void updateMods();
    void download(List<ModDefinitionContext> contexts, List<String> modsID, boolean all);
    void setDownloadDestination(String value);
    void setCleanup(boolean value);
}

