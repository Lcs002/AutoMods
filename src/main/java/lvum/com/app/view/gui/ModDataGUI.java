package lvum.com.app.view.gui;

import lvum.com.app.model.mod_definition.ModDefinition;
import lvum.com.app.model.mod_definition.github.yml.YMLModDefinitionDependency;

import javax.swing.*;
import java.awt.*;

public class ModDataGUI extends JPanel {

    private ModDefinition ModDefinition;
    private JLabel modName = new JLabel();
    private JLabel modIdAndVersion = new JLabel();
    private JLabel description = new JLabel();
    private JLabel dependenciesTitle = new JLabel();
    private JLabel dependencies = new JLabel();


    public ModDataGUI(){
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

    public void setMod(ModDefinition ModDefinition) {
        modName.setText(ModDefinition.getName());
        modIdAndVersion.setText(ModDefinition.getModID() + ":" + ModDefinition.getVersion());
        StringBuilder sb = new StringBuilder();
        if (ModDefinition.getTargetVersionDefinition().getDependencies() != null)
            for (YMLModDefinitionDependency YMLModDefinitionDependency : ModDefinition.getTargetVersionDefinition().getDependencies())
                sb.append(YMLModDefinitionDependency.getModID()).append(":").append(YMLModDefinitionDependency.getVersion())
                        .append("\n");
        dependencies.setText(sb.toString());
        description.setText(ModDefinition.getDescription());
    }
}
