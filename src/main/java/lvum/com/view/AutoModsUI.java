package lvum.com.view;

import lvum.com.AutoMods;
import lvum.com.model.mod.github.TargetedMod;
import lvum.com.model.mod.github.definition.ModContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoModsUI extends AppUI {
    private static final String MESSAGE_TITLE = "AutoMods - CaposMods";
    private static final String INITIAL_MESSAGE =
            """
            Do you wish do download the latest necessary Definitions for 'caposingenieros.aternos.me:23447'?
            All files will be downloaded to Appdata/Roaming/.minecraft/targetedMods.
            No local files will be removed, moved or changed during this process.
            """;

    private static final String SUCCESS_MESSAGE =
            """
            Download Complete!
            The following Definitions were installed:%s       
            """;
    private static final String ERROR_MESSAGE =
            """
            The following Definitions couldn't be downloaded:%s
            Make sure they are not already on 'targetedMods' folder.
            """;
    private final Dimension frameDimension = new Dimension(600, 800);
    private final JPanel modsPanel = new JPanel();
    private final JScrollPane modsScrollPane = new JScrollPane(modsPanel);
    private final JCheckBox downloadClient = new JCheckBox("Download Client Mods");
    private final JCheckBox downloadServer = new JCheckBox("Download Server Mods");
    private final JToggleButton clientModsBtn = new JToggleButton("Client");
    private final JToggleButton serverModsBtn = new JToggleButton("Server");
    private final JButton refreshModsBtn = new JButton("Refresh");
    private final JButton downloadModsBtn = new JButton("Download");
    private final JButton dependencyModsBtn = new JButton("Dependencies");
    private final ModView modView = new ModView();
    private final AutoMods autoMods;

    private Map<JCheckBox, TargetedMod> clientMods;
    private Map<JCheckBox, TargetedMod> serverMods;
    private Map<JCheckBox, TargetedMod> dependencyMods;
    private String context;

    public AutoModsUI(AutoMods autoMods) {
        this.context = "client";
        this.autoMods = autoMods;
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
        this.setTitle("Auto Definitions");
        this.setSize(frameDimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.modsPanel.setLayout(new BoxLayout(modsPanel, BoxLayout.Y_AXIS));
        this.modsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.modsScrollPane.setPreferredSize(new Dimension(250, 600));

        clientModsBtn.addActionListener(x -> {
            context = "client";
            serverModsBtn.setSelected(false);
            showContextMods();
        });

        serverModsBtn.addActionListener(x -> {
            context = "server";
            clientModsBtn.setSelected(false);
            showContextMods();
        });

        dependencyModsBtn.addActionListener(x -> {
            context = "dependency";
            showContextMods();
        });

        refreshModsBtn.addActionListener(x -> {
            autoMods.updateMods();
        });

        downloadModsBtn.addActionListener(x -> {
            List<TargetedMod> selectedTargetedMods = new ArrayList<>();
            if (downloadClient.isSelected())
                for (Map.Entry<JCheckBox, TargetedMod> mod : clientMods.entrySet())
                    if (mod.getKey().isSelected()) selectedTargetedMods.add(mod.getValue());
            if (downloadServer.isSelected())
                for (Map.Entry<JCheckBox, TargetedMod> mod : serverMods.entrySet())
                    if (mod.getKey().isSelected()) selectedTargetedMods.add(mod.getValue());
            autoMods.downloadMods(selectedTargetedMods);
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
        left.setSize(250, 800);
        left.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        left.add(leftup);
        left.add(leftdown);

        JScrollPane modScroll = new JScrollPane(modView);
        modScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        modScroll.setPreferredSize(new Dimension(250, 600));

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setSize(250, 800);
        right.add(modScroll);

        JPanel up = new JPanel();
        up.setLayout(new GridLayout(1, 2));
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

    public void updateMods(List<TargetedMod> targetedMods) {
        clientMods.clear();
        serverMods.clear();
        for (TargetedMod targetedMod : targetedMods) {
            if (targetedMod.getContexts() != null) {
                JCheckBox checkBox = new JCheckBox(targetedMod.getName());

                checkBox.addActionListener(x -> {
                    if (serverMods.containsKey(checkBox))
                        modView.setMod(serverMods.get(checkBox));
                    else if (clientMods.containsKey(checkBox))
                        modView.setMod(clientMods.get(checkBox));
                });

                for (ModContext modContextDefinition : targetedMod.getContexts()) {
                    if (modContextDefinition.equals(ModContext.client)) clientMods.put(checkBox, targetedMod);
                    if (modContextDefinition.equals(ModContext.server)) serverMods.put(checkBox, targetedMod);
                }

                if (!targetedMod.getOptional()) {
                    checkBox.setModel(new ReadOnlyToggleButtonModel(true));
                    checkBox.setFocusable(false);
                    checkBox.setForeground(Color.gray);
                }
            }
        }
        showContextMods();
    }

    private void showContextMods() {
        Map<JCheckBox, TargetedMod> targetMods = new HashMap<>();

        switch (context) {
            case "server" -> targetMods = serverMods;
            case "client" -> targetMods = clientMods;
            case "dependency" -> targetMods = dependencyMods;
        }

        modsPanel.removeAll();
        for (Map.Entry<JCheckBox, TargetedMod> targetMod : targetMods.entrySet()) {
            modsPanel.add(targetMod.getKey());
        }
        modsPanel.updateUI();
    }
}
