package lvum.com.cli;

import lvum.com.app.AutoMods;
import lvum.com.app.AutoModsFactory;
import lvum.com.cli.commands.CliCmdDownload;
import picocli.CommandLine;

@CommandLine.Command(name="automods", subcommands = {
    CliCmdDownload.class
})
public class CliAutoMods implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new CliAutoMods());
    }

    @Override
    public void run() {
        AutoModsFactory.createWindowAutoMods();
    }
}
