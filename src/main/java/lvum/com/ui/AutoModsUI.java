package lvum.com.ui;

import lvum.com.AutoMods;
import lvum.com.Mod;
import lvum.com.definition.ModDefinition;
import lvum.com.definition.YMLContextDefinition;
import lvum.com.definition.YMLModDefinition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoModsUI extends JFrame {

    private final Dimension frameDimension = new Dimension(600, 800);
    private final JPanel modsPanel = new JPanel();
    private final JScrollPane modsScrollPane = new JScrollPane(modsPanel);
    private final JCheckBox downloadClient = new JCheckBox("Download Client Mods");
    private final JCheckBox downloadServer = new JCheckBox("Download Server Mods");
    private final JButton clientModsBtn = new JButton("Client");
    private final JButton serverModsBtn = new JButton("Server");
    private final JButton refreshModsBtn = new JButton("Refresh");
    private final JButton downloadModsBtn = new JButton("Download");
    private final JButton dependencyModsBtn = new JButton("Dependencies");
    private final AutoMods aplication;

    private Map<JCheckBox, Mod> clientMods;
    private Map<JCheckBox, Mod> serverMods;
    private Map<JCheckBox, Mod> dependencyMods;
    private String context;


    public static void main(String[] args) {
        AutoModsUI autoModsUI = new AutoModsUI(null);
        List<Mod> mods = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ModDefinition modDefinition = new YMLModDefinition();
            modDefinition.setName("AAA");
            modDefinition.setOptional(i%2 == 0);
            modDefinition.setContexts(new YMLContextDefinition[] {YMLContextDefinition.server});
            mods.add(new Mod(modDefinition, "bb"));
        }
        autoModsUI.setVisible(true);
        autoModsUI.updateMods(mods);
    }

    public AutoModsUI(AutoMods autoMods) {
        this.context = "client";
        this.aplication = autoMods;
        this.serverMods = new HashMap<>();
        this.clientMods = new HashMap<>();
        this.dependencyMods = new HashMap<>();
        setLookAndFeel();
        initializeUI();
    }

    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to set LookAndFeel");
        }
    }

    private void initializeUI(){
        this.setTitle("Auto Mods");
        this.setSize(frameDimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.modsPanel.setLayout(new BoxLayout(modsPanel, BoxLayout.Y_AXIS));
        this.modsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.modsScrollPane.setPreferredSize(new Dimension(250, 600));

        clientModsBtn.addActionListener(x -> {
            context = "client";
            showContextMods();
        });

        serverModsBtn.addActionListener(x -> {
            context = "server";
            showContextMods();
        });

        dependencyModsBtn.addActionListener(x -> {
            context = "dependency";
            showContextMods();
        });

        refreshModsBtn.addActionListener(x -> {
            aplication.updateMods();
        });

        downloadModsBtn.addActionListener(x -> {
            List<Mod> selectedMods = new ArrayList<>();
            if (downloadClient.isSelected())
                for (Map.Entry<JCheckBox, Mod> mod : clientMods.entrySet())
                    if (mod.getKey().isSelected()) selectedMods.add(mod.getValue());
            if (downloadServer.isSelected())
                for (Map.Entry<JCheckBox, Mod> mod : serverMods.entrySet())
                    if (mod.getKey().isSelected()) selectedMods.add(mod.getValue());
            aplication.downloadMods(selectedMods);
        });

        JPanel leftup = new JPanel();
        leftup.setLayout(new BoxLayout(leftup, BoxLayout.X_AXIS));
        leftup.setSize(250, 200);
        leftup.add(clientModsBtn);
        leftup.add(serverModsBtn);
        leftup.setBackground(Color.BLACK);

        JPanel leftdown = new JPanel();
        leftdown.setSize(250, 700);
        leftdown.add(modsScrollPane);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setSize(300, 800);
        left.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        left.add(leftup);
        left.add(leftdown);

        JPanel right = new JPanel();
        right.setSize(300, 800);

        JPanel up = new JPanel(new GridLayout(1,2));
        up.add(left);
        up.add(right);

        JPanel down = new JPanel();
        down.setLayout(new GridLayout(2, 2));
        down.setSize(400, 200);
        down.add(refreshModsBtn);
        down.add(downloadModsBtn);
        down.add(downloadClient);
        down.add(downloadServer);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        container.add(up);
        container.add(down);

        this.add(container);
    }

    public void updateMods(List<Mod> mods) {
        clientMods.clear();
        serverMods.clear();
        for (Mod mod : mods) {
            if (mod.getContexts() != null) {
                JCheckBox checkBox = new JCheckBox(mod.getName());

                for (YMLContextDefinition contextDefinition : mod.getContexts()) {
                    if (contextDefinition.equals(YMLContextDefinition.client)) clientMods.put(checkBox, mod);
                    if (contextDefinition.equals(YMLContextDefinition.server)) serverMods.put(checkBox, mod);
                }

                if (!mod.getOptional()) {
                    checkBox.setModel(new ReadOnlyToggleButtonModel(true));
                    checkBox.setFocusable(false);
                }
            }
        }
        showContextMods();
    }

    private void showContextMods() {
        Map<JCheckBox, Mod> targetMods = new HashMap<>();

        switch (context) {
            case "server" -> targetMods = serverMods;
            case "client" -> targetMods = clientMods;
            case "dependency" -> targetMods = dependencyMods;
        }

        modsPanel.removeAll();
        for (Map.Entry<JCheckBox, Mod> targetMod : targetMods.entrySet()) {
            modsPanel.add(targetMod.getKey());
        }
        modsPanel.updateUI();
    }
}
