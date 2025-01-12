package sunsetsatellite.signalindustries;

import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.signalindustries.api.impl.catalyst.fluid.SIFluids;
import sunsetsatellite.signalindustries.entities.mob.EntityInfernal;
import sunsetsatellite.signalindustries.recipes.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.RecipeNamespaceSI;
import sunsetsatellite.signalindustries.recipes.container.*;
import sunsetsatellite.signalindustries.recipes.container.waking.WakingAlloySmelterRecipes;
import sunsetsatellite.signalindustries.recipes.container.waking.WakingCrusherRecipes;
import sunsetsatellite.signalindustries.recipes.container.waking.WakingInfuserRecipes;
import sunsetsatellite.signalindustries.recipes.container.waking.WakingPlateFormerRecipes;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachineFluid;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static sunsetsatellite.catalyst.Catalyst.listOf;
import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;
import static sunsetsatellite.signalindustries.SignalIndustries.MOD_ID;

public class SIRecipes implements RecipeEntrypoint {
    public static RecipeNamespaceSI SIGNAL_INDUSTRIES = new RecipeNamespaceSI();
    public static RecipeGroup<RecipeEntryCrafting<?,?>> WORKBENCH;
    public static RecipeGroup<RecipeEntryFurnace> FURNACE;
    public static RecipeGroupSI<RecipeEntryMachineFluid> EXTRACTOR;
    public static RecipeGroupSI<RecipeEntryMachine> CRUSHER;
    public static RecipeGroupSI<RecipeEntryMachine> WAKING_CRUSHER;
    public static RecipeGroupSI<RecipeEntryMachine> ALLOY_SMELTER;
    public static RecipeGroupSI<RecipeEntryMachine> WAKING_ALLOY_SMELTER;
    public static RecipeGroupSI<RecipeEntryMachine> PLATE_FORMER;
    public static RecipeGroupSI<RecipeEntryMachine> WAKING_PLATE_FORMER;
    public static RecipeGroupSI<RecipeEntryMachineFluid> PUMP;
    public static RecipeGroupSI<RecipeEntryMachine> STONEWORKS;
    public static RecipeGroupSI<RecipeEntryMachine> CRYSTAL_CUTTER;
    public static RecipeGroupSI<RecipeEntryMachine> CRYSTAL_CHAMBER;
    public static RecipeGroupSI<RecipeEntryMachine> INFUSER;
    public static RecipeGroupSI<RecipeEntryMachine> WAKING_INFUSER;
    public static RecipeGroupSI<RecipeEntryMachine> CENTRIFUGE;
    public static RecipeGroupSI<RecipeEntryMachineFluid> COLLECTOR;
    public static RecipeGroupSI<RecipeEntryMachine> INDUCTION_SMELTER;

