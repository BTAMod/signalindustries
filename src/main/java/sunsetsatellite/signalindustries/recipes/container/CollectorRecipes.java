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

public class CollectorRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachineFluid>> {
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachineFluid> group) {
        group.register(
                "basic",
                new RecipeEntryMachineFluid(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.signalumCrystal))
                        },
                        new FluidStack(SIBlocks.energyFlowing,1),
                        new RecipeProperties(10, Tier.BASIC,true))
        );
        group.register(
                "reinforced",
                new RecipeEntryMachineFluid(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.signalumCrystal))
                        },
                        new FluidStack(SIBlocks.energyFlowing,2),
                        new RecipeProperties(10, Tier.REINFORCED,true))
        );
        group.register(
                "reinforced_aw_frag",
                new RecipeEntryMachineFluid(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new ItemStack(SIItems.awakenedSignalumFragment))
                        },
                        new FluidStack(SIBlocks.energyFlowing,4),
                        new RecipeProperties(10, Tier.REINFORCED,true))
        );
    }
}
