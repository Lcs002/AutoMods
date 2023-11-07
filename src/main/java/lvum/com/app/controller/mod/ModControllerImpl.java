package lvum.com.app.controller.mod;

import lvum.com.app.AutoMods;
import lvum.com.app.model.mod_definition.*;
import lvum.com.app.model.mod_reference.ModReference;
import lvum.com.app.model.mod_reference.ModReferenceRepository;
import lvum.com.app.view.ModInfo;
import lvum.com.utils.downloader.FileDownloader;
import lvum.com.app.view.AutoModsView;
import lvum.com.utils.downloader.FileDownloaderImpl;

import java.util.*;
import java.util.logging.Logger;

public class ModControllerImpl implements ModControllerComponent {
    private static final Logger LOGGER = Logger.getLogger(ModControllerImpl.class.getName());
    private AutoModsView view;
    private ModDefinitionRepository modDefinitionRepository;
    private ModReferenceRepository modReferenceRepository;
    private String downloadDestination;
    private boolean cleanup;
    private FileDownloader downloader;

    @Override
    public void updateMods() {
        modReferenceRepository.updateRepository();
        modDefinitionRepository.updateRepository();

        List<ModInfo> modsInfo = new ArrayList<>();
        List<ModReference> modReferences = modReferenceRepository.getAll();
        for (ModReference modReference : modReferences) {
            ModDefinition modDefinition = modDefinitionRepository.getOne(modReference.getModID());
            ModDefinitionVersion modDefinitionVersion = getTargetModDefinitionVersion(modReference.getModID());
            modsInfo.add(new ModInfo(modDefinition, modDefinitionVersion.getVersion()));
        }
        view.updateMods(modsInfo);
    }

    @Override
    public void download(List<ModDefinitionContext> contexts, List<String> modsID, boolean downloadAll) {
        List<String> files = new ArrayList<>();

        if (!contexts.isEmpty()) {
            for (ModReference modReference : modReferenceRepository.getAll()) {
                String modID = modReference.getModID();
                ModDefinition modDefinition = modDefinitionRepository.getOne(modID);
                ModDefinitionVersion targetModDefinitionVersion = getTargetModDefinitionVersion(modID);
                Boolean optional = modDefinition.getOptional();
                if (optional != null && ((!optional || downloadAll)
                            && (files.isEmpty() || !files.contains(targetModDefinitionVersion.getFile()))
                            && (modDefinition.getContexts() == null
                            || Arrays.stream(modDefinition.getContexts()).anyMatch(contexts::contains))))
                {
                    files.add(targetModDefinitionVersion.getFile());
                    getDependencies(targetModDefinitionVersion, files);
                }
            }
        }

        for (String modID : modsID) {
            ModReference modReference = modReferenceRepository.getOne(modID);
            if (modReference == null) throw new IllegalArgumentException();
            ModDefinition modDefinition = modDefinitionRepository.getOne(modID);
            if (modDefinition == null) throw new IllegalArgumentException();
            List<ModDefinitionContext> modDefinitionContexts = Arrays.asList(modDefinition.getContexts());

            if ((contexts.contains(ModDefinitionContext.server)
                    && modDefinitionContexts.contains(ModDefinitionContext.server)
                    || contexts.contains(ModDefinitionContext.client)
                    && modDefinitionContexts.contains(ModDefinitionContext.client)))
            {
                ModDefinitionVersion targetModDefinitionVersion = getTargetModDefinitionVersion(modID);
                if (!files.contains(targetModDefinitionVersion.getFile())) {
                    files.add(targetModDefinitionVersion.getFile());
                    getDependencies(targetModDefinitionVersion, files);
                }
            }
        }
        downloader = new FileDownloaderImpl();
        downloader.download(files, downloadDestination, cleanup);
        view.showCompletedDownload();
    }

    @Override
    public void setDownloadDestination(String value) {
        downloadDestination = value;
    }

    @Override
    public void setCleanup(boolean value) {
        cleanup = value;
    }

    @Override
    public void setView(AutoModsView autoModsView) {
        this.view = autoModsView;
    }

    @Override
    public void setModDefinitionRepository(ModDefinitionRepository modDefinitionRepository) {
        this.modDefinitionRepository = modDefinitionRepository;
    }
    @Override
    public void setModReferenceRepository(ModReferenceRepository modReferenceRepository) {
        this.modReferenceRepository = modReferenceRepository;
    }

    @Override
    public void setDownloader(FileDownloader fileDownloader) {
        this.downloader = fileDownloader;
    }

    private ModDefinitionVersion getTargetModDefinitionVersion(String modID) {
        ModReference modReference = modReferenceRepository.getOne(modID);
        ModDefinition modDefinition = modDefinitionRepository.getOne(modID);

        List<ModDefinitionVersion> modDefinitionVersions = Arrays.stream(modDefinition.getVersions()).toList();
        String version = modReference.getVersion();
        Optional<ModDefinitionVersion> targetVersion = modDefinitionVersions.stream()
                .filter(x -> x.getVersion().equals(version)).findFirst();

        if (targetVersion.isEmpty()) {
            targetVersion = modDefinitionVersions.stream().findFirst();
            if (targetVersion.isEmpty()) throw new IllegalArgumentException();
        }
        return targetVersion.get();
    }

    private void getDependencies(ModDefinitionVersion modDefinitionVersion, List<String> files) {
        if (modDefinitionVersion.getDependencies() == null) return;
        ModDefinitionDependency[] modDefinitionDependencies = modDefinitionVersion.getDependencies();
        for (ModDefinitionDependency modDefinitionDependency : modDefinitionDependencies) {
            ModDefinition modDefinition = modDefinitionRepository.getOne(modDefinitionDependency.getModID());
            Optional<ModDefinitionVersion> dependencyModDefinitionVersion = Arrays.stream(modDefinition.getVersions())
                    .filter(x -> x.getVersion().equals(modDefinitionDependency.getVersion())).findFirst();
            if (dependencyModDefinitionVersion.isEmpty())
                throw new IllegalArgumentException("[ERROR]: Version " + modDefinitionDependency.getVersion() +
                        " for " + modDefinitionDependency.getModID() + " not found.");
            String file = dependencyModDefinitionVersion.get().getFile();
            if (!files.contains(file)) files.add(file);
        }
    }
}
