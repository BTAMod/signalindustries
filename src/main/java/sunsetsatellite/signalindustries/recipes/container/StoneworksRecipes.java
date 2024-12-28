package sunsetsatellite.signalindustries.recipes.container;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.RecipeExtendedSymbol;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

public class StoneworksRecipes implements MachineRecipesBase<RecipeGroup<RecipeEntryMachine>>{
    @Override
    public void addRecipes(RecipeGroup<RecipeEntryMachine> group) {
        group.register(
                "obsidian",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,500)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,500)),
                        },
                        new ItemStack(Block.obsidian,1),
                        new RecipeProperties(100,20, 0, Tier.BASIC,false)
                )
        );
        group.register(
                "cobblestone",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.cobbleStone,1),
                        new RecipeProperties(10,20, 1, Tier.BASIC,false)
                )
        );
        group.register(
                "stone",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.stone,1),
                        new RecipeProperties(10,20, 2, Tier.BASIC,false)
                )
        );
        group.register(
                "basalt",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.basalt,1),
                        new RecipeProperties(10,20, 3, Tier.BASIC,false)
                )
        );
        group.register(
                "granite",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.granite,1),
                        new RecipeProperties(10,20, 4, Tier.BASIC,false)
                )
        );
        group.register(
                "limestone",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.limestone,1),
                        new RecipeProperties(10,20, 5, Tier.BASIC,false)
                )
        );
        group.register(
                "permafrost",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,1000))
                        },
                        new ItemStack(Block.permafrost,1),
                        new RecipeProperties(100,20, 6, Tier.BASIC,false)
                )
        );
        group.register(
                "cobble_basalt",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.cobbleBasalt,1),
                        new RecipeProperties(10,20, 7, Tier.BASIC,false)
                )
        );
        group.register(
                "cobble_granite",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.cobbleGranite,1),
                        new RecipeProperties(10,20, 8, Tier.BASIC,false)
                )
        );
        group.register(
                "cobble_limestone",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,0)),
                        },
                        new ItemStack(Block.cobbleLimestone,1),
                        new RecipeProperties(10,20, 9, Tier.BASIC,false)
                )
        );
        group.register(
                "cobble_permafrost",
                new RecipeEntryMachine(
                        new RecipeExtendedSymbol[]{
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidLavaFlowing,0)),
                                new RecipeExtendedSymbol(new FluidStack((BlockFluid) Block.fluidWaterFlowing,1000))
                        },
                        new ItemStack(Block.cobblePermafrost,1),
                        new RecipeProperties(100,20, 10, Tier.BASIC,false)
                )
        );
    }
}
