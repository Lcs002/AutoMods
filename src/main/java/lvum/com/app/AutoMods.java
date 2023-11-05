package lvum.com.app;

import lvum.com.controller.mod.ModController;
import lvum.com.model.mod.ModRepository;
import lvum.com.view.AutoModsView;

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
    private final ModRepository modRepository;

    public AutoMods(AutoModsView autoModsView, ModController modController, ModRepository modRepository) {
        this.autoModsView = autoModsView;
        this.modRepository = modRepository;
        this.modController = modController;

        this.autoModsView.setAutoMods(this);
        this.modController.setRepository(this.modRepository);
        this.modController.setView(this.autoModsView);

    }

    // TODO avoid this with a list of Controllers and a method GetController<T>

    public ModController getModController(){
        return modController;
    }
}
