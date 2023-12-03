package sunsetsatellite.signalindustries.inventories;

import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.energy.impl.ItemEnergyContainer;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.SignalIndustries;

public class TileEntitySignalumDynamo extends TileEntityTieredEnergyConductor {

    public TileEntitySignalumDynamo(){
        cost = 40;
        itemContents = new ItemStack[2];
        fluidContents = new FluidStack[1];
        fluidCapacity[0] = 4000;
        acceptedFluids.get(0).add((BlockFluid) SignalIndustries.energyFlowing);
        setCapacity(10000);
        setEnergy(0);
        setTransfer(250);
        for (Direction dir : Direction.values()) {
            setConnection(dir, Connection.OUTPUT);
        }
    }

    @Override
    public void tick() {
        super.tick();
        boolean update = false;
        if(fuelBurnTicks > 0){
            fuelBurnTicks--;
        }

        if(!worldObj.isClientSide){
            if(isBurning() && canProcess()){
                modifyEnergy(20);
            } else if(canProcess()){
                fuel();
                if(fuelBurnTicks > 0){
                    fuelBurnTicks++;
                }
            }
        }

        if(getStackInSlot(1) != null && getStackInSlot(1).getItem() instanceof ItemEnergyContainer){
            ItemStack stack = getStackInSlot(1);
            ItemEnergyContainer item = (ItemEnergyContainer) getStackInSlot(1).getItem();
            provide(stack,getMaxProvide(),false);
            onInventoryChanged();
        }
        if(getStackInSlot(0) != null && getStackInSlot(0).getItem() instanceof ItemEnergyContainer) {
            ItemStack stack = getStackInSlot(0);
            ItemEnergyContainer item = (ItemEnergyContainer) getStackInSlot(0).getItem();
            receive(stack,getMaxReceive(),false);
            onInventoryChanged();
        }

    }

    public boolean fuel(){
        int burn = SignalIndustries.getEnergyBurnTime(fluidContents[0]);
        if(burn > 0 && canProcess() && fluidContents[0].amount >= cost){
            fuelMaxBurnTicks = fuelBurnTicks = burn;
            fluidContents[0].amount -= cost;
            if(fluidContents[0].amount == 0) {
                fluidContents[0] = null;
            }
            return true;
        }
        return false;
    }

    private boolean canProcess() {
        if(fluidContents[0] == null) {
            return false;
        } else {
            return fluidContents[0].getLiquid() == SignalIndustries.energyFlowing && fluidContents[0].amount >= cost && getEnergy() < getCapacity();
        }
    }

}
