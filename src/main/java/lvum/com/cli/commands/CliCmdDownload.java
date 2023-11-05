package lvum.com.cli.commands;

import lvum.com.app.AutoMods;
import lvum.com.app.AutoModsFactory;
import lvum.com.cli.CliAutoMods;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name="download")
public class CliCmdDownload implements Runnable {

    @CommandLine.ParentCommand()
    CliAutoMods parent;

    @CommandLine.Option(names = {"-c", "--client"})
    boolean client;

    @CommandLine.Option(names = {"-s", "--server"})
    boolean server;

    @CommandLine.Option(names = {"-f", "--files"}, split = " ", description = "Additional mods")
    List files;

    @Override
    public void run() {
        AutoMods autoMods = AutoModsFactory.createCliAutoMods();
    }
}
