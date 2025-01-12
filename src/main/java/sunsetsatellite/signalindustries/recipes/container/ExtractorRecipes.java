package sunsetsatellite.signalindustries.recipes.container;

import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachineFluid;
import sunsetsatellite.catalyst.fluids.util.RecipeExtendedSymbol;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

public class ExtractorRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachineFluid>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachineFluid> group) {
        group.register(
                "prototype",
                new RecipeEntryMachineFluid(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.rawSignalumCrystal))
                        },
                        new FluidStack(SIBlocks.energyFlowing,160),
                        new RecipeProperties(200, Tier.PROTOTYPE,true))
        );
        group.register(
                "basic",
                new RecipeEntryMachineFluid(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.rawSignalumCrystal))
                        },
                        new FluidStack(SIBlocks.energyFlowing,240),
                        new RecipeProperties(100, Tier.BASIC,true))
        );
        group.register(
                "reinforced",
                new RecipeEntryMachineFluid(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.rawSignalumCrystal,4))
                        },
                        new FluidStack(SIBlocks.energyFlowing,480*4),
                        new RecipeProperties(200, Tier.REINFORCED,true))
        );
    }
}
