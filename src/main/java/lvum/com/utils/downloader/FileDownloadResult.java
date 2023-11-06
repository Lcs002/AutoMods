package lvum.com.utils.downloader;

import java.util.List;

public interface FileDownloadResult {
    List<String> getModsDownloaded();

    List<String> getModsWithErrors();

    void addError(String modid);

    void addDownload(String modid);
}
