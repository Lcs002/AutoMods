package lvum.com.view;

import lvum.com.model.mod.github.TargetedMod;
import lvum.com.model.mod.github.definition.ModDependencyDefinition;

import javax.swing.*;
import java.awt.*;

public class ModView extends JPanel {

    private TargetedMod targetedMod;
    private JLabel modName = new JLabel();
    private JLabel modIdAndVersion = new JLabel();
    private JLabel description = new JLabel();
    private JLabel dependenciesTitle = new JLabel();
    private JLabel dependencies = new JLabel();


    public ModView(){
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

    public void setMod(TargetedMod targetedMod) {
        modName.setText(targetedMod.getName());
        modIdAndVersion.setText(targetedMod.getModID() + ":" + targetedMod.getTargetVersion().getVersion());
        StringBuilder sb = new StringBuilder();
        if (targetedMod.getTargetVersion().getDependencies() != null)
            for (ModDependencyDefinition modDependencyDefinition : targetedMod.getTargetVersion().getDependencies())
                sb.append(modDependencyDefinition.getModID()).append(":").append(modDependencyDefinition.getVersion())
                        .append("\n");
        dependencies.setText(sb.toString());
        description.setText(targetedMod.getDescription());
    }
}
