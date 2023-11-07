package lvum.com.app.controller.mod;

import lvum.com.app.AutoMods;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.model.mod_reference.ModReferenceRepository;
import lvum.com.app.view.AutoModsView;
import lvum.com.utils.downloader.FileDownloader;

public interface ModControllerComponent extends ModController {
    void setModDefinitionRepository(ModDefinitionRepository modDefinitionRepository);
    void setModReferenceRepository(ModReferenceRepository modReferenceRepository);
    void setView(AutoModsView autoModsView);
    void setDownloader(FileDownloader fileDownloader);
}