    @Override
    public void onRecipesReady() {
        new SIFluids().initializePlugin(LOGGER);
        resetGroups();
        registerNamespaces();
        load();
        MobInfoRegistry.register(EntityInfernal.class,"guidebook.section.mob.infernal.name", "guidebook.section.mob.infernal.desc",40,1000,new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(SIItems.infernalFragment),1,0,2)});

    }

    public void resetGroups(){
        SIGNAL_INDUSTRIES = new RecipeNamespaceSI();
        WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));
        FURNACE = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.furnaceStoneIdle)));
        EXTRACTOR = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.prototypeExtractor), new ItemStack(SIBlocks.basicExtractor), new ItemStack(SIBlocks.reinforcedExtractor))));
        CRUSHER = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.prototypeCrusher),new ItemStack(SIBlocks.basicCrusher),new ItemStack(SIBlocks.reinforcedCrusher))));
        ALLOY_SMELTER = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.prototypeAlloySmelter),new ItemStack(SIBlocks.basicAlloySmelter),new ItemStack(SIBlocks.reinforcedAlloySmelter))));
        PLATE_FORMER = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.prototypePlateFormer),new ItemStack(SIBlocks.basicPlateFormer),new ItemStack(SIBlocks.reinforcedPlateFormer))));
        PUMP = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.prototypePump),new ItemStack(SIBlocks.basicPump))));
        STONEWORKS = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.basicStoneworks))));
        CRYSTAL_CUTTER = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.prototypeCrystalCutter),new ItemStack(SIBlocks.basicCrystalCutter),new ItemStack(SIBlocks.reinforcedCrystalCutter))));
        CRYSTAL_CHAMBER = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.basicCrystalChamber),new ItemStack(SIBlocks.reinforcedCrystalChamber))));
        INFUSER = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.basicInfuser),new ItemStack(SIBlocks.reinforcedInfuser))));
        CENTRIFUGE = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.reinforcedCentrifuge))));
        COLLECTOR = new RecipeGroupSI<>(new RecipeSymbol(Arrays.asList(new ItemStack(SIBlocks.basicCollector),new ItemStack(SIBlocks.reinforcedCollector))));
        WAKING_CRUSHER = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.wakingCrusher))));
        WAKING_PLATE_FORMER = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.wakingPlateFormer))));
        WAKING_ALLOY_SMELTER = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.wakingAlloySmelter))));
        WAKING_INFUSER = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.wakingInfuser))));
        INDUCTION_SMELTER = new RecipeGroupSI<>(new RecipeSymbol(Collections.singletonList(new ItemStack(SIBlocks.basicInductionSmelter))));
    }

    public void load(){
        Registries.RECIPE_TYPES.register("signalindustries:machine", RecipeEntryMachine.class);
        Registries.RECIPE_TYPES.register("signalindustries:machine/fluid", RecipeEntryMachineFluid.class);
        /*List<ItemStack> abilityGroup = new ArrayList<>();
        abilityGroup.add(SIItems.boostAbilityContainer.getDefaultStack());
        abilityGroup.add(SIItems.projectileAbilityContainer.getDefaultStack());
        RecipeBuilder.addItemsToGroup(MOD_ID,"ability_containers",abilityGroup.toArray());*/
        List<ItemStack> romChipGroup = new ArrayList<>();
        romChipGroup.add(SIItems.romChipBoost.getDefaultStack());
        romChipGroup.add(SIItems.romChipProjectile.getDefaultStack());
        RecipeBuilder.addItemsToGroup(MOD_ID,"rom_chips",romChipGroup.toArray());
        RecipeBuilder.addItemsToGroup("common_plates","cobblestone",SIItems.cobblestonePlate);
        RecipeBuilder.addItemsToGroup("common_plates","stone",SIItems.stonePlate);
        RecipeBuilder.addItemsToGroup("common_plates","steel",SIItems.steelPlate);
        RecipeBuilder.addItemsToGroup("common_dusts","coal",SIItems.coalDust);
        RecipeBuilder.addItemsToGroup("common_dusts","iron",SIItems.ironDust);
        RecipeBuilder.addItemsToGroup("common_dusts","gold",SIItems.goldDust);
        RecipeBuilder.addItemsToGroup("common_dusts","nether_coal",SIItems.netherCoalDust);
        RecipeBuilder.addItemsToGroup("common_tiny_dusts","nether_coal",SIItems.tinyNetherCoalDust);
        Registries.ITEM_GROUPS.register("minecraft:water", listOf(new ItemStack(Block.fluidWaterFlowing),new ItemStack(Block.fluidWaterStill)));
        Registries.ITEM_GROUPS.register("minecraft:lava", listOf(new ItemStack(Block.fluidLavaFlowing),new ItemStack(Block.fluidLavaStill)));
        new ExtractorRecipes().addRecipes(EXTRACTOR);
        new CrusherRecipes().addRecipes(CRUSHER);
        new AlloySmelterRecipes().addRecipes(ALLOY_SMELTER);
        new PlateFormerRecipes().addRecipes(PLATE_FORMER);
        new PumpRecipes().addRecipes(PUMP);
        new StoneworksRecipes().addRecipes(STONEWORKS);
        new CrystalCutterRecipes().addRecipes(CRYSTAL_CUTTER);
        new CrystalChamberRecipes().addRecipes(CRYSTAL_CHAMBER);
        new InfuserRecipes().addRecipes(INFUSER);
        new CentrifugeRecipes().addRecipes(CENTRIFUGE);
        new CollectorRecipes().addRecipes(COLLECTOR);
        new WakingCrusherRecipes().addRecipes(WAKING_CRUSHER);
        new WakingPlateFormerRecipes().addRecipes(WAKING_PLATE_FORMER);
        new WakingAlloySmelterRecipes().addRecipes(WAKING_ALLOY_SMELTER);
        new WakingInfuserRecipes().addRecipes(WAKING_INFUSER);
        DataLoader.loadRecipesFromFile("/assets/signalindustries/recipes/workbench.json");
        DataLoader.loadRecipesFromFile("/assets/signalindustries/recipes/workbench_prototype.json");
        DataLoader.loadRecipesFromFile("/assets/signalindustries/recipes/workbench_basic.json");
        DataLoader.loadRecipesFromFile("/assets/signalindustries/recipes/workbench_reinforced.json");
        DataLoader.loadRecipesFromFile("/assets/signalindustries/recipes/workbench_awakened.json");
        DataLoader.loadRecipesFromFile("/assets/signalindustries/recipes/furnace.json");
        LOGGER.info("{} recipes in {} groups.", SIGNAL_INDUSTRIES.getAllRecipes().size(), SIGNAL_INDUSTRIES.size());
    }

    public static void loadSpecial(){
        new InductionSmelterRecipes().addRecipes(SIRecipes.INDUCTION_SMELTER);
    }

    @Override
    public void initNamespaces() {
        resetGroups();
        registerNamespaces();
    }

    public void registerNamespaces(){
        SIGNAL_INDUSTRIES.register("workbench",WORKBENCH);
        SIGNAL_INDUSTRIES.register("furnace",FURNACE);
        SIGNAL_INDUSTRIES.register("extractor",EXTRACTOR);
        SIGNAL_INDUSTRIES.register("crusher",CRUSHER);
        SIGNAL_INDUSTRIES.register("alloy_smelter",ALLOY_SMELTER);
        SIGNAL_INDUSTRIES.register("plate_former",PLATE_FORMER);
        SIGNAL_INDUSTRIES.register("pump",PUMP);
        SIGNAL_INDUSTRIES.register("stoneworks",STONEWORKS);
        SIGNAL_INDUSTRIES.register("crystal_cutter",CRYSTAL_CUTTER);
        SIGNAL_INDUSTRIES.register("crystal_chamber",CRYSTAL_CHAMBER);
        SIGNAL_INDUSTRIES.register("infuser",INFUSER);
        SIGNAL_INDUSTRIES.register("centrifuge",CENTRIFUGE);
        SIGNAL_INDUSTRIES.register("collector",COLLECTOR);
        SIGNAL_INDUSTRIES.register("waking_crusher",WAKING_CRUSHER);
        SIGNAL_INDUSTRIES.register("waking_plate_former",WAKING_PLATE_FORMER);
        SIGNAL_INDUSTRIES.register("waking_alloy_smelter",WAKING_ALLOY_SMELTER);
        SIGNAL_INDUSTRIES.register("waking_infuser",WAKING_INFUSER);
        SIGNAL_INDUSTRIES.register("induction_smelter",INDUCTION_SMELTER);
        Registries.RECIPES.register("signalindustries",SIGNAL_INDUSTRIES);
    }

}
