package sunsetsatellite.signalindustries.inventories.machines;


import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIRecipes;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.interfaces.IBoostable;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMachineSimple;

import java.util.ArrayList;


public class TileEntityStoneworks extends TileEntityTieredMachineSimple implements IBoostable {

    public TileEntityStoneworks(){
        itemContents = new ItemStack[1];
        fluidContents = new FluidStack[3];
        fluidCapacity = new int[3];
        acceptedFluids.clear();
        for (FluidStack ignored : fluidContents) {
            acceptedFluids.add(new ArrayList<>());
        }
        fluidCapacity[0] = 2000;
        fluidCapacity[1] = 2000;
        fluidCapacity[2] = 2000;
        acceptedFluids.get(0).add(SIBlocks.energyFlowing);
        acceptedFluids.get(1).add((BlockFluid) Block.fluidWaterFlowing);
        acceptedFluids.get(2).add((BlockFluid) Block.fluidLavaFlowing);
        energySlot = 0;
        recipeGroup = SIRecipes.STONEWORKS;
        fluidInputs = new int[]{1,2};
        itemOutputs = new int[]{0};
    }

    @Override
    public String getInvName() {
        return "Stoneworks";
    }

    @Override
    public void tick() {
        super.tick();
        if(getBlockType() != null){
            fluidCapacity[0] = 2000 * (((BlockContainerTiered) getBlockType()).getTier().ordinal()+1);
            fluidCapacity[1] = 2000 * (((BlockContainerTiered) getBlockType()).getTier().ordinal()+1);
            fluidCapacity[2] = 2000 * (((BlockContainerTiered) getBlockType()).getTier().ordinal()+1);
        }
    }
}
