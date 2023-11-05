package lvum.com.model.mod.github.reference;

import lvum.com.model.mod.ModDownloadResult;

import java.util.ArrayList;
import java.util.List;

public class GithubModDownloadResult implements ModDownloadResult {
    private List<String> modsWithErrors = new ArrayList<>();
    private List<String> modsDownloaded = new ArrayList<>();

    public List<String> getModsDownloaded() {
        return modsDownloaded;
    }

    public List<String> getModsWithErrors() {
        return modsWithErrors;
    }

    public void addError(String modid) {
        modsWithErrors.add(modid);
    }

    public void addDownload(String modid) {
        modsDownloaded.add(modid);
    }
}
