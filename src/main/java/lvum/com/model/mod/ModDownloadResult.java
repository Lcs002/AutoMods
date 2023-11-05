package lvum.com.model.mod;

import java.util.List;

public interface ModDownloadResult {
    List<String> getModsDownloaded();

    List<String> getModsWithErrors();

    void addError(String modid);

    void addDownload(String modid);
}
