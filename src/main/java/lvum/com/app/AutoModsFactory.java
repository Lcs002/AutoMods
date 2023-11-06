package lvum.com.app;

import lvum.com.app.controller.mod.ModControllerImpl;
import lvum.com.app.model.mod_definition.github.GithubModDefinitionRepository;
import lvum.com.app.view.cli.AutoModsCliView;
import lvum.com.app.view.gui.AutoModsGUI;

public class AutoModsFactory {
    public static AutoMods createWindowAutoMods(){
        return new AutoMods(
                new AutoModsGUI(),
                new ModControllerImpl(),
                new GithubModDefinitionRepository()
        );
    }

    public static AutoMods createCliAutoMods(){
        return new AutoMods(
                new AutoModsCliView(),
                new ModControllerImpl(),
                new GithubModDefinitionRepository()
        );
    }
}
