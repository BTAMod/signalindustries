package sunsetsatellite.signalindustries.inventories.machines;


import net.minecraft.client.Minecraft;
import net.minecraft.core.Global;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.signalindustries.SIAchievements;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.interfaces.IBoostable;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMachineSimple;
import sunsetsatellite.signalindustries.SIRecipes;


public class TileEntityAlloySmelter extends TileEntityTieredMachineSimple implements IBoostable {

    public TileEntityAlloySmelter(){
        itemContents = new ItemStack[3];
        fluidCapacity[0] = 2000;
        acceptedFluids.get(0).add(SIBlocks.energyFlowing);
        energySlot = 0;
        recipeGroup = SIRecipes.ALLOY_SMELTER;
        itemInputs = new int[]{0,2};
        itemOutputs = new int[]{1};
    }

    @Override
    public String getInvName() {
        return "Alloy Smelter";
    }

    @Override
    public void tick() {
        super.tick();
        if(getBlockType() != null){
            fluidCapacity[0] = 2000 * (((BlockContainerTiered) getBlockType()).getTier().ordinal()+1);
        }
    }

    @Override
    public void processItem() {
        super.processItem();
        if(itemContents[itemOutputs[0]].itemID == SIItems.reinforcedCrystalAlloyIngot.id && !Global.isServer){
            Minecraft.getMinecraft(this).thePlayer.triggerAchievement(SIAchievements.KNIGHTS_ALLOY);
        }
    }

}
