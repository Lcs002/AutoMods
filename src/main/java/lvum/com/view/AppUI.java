package lvum.com.view;

import lvum.com.model.mod.github.TargetedMod;

import javax.swing.*;
import java.util.List;

public abstract class AppUI extends JFrame {
    public abstract void updateMods(List<TargetedMod> targetedMods);
    public abstract void showCompletedDownload();
}
