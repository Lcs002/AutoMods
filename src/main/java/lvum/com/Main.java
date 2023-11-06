package lvum.com;

import lvum.com.app.AutoModsFactory;
import lvum.com.app.commands.Download;
import picocli.CommandLine;

@CommandLine.Command(name="automods", subcommands = {
    Download.class
})
public class Main implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new Main(), args);
    }

    @Override
    public void run() {
        AutoModsFactory.createWindowAutoMods();
    }
}
