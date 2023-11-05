package lvum.com.model.mod;

public class ModDownloadTargetBuilder {

    public static ModDownloadTarget create(String modID, String version) {
        return new ModDownloadTargetImpl(modID, version);
    }

    private static class ModDownloadTargetImpl implements ModDownloadTarget{

        private String modID;
        private String version;

        @Override
        public String getModID() {
            return modID;
        }

        @Override
        public String getModVersion() {
            return version;
        }

        private ModDownloadTargetImpl(String modID, String version){
            this.modID = modID;
            this.version = version;
        }
    }
}
