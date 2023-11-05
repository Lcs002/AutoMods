package lvum.com.model.mod;

import java.nio.file.Path;
import java.util.List;

public interface ModRepository {
    void updateRepository();
    ModData getModData(String modID);
    List<ModData> getModsData();
    ModDownloadResult download(ModDownloadTarget modDownloadTarget, String destFolderPath);
    ModDownloadResult download(List<ModDownloadTarget> modDownloadTargets, String destFolderPath);
}
