package lvum.com.app.model.mod_definition;

import lvum.com.utils.downloader.FileDownloader;

import java.util.List;

public interface ModDefinitionRepository {
    void updateRepository();
    ModDefinition getModData(String modID);
    List<ModDefinition> getModsData(List<String> modsID);
    FileDownloader getDownloader();
}
