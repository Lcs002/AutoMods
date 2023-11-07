package lvum.com.app.model.mod_reference;

import java.util.List;

public interface ModReferenceRepository {
    void updateRepository();
    ModReference getOne(String modID);
    List<ModReference> getMultiple(List<String> modsID);
    List<ModReference> getAll();
}
