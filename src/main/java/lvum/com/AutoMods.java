package lvum.com;

import lvum.com.controller.mod.ModControllerImpl;
import lvum.com.controller.mod.ModController;
import lvum.com.model.mod.github.GithubModRepository;
import lvum.com.model.mod.github.TargetedMod;
import lvum.com.model.mod.ModRepository;
import lvum.com.view.AppUI;
import lvum.com.view.AutoModsUI;

import java.util.List;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class AutoMods
{
    private static final Logger LOGGER = Logger.getLogger(AutoMods.class.getName());
    private final AppUI ui;
    private final ModController modController;
    private final ModRepository modRepository;


    public static void main( String[] args ) {
        AutoMods autoMods = new AutoMods();
        autoMods.start();
    }


    public AutoMods() {
        this.ui = new AutoModsUI(this);
        this.modRepository = new GithubModRepository();
        this.modController = new ModControllerImpl(ui, modRepository);
    }

    public void start() {
        ui.setVisible(true);
    }

    // TODO avoid this with a list of Controllers and a method GetController<T>

    public void updateMods() {
        modController.updateMods();
    }

    public void downloadMods(List<TargetedMod> targetedMods) {
        modController.downloadMods(targetedMods);
    }
}
