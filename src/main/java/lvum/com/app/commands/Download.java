package lvum.com.app.commands;

import lvum.com.Main;
import lvum.com.app.AutoMods;
import lvum.com.app.AutoModsFactory;
import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.app.controller.mod.ModDownloadTarget;
import lvum.com.app.controller.mod.ModDownloadTargetBuilder;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name="download")
public class Download implements Runnable {

    @CommandLine.ParentCommand()
    Main parent;

    @CommandLine.Option(names = {"-c", "--client"})
    boolean client;

    @CommandLine.Option(names = {"-s", "--server"})
    boolean server;

    @CommandLine.Option(names={"-a", "--all"}, description = "Downloads all optional mods for the given contexts")
    boolean all;

    @CommandLine.Option(names = {"-m", "--mods"}, split = " ", description = "Additional mods")
    List<String> mods;

    @Override
    public void run() {
        AutoMods autoMods = AutoModsFactory.createCliAutoMods();
        List<ModDefinitionContext> contexts = new ArrayList<>();
        List<ModDownloadTarget> modDownloadTargets = new ArrayList<>();
        if (client) contexts.add(ModDefinitionContext.client);
        if (server) contexts.add(ModDefinitionContext.server);
        if (mods != null)
            for (String mod : mods) modDownloadTargets.add(ModDownloadTargetBuilder.create(mod, null));
        autoMods.getModController().updateMods();
        autoMods.getModController().downloadMods(contexts, modDownloadTargets, all);
    }
}
