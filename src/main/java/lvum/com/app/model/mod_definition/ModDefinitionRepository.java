package lvum.com.app.model.mod_definition;

import java.util.List;

public interface ModDefinitionRepository {
    void updateRepository();
    ModDefinition getOne(String modID);
    List<ModDefinition> getMultiple(List<String> modsID);
    List<ModDefinition> getAll();
}
