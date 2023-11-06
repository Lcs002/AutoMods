package lvum.com.app.controller.mod;

import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.utils.downloader.FileDownloader;
import lvum.com.app.view.AutoModsView;

import java.util.*;
import java.util.logging.Logger;

public class ModControllerImpl implements ModController {
    private static final Logger LOGGER = Logger.getLogger(ModControllerImpl.class.getName());
    private AutoModsView view;
    private ModDefinitionRepository repository;
    private FileDownloader downloader;

    @Override
    public void updateMods() {
        repository.updateRepository();
        view.updateMods(repository.getModsData());
    }

    @Override
    public void downloadMods(List<ModDefinitionContext> contexts, List<ModDownloadTarget> modDownloadTargets, boolean all) {
        List<ModDownloadTarget> modsToDownload = new ArrayList<>(modDownloadTargets);
        List<ModDefinition> modsData = repository.getModsData();

        for (ModDefinition mod : modsData) {
            if (mod.getOptional() != null
                    && (!mod.getOptional() || all)
                    && (contexts.contains(ModDefinitionContext.server)
                        && Arrays.stream(mod.getContexts()).toList().contains(ModDefinitionContext.server)
                        || contexts.contains(ModDefinitionContext.client)
                        && Arrays.stream(mod.getContexts()).toList().contains(ModDefinitionContext.client)))
            {
                modsToDownload.add(ModDownloadTargetBuilder
                        .create(mod.getModID(), mod.getTargetVersionDefinition().getVersion()));
            }
        }
        downloader.download()
        view.showCompletedDownload();
    }

    @Override
    public void setView(AutoModsView autoModsView) {
        this.view = autoModsView;
    }
    @Override
    public void setRepository(ModDefinitionRepository modDefinitionRepository) {
        this.repository = modDefinitionRepository;
    }
    @Override
    public void setDownloader(FileDownloader fileDownloader) {
        this.downloader = fileDownloader;
    }
}
