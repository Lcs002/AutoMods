package lvum.com.app;

import lvum.com.controller.mod.ModControllerImpl;
import lvum.com.model.mod.github.GithubModRepository;
import lvum.com.view.cli.AutoModsCliView;
import lvum.com.view.window.AutoModsGUI;

public class AutoModsFactory {
    public static AutoMods createWindowAutoMods(){
        return new AutoMods(
                new AutoModsGUI(),
                new ModControllerImpl(),
                new GithubModRepository()
        );
    }

    public static AutoMods createCliAutoMods(){
        return new AutoMods(
                new AutoModsCliView(),
                new ModControllerImpl(),
                new GithubModRepository()
        );
    }
}
