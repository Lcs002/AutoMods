package lvum.com.app.view.gui;

import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.ModDefinitionDependency;
import lvum.com.app.model.mod_definition.ModDefinitionVersion;
import lvum.com.app.model.mod_definition.github.yml.YMLModDefinitionDependency;
import lvum.com.app.view.ModInfo;

import javax.swing.*;
import java.awt.*;

public class ModInfoGUI extends JPanel {
    private JLabel modName = new JLabel();
    private JLabel modIdAndVersion = new JLabel();
    private JLabel description = new JLabel();
    private JLabel dependenciesTitle = new JLabel();
    private JLabel dependencies = new JLabel();


    public ModInfoGUI(){
        this.setSize(250, 600);
        this.setLayout(new GridLayout(0,1));
        modName.setFont(new Font("Verdana", Font.BOLD, 22));
        modName.setHorizontalAlignment(JLabel.CENTER);
        modIdAndVersion.setFont(new Font("Verdana", Font.ITALIC, 14));
        modIdAndVersion.setHorizontalAlignment(JLabel.CENTER);
        description.setFont(new Font("Verdana", Font.ITALIC, 14));
        description.setHorizontalAlignment(JLabel.CENTER);
        dependenciesTitle.setFont(new Font("Verdana", Font.BOLD, 18));
        dependenciesTitle.setText("Dependencies");
        dependenciesTitle.setHorizontalAlignment(JLabel.CENTER);
        modName.setHorizontalAlignment(JLabel.CENTER);
        dependencies.setFont(new Font("Verdana", Font.ITALIC, 14));
        dependencies.setHorizontalAlignment(JLabel.CENTER);
        this.add(modName);
        this.add(modIdAndVersion);
        this.add(description);
        this.add(dependenciesTitle);
        this.add(dependencies);
    }

    public void setMod(ModInfo modInfo) {
        modName.setText(modInfo.getName());
        modIdAndVersion.setText(modInfo.getModID() + ":" + modInfo.getVersion());
        StringBuilder sb = new StringBuilder();
        if (modInfo.getTargetVersion().getDependencies() != null)
            for (ModDefinitionDependency modDefinitionDependency : modInfo.getTargetVersion().getDependencies())
                sb.append(modDefinitionDependency.getModID()).append(":").append(modDefinitionDependency.getVersion())
                        .append("\n");
        dependencies.setText(sb.toString());
        description.setText(modInfo.getDescription());
    }
}
