package lvum.com.app;

import lvum.com.app.controller.mod.ModController;
import lvum.com.app.controller.mod.ModControllerComponent;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.model.mod_reference.ModReferenceRepository;
import lvum.com.app.view.AutoModsView;
import lvum.com.app.view.AutoModsViewComponent;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class AutoMods
{
    private static final Logger LOGGER = Logger.getLogger(AutoMods.class.getName());
    private final AutoModsViewComponent autoModsView;
    private final ModControllerComponent modController;
    private final ModDefinitionRepository modDefinitionRepository;
    private final ModReferenceRepository modReferenceRepository;

    public AutoMods(AutoModsViewComponent autoModsView, ModControllerComponent modController,
                    ModDefinitionRepository modDefinitionRepository, ModReferenceRepository modReferenceRepository) {
        this.autoModsView = autoModsView;
        this.modDefinitionRepository = modDefinitionRepository;
        this.modReferenceRepository = modReferenceRepository;
        this.modController = modController;

        this.autoModsView.setModController(modController);
        this.modController.setView(autoModsView);
        this.modController.setModDefinitionRepository(modDefinitionRepository);
        this.modController.setModReferenceRepository(modReferenceRepository);

    }

    // TODO avoid this with a list of Controllers and a method GetController<T>

    public ModController getModController(){
        return modController;
    }
}
