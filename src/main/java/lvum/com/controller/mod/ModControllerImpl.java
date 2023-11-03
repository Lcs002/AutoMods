package lvum.com.controller.mod;

import lvum.com.model.mod.github.TargetedMod;
import lvum.com.model.mod.ModRepository;
import lvum.com.model.mod.github.definition.ModDependencyDefinition;
import lvum.com.model.mod.github.definition.ModDefinition;
import lvum.com.model.mod.github.definition.ModVersionDefinition;
import lvum.com.model.mod.github.reference.ModReference;
import lvum.com.view.AppUI;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModControllerImpl implements ModController {
    private static final Logger LOGGER = Logger.getLogger(ModControllerImpl.class.getName());
    private final AppUI view;
    private final ModRepository repository;


    public ModControllerImpl(AppUI view, ModRepository repository){
        this.view = view;
        this.repository = repository;
    }


    @Override
    public void updateMods() {
        repository.updateRepository();
        view.updateMods(getMods());
    }

    @Override
    public void downloadMods(List<TargetedMod> targetedMods) {
        List<TargetedMod> dependencies = getModDependencies(targetedMods);
        targetedMods.addAll(dependencies);
        repository.download(targetedMods);
        view.showCompletedDownload();
    }

    private List<TargetedMod> getMods() {
        List<ModDefinition> definitions = repository.getDefinitions();
        List<ModReference> references = repository.getReferences();
        List<TargetedMod> targetedMods = new ArrayList<>();
        List<String> included = new ArrayList<>();

        // For each TargetedMod referenced...
        for (ModReference modReference : references) {
            // If the mod is not already included...
            if (!included.contains(modReference.getModid())) {
                // Include the mod
                included.add(modReference.getModid());

                // Find the TargetedMod Definition
                ModDefinition modDefinition = getModDefinition(modReference.getModid(), definitions);

                // Get its target version from the Reference
                String targetVersion;
                if (modReference.getVersion() != null) targetVersion = modReference.getVersion();
                // Or get the latest version
                else targetVersion = Arrays.stream(modDefinition.getVersions()).findFirst().get().getVersion();

                // Get Target ModVersionDefinition Definition
                Optional<ModVersionDefinition> targetVersionDefinition =
                        Arrays.stream(modDefinition.getVersions())
                                .filter(x -> x.getVersion().equals(targetVersion)).findFirst();

                // If a version was found
                if (targetVersionDefinition.isPresent())
                    targetedMods.add(new TargetedMod(modDefinition, targetVersionDefinition.get()));
                // TODO Else: tell the user no version of the referenced mod could be found
            }
        }
        return targetedMods;
    }

    private ModDefinition getModDefinition(String mod, List<ModDefinition> definitions) {
        Optional<ModDefinition> modDefinition;
        modDefinition = definitions.stream().filter(x -> Objects.equals(x.getModID(), mod)).findFirst();
        LOGGER.log(Level.INFO, "    Found Definition for modID '" + mod + "'");
        return modDefinition.orElse(null);
    }

    private List<TargetedMod> getModDependencies(List<TargetedMod> targetedMods) {
        List<ModDefinition> definitions = repository.getDefinitions();
        List<TargetedMod> dependencyTargetedMods = new ArrayList<>();
        List<String> included = new ArrayList<>();

        // For each mod
        for (TargetedMod targetedMod : targetedMods) {
            // Get the target version definition of the targetedMod (the version that will be downloaded)
            ModVersionDefinition targetModVersionDefinitionDefinition = targetedMod.getTargetVersion();
            // If the version does not have any dependencies, go to next targetedMod
            if (targetModVersionDefinitionDefinition.getDependencies() != null)
                // For each TargetedMod ModDependencyDefinition...
                for (ModDependencyDefinition modDependency : targetModVersionDefinitionDefinition.getDependencies())
                    // If the modDependency is not already included for download...
                    if (!included.contains(modDependency.getModID())) {
                        // Add the modDependency in the included list
                        included.add(modDependency.getModID());
                        // Find the ModDependencyDefinition Definition
                        ModDefinition modDependencyDefinition =
                                getModDefinition(modDependency.getModID(), definitions);
                        // Find the version
                        ModVersionDefinition dependencyTargetVersion = Arrays.stream(modDependencyDefinition.getVersions())
                                        .filter(x -> x.getVersion() == modDependency.getVersion()).findAny().get();
                        dependencyTargetedMods.add(new TargetedMod(modDependencyDefinition, dependencyTargetVersion));
                    }
        }
        return dependencyTargetedMods;
    }
}
