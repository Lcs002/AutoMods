package lvum.com.app;

import lvum.com.app.controller.mod.ModController;
import lvum.com.app.model.mod_definition.ModDefinitionRepository;
import lvum.com.app.view.AutoModsView;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class AutoMods
{
    private static final Logger LOGGER = Logger.getLogger(AutoMods.class.getName());
    private final AutoModsView autoModsView;
    private final ModController modController;
    private final ModDefinitionRepository modDefinitionRepository;

    public AutoMods(AutoModsView autoModsView, ModController modController, ModDefinitionRepository modDefinitionRepository) {
        this.autoModsView = autoModsView;
        this.modDefinitionRepository = modDefinitionRepository;
        this.modController = modController;

        this.autoModsView.setAutoMods(this);
        this.modController.setRepository(this.modDefinitionRepository);
        this.modController.setView(this.autoModsView);

    }

    // TODO avoid this with a list of Controllers and a method GetController<T>

    public ModController getModController(){
        return modController;
    }
}
