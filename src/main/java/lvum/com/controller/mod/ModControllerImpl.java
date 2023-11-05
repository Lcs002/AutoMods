package lvum.com.controller.mod;

import lvum.com.model.mod.ModDownloadTarget;
import lvum.com.model.mod.ModRepository;
import lvum.com.model.mod.github.definition.ModDependencyDefinition;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.definition.ModVersionDefinition;
import lvum.com.view.AutoModsView;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModControllerImpl implements ModController {
    private static final Logger LOGGER = Logger.getLogger(ModControllerImpl.class.getName());
    private AutoModsView view;
    private ModRepository repository;
    private Path downloadFolderPath;
    private boolean deleteIfNotTarget;


    public ModControllerImpl()
    {
        this.downloadFolderPath = getAppDataMincraftModsFolder();
    }

    private Path getAppDataMincraftModsFolder() {
        LOGGER.log(Level.INFO, "[STARTED] Find Appdata Folder");
        String folderPath = System.getenv("APPDATA");
        Path path = Paths.get(folderPath + "/.minecraft/mods");
        if (!Files.exists(path)) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "ERROR: Folder '" + path + "' not found",
                    "Folder not found", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        LOGGER.log(Level.INFO, "[ENDED] Find Appdata Folder");
        return path;
    }


    @Override
    public void updateMods() {
        repository.updateRepository();
        view.updateMods(repository.getModsData());
    }

    @Override
    public void downloadMods(List<ModDownloadTarget> modDownloadTargets) {
        repository.download(modDownloadTargets, downloadFolderPath.toString());
        view.showCompletedDownload();
    }

    @Override
    public void setModsFolderPath(Path path) {
        downloadFolderPath = path;
    }

    @Override
    public Path getModsFolderPath(){
        return downloadFolderPath;
    }

    @Override
    public void setDeleteIfNotTarget(boolean value) {
        deleteIfNotTarget = value;
    }

    @Override
    public void setRepository(ModRepository modRepository) {
        this.repository = modRepository;
    }

    @Override
    public void setView(AutoModsView autoModsView) {
        this.view = autoModsView;
    }
}
