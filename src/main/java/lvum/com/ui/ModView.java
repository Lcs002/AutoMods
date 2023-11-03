package lvum.com.ui;

import lvum.com.Mod;
import lvum.com.definition.YMLModDefinition;
import lvum.com.definition.YMLModsDefinitions;

import javax.swing.*;
import java.awt.*;

public class ModView extends JPanel {

    private Mod mod;
    private JLabel modName = new JLabel();
    private JLabel modIdAndVersion = new JLabel();
    private JLabel versionsTitle = new JLabel();
    private JLabel versions = new JLabel();
    private JLabel dependenciesTitle = new JLabel();
    private JLabel dependencies = new JLabel();

    public static void main(String[] args) {
        JFrame p = new JFrame();
        ModView modView = new ModView();
        Mod def = new Mod(new YMLModDefinition(), "q");
        def.setName("Mod Name");
        def.setModID("mod-id");
        def.setOptional(false);
        def.setVersion("1.20.1");
        modView.setMod(def);
        p.setSize(800, 900);
        p.getContentPane().add(modView);
        p.setVisible(true);
    }

    public ModView(){
        this.setSize(250, 600);
        this.setLayout(new GridLayout(0,1));
        modName.setFont(new Font("Verdana", Font.BOLD, 22));
        modName.setHorizontalAlignment(JLabel.CENTER);
        modIdAndVersion.setFont(new Font("Verdana", Font.ITALIC, 14));
        modIdAndVersion.setHorizontalAlignment(JLabel.CENTER);
        versionsTitle.setFont(new Font("Verdana", Font.BOLD, 18));
        versionsTitle.setText("Versions");
        versionsTitle.setHorizontalAlignment(JLabel.CENTER);
        versions.setFont(new Font("Verdana", Font.ITALIC, 14));
        versions.setHorizontalAlignment(JLabel.CENTER);
        dependenciesTitle.setFont(new Font("Verdana", Font.BOLD, 18));
        dependenciesTitle.setText("Dependencies");
        dependenciesTitle.setHorizontalAlignment(JLabel.CENTER);
        modName.setHorizontalAlignment(JLabel.CENTER);
        dependencies.setFont(new Font("Verdana", Font.ITALIC, 14));
        dependencies.setHorizontalAlignment(JLabel.CENTER);
        this.add(modName);
        this.add(modIdAndVersion);
        this.add(versionsTitle);
        this.add(versions);
        this.add(dependenciesTitle);
        this.add(dependencies);
    }

    public void setMod(Mod mod) {
        modName.setText(mod.getName());
        modIdAndVersion.setText(mod.getModID() + ":" + mod.getVersion());
    }
}
