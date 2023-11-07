package lvum.com.app.view.gui;

import lvum.com.app.controller.mod.ModController;
import lvum.com.app.model.mod_definition.ModDefinitionContext;
import lvum.com.app.view.AutoModsViewComponent;
import lvum.com.app.view.ModInfo;
import lvum.com.utils.FolderFinder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoModsGUI extends JFrame implements AutoModsViewComponent {
    private static final String MESSAGE_TITLE = "Main - CaposMods";
    private static final String INITIAL_MESSAGE =
            """
            Do you wish do download the latest necessary YMLDefinitions for 'caposingenieros.aternos.me:23447'?
            All files will be downloaded to Appdata/Roaming/.minecraft/ModDefinition.
            No local files will be removed, moved or changed during this process.
            """;

    private static final String SUCCESS_MESSAGE =
            """
            Download Complete!
            The following YMLDefinitions were installed:%s       
            """;
    private static final String ERROR_MESSAGE =
            """
            The following YMLDefinitions couldn't be downloaded:%s
            Make sure they are not already on 'ModDefinition' folder.
            """;
    private final Dimension frameDimension;
    private final JPanel modsPanel;
    private final JScrollPane modsScrollPane;
    private final JCheckBox downloadClient;
    private final JCheckBox downloadServer;
    private final JToggleButton clientModsBtn;
    private final JToggleButton serverModsBtn;
    private final JButton refreshModsBtn;
    private final JButton downloadModsBtn;
    private final JButton dependencyModsBtn;
    private final JButton destinationBtn;
    private final JFileChooser destinationFileChooser;
    private final ModInfoGUI modInfoGUI;
    private ModController modController;

    private Map<JCheckBox, ModInfo> clientMods;
    private Map<JCheckBox, ModInfo> serverMods;
    private Map<JCheckBox, ModInfo> dependencyMods;
    private String context;
    private String downloadDestination;

    public AutoModsGUI() {
        this.context = "client";
        this.serverMods = new HashMap<>();
        this.clientMods = new HashMap<>();
        this.dependencyMods = new HashMap<>();
        setLookAndFeel();
        this.frameDimension = new Dimension(600, 800);
        modsPanel = new JPanel();
        modsScrollPane = new JScrollPane(modsPanel);
        downloadClient = new JCheckBox("Download Client Mods");
        downloadServer = new JCheckBox("Download Server Mods");
        clientModsBtn = new JToggleButton("Client");
        serverModsBtn = new JToggleButton("Server");
        refreshModsBtn = new JButton("Refresh");
        downloadModsBtn = new JButton("Download");
        dependencyModsBtn = new JButton("Dependencies");
        destinationBtn = new JButton("Destination");
        destinationFileChooser = new JFileChooser();
        modInfoGUI = new ModInfoGUI();
        initializeUI();
        this.setVisible(true);
    }

    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch (Exception e) {
            System.out.println("Unable to set LookAndFeel");
        }
    }

    private void initializeUI(){
        this.setTitle("Auto YMLDefinitions");
        this.setSize(frameDimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.modsPanel.setLayout(new BoxLayout(modsPanel, BoxLayout.Y_AXIS));
        this.modsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.modsScrollPane.setPreferredSize(new Dimension(250, 600));
        this.destinationFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

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

        destinationBtn.addActionListener(x -> {
            int val = destinationFileChooser.showDialog(this, "Select");
            if (val == JFileChooser.APPROVE_OPTION) {
                File folder = destinationFileChooser.getSelectedFile();
                downloadDestination = folder.getAbsolutePath();
            }
        });

        refreshModsBtn.addActionListener(x -> {
            modController.updateMods();
        });

        downloadModsBtn.addActionListener(x -> {
            List<String> modDownloadTargets = new ArrayList<>();
            List<ModDefinitionContext> contexts = new ArrayList<>();
            if (downloadClient.isSelected()) {
                contexts.add(ModDefinitionContext.client);
                for (Map.Entry<JCheckBox, ModInfo> mod : clientMods.entrySet()) {
                    ModInfo modInfo = mod.getValue();
                    if (mod.getKey().isSelected() && modInfo.getOptional())
                        modDownloadTargets.add(mod.getValue().getModID());
                }
            }

            if (downloadServer.isSelected()) {
                contexts.add(ModDefinitionContext.server);
                for (Map.Entry<JCheckBox, ModInfo> mod : serverMods.entrySet()) {
                    ModInfo modInfo = mod.getValue();
                    if (mod.getKey().isSelected() && modInfo.getOptional())
                        modDownloadTargets.add(mod.getValue().getModID());
                }
            }
            if (downloadDestination == null) downloadDestination = FolderFinder.getAppDataMincraftModsFolder();
            modController.setDownloadDestination(downloadDestination);
            modController.download(contexts, modDownloadTargets, false);
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

        JScrollPane modScroll = new JScrollPane(modInfoGUI);
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
        down.add(destinationBtn);
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

    @Override
    public void updateMods(List<ModInfo> modsInfo) {
        System.out.println(modsInfo);
        clientMods.clear();
        serverMods.clear();
        for (ModInfo modInfo : modsInfo) {
            if (modInfo.getContexts() != null) {
                JCheckBox checkBox = new JCheckBox(modInfo.getName());

                checkBox.addActionListener(x -> {
                    if (serverMods.containsKey(checkBox))
                        modInfoGUI.setMod(serverMods.get(checkBox));
                    else if (clientMods.containsKey(checkBox))
                        modInfoGUI.setMod(clientMods.get(checkBox));
                });

                for (ModDefinitionContext modDefinitionContextDefinition : modInfo.getContexts()) {
                    if (modDefinitionContextDefinition.equals(ModDefinitionContext.client)) clientMods.put(checkBox, modInfo);
                    if (modDefinitionContextDefinition.equals(ModDefinitionContext.server)) serverMods.put(checkBox, modInfo);
                }

                if (!modInfo.getOptional()) {
                    checkBox.setModel(new ReadOnlyToggleButtonModel(true));
                    checkBox.setFocusable(false);
                    checkBox.setForeground(Color.gray);
                }
            }
        }
        showContextMods();
    }

    private void showContextMods() {
        Map<JCheckBox, ModInfo> targetMods = new HashMap<>();

        switch (context) {
            case "server" -> targetMods = serverMods;
            case "client" -> targetMods = clientMods;
            case "dependency" -> targetMods = dependencyMods;
        }

        modsPanel.removeAll();
        for (Map.Entry<JCheckBox, ModInfo> targetMod : targetMods.entrySet()) {
            modsPanel.add(targetMod.getKey());
        }
        modsPanel.updateUI();
    }

    @Override
    public void showCompletedDownload() {
        JOptionPane.showMessageDialog(this, "Download Complete!");
    }

    @Override
    public void setModController(ModController modController) {
        this.modController = modController;
    }
}
