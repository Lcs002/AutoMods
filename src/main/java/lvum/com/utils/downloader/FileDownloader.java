package lvum.com.utils.downloader;

import java.util.List;

public interface FileDownloader {
    FileDownloadResult download(String file, String destination, boolean cleanup);
    FileDownloadResult download(List<String> files, String destination, boolean cleanup);
}
