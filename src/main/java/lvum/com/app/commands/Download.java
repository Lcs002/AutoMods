package lvum.com.app.commands;

import lvum.com.Main;
import lvum.com.app.AutoMods;
import lvum.com.app.AutoModsFactory;
import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.utils.FolderFinder;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@CommandLine.Command(name="download")
public class Download implements Runnable {
    private static Logger LOGGER = Logger.getLogger(Download.class.getName());

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

    @CommandLine.Option(names = {"-d", "--destination"})
    String destination;

    @CommandLine.Option(names = {"-rm", "--remove"}, description = "Removes all .jar files from the folder")
    boolean remove;

    @Override
    public void run() {
        AutoMods autoMods = AutoModsFactory.createCliAutoMods();
        List<ModDefinitionContext> contexts = new ArrayList<>();
        if (client) contexts.add(ModDefinitionContext.client);
        if (server) contexts.add(ModDefinitionContext.server);
        if (mods == null) mods = new ArrayList<>();
        if (destination == null) destination = FolderFinder.getAppDataMincraftModsFolder();

        autoMods.getModController().updateMods();
        autoMods.getModController().setDownloadDestination(destination);
        autoMods.getModController().setCleanup(remove);
        autoMods.getModController().download(contexts, mods, all);
    }
}
