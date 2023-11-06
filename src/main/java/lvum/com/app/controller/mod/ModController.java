package lvum.com.app.controller.mod;

import lvum.com.utils.downloader.FileDownloader;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.view.AutoModsView;

import java.util.List;

public interface ModController {
    void updateMods();
    void setView(AutoModsView autoModsView);
    void download(List<ModDownloadTarget> modDownloadTargets);
    void setRepository(ModDefinitionRepository modDefinitionRepository);
    void setDownloader(FileDownloader fileDownloader);
}
