package sunsetsatellite.signalindustries.recipes.container.waking;

import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.RecipeExtendedSymbol;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.recipes.container.MachineRecipesBase;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

public class WakingAlloySmelterRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        group.register(
                "steel_ingot",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(Item.ingotIron)),
                                new RecipeExtendedSymbol(new ItemStack(SIItems.tinyNetherCoalDust))
                        },
                        new ItemStack(Item.ingotSteel, 1),
                        new RecipeProperties(100, 40, Tier.REINFORCED, false)
                )
        );
        group.register(
                "crystal_alloy_ingot_2",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(Item.ingotSteel)),
                                new RecipeExtendedSymbol(new ItemStack(SIItems.emptySignalumCrystalDust))
                        },
                        new ItemStack(SIItems.crystalAlloyIngot, 2),
                        new RecipeProperties(100, 40, Tier.REINFORCED, false)
                )
        );
        group.register(
                "reinforced_crystal_alloy_ingot",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.crystalAlloyIngot)),
                                new RecipeExtendedSymbol(new ItemStack(SIBlocks.glowingObsidian, 2))
                        },
                        new ItemStack(SIItems.reinforcedCrystalAlloyIngot, 1),
                        new RecipeProperties(100, 80, Tier.REINFORCED, false)
                )
        );
        group.register(
                "condensed_milk_can",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(Item.bucketMilk)),
                                new RecipeExtendedSymbol(new ItemStack(Item.dustSugar, 8))
                        },
                        new ItemStack(SIItems.condensedMilkCan, 1),
                        new RecipeProperties(50, 20, Tier.REINFORCED, false).setConsumeContainers()
                )
        );
        group.register(
                "caramel_bucket",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.condensedMilkCan)),
                                new RecipeExtendedSymbol(new ItemStack(Item.dustSugar, 4))
                        },
                        new ItemStack(SIItems.bucketCaramel, 1),
                        new RecipeProperties(50, 20, Tier.REINFORCED, false).setConsumeContainers()
                )
        );
        group.register(
                "void_alloy_ingot",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.reinforcedCrystalAlloyIngot, 1)),
                                new RecipeExtendedSymbol(new ItemStack(SIItems.realityString, 4))
                        },
                        new ItemStack(SIItems.voidAlloyIngot, 1),
                        new RecipeProperties(300, 120, Tier.REINFORCED, false)
                )
        );
    }
}
