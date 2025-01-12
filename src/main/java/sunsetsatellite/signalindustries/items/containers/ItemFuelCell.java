package sunsetsatellite.signalindustries.items.containers;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import sunsetsatellite.catalyst.fluids.api.IFluidInventory;
import sunsetsatellite.catalyst.fluids.api.IItemFluidContainer;
import sunsetsatellite.catalyst.fluids.impl.ItemInventoryFluid;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidContainer;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIItems;


public class ItemFuelCell extends Item implements IItemFluidContainer, ICustomDescription {

    public ItemFuelCell(String name, int id) {
        super(name, id);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 4000;
    }

    @Override
    public int getRemainingCapacity(ItemStack stack) {
        return 4000-(stack.getData().getInteger("fuel")+stack.getData().getInteger("depleted"));
    }

    @Override
    public boolean canFill(ItemStack stack) {
        return getRemainingCapacity(stack) > 0;
    }

    @Override
    public boolean canDrain(ItemStack stack) {
        return getCapacity(stack) > getRemainingCapacity(stack);
    }

    @Override
    public FluidStack getCurrentFluid(ItemStack stack) {
        return new FluidStack(SIBlocks.energyFlowing,getCapacity(stack));
    }

    @Override
    public void setCurrentFluid(FluidStack fluidStack, ItemStack stack) {

    }

    @Override
    public ItemStack fill(FluidStack fluidStack, ItemStack stack) {
        if(fluidStack == null){
            return null;
        }
        if(fluidStack.getLiquid() == SIBlocks.energyFlowing){
            int remaining = getRemainingCapacity(stack);
            int saturation = stack.getData().getInteger("fuel");
            int amount = fluidStack.amount;
            ItemStack cell = new ItemStack(SIItems.fuelCell,1);
            if(remaining == 0){
                return null;
            }
            if(amount > remaining){
                fluidStack.amount -= remaining;
                CompoundTag data = new CompoundTag();
                data.putInt("fuel",getCapacity(stack));
                cell.setData(data);
                return cell;
            } else {
                CompoundTag data = new CompoundTag();
                data.putInt("fuel",saturation + amount);
                cell.setData(data);
                return cell;
            }
        }
        return null;
    }

    @Override
    public ItemStack fill(FluidStack fluidStack, ItemStack stack, TileEntityFluidContainer tile) {
        return fill(fluidStack, stack);
    }

    @Override
    public ItemStack fill(FluidStack fluidStack, ItemStack stack, IFluidInventory tile) {
        return fill(fluidStack, stack);
    }

    @Override
    public ItemStack fill(FluidStack fluidStack, ItemStack stack, TileEntityFluidContainer tile, int maxAmount) {
        if(fluidStack == null){
            return null;
        }
        if(fluidStack.getLiquid() == SIBlocks.energyFlowing){
            int remaining = getRemainingCapacity(stack);
            int saturation = stack.getData().getInteger("fuel");
            int amount = Math.min(fluidStack.amount,maxAmount);
            ItemStack cell = new ItemStack(SIItems.fuelCell,1);
            if(remaining == 0) return null;
            int result = Math.min(amount,remaining);
            if(result == 0) return null;
            fluidStack.amount -= result;
            CompoundTag data = new CompoundTag();
            data.putInt("fuel", saturation+result);
            cell.setData(data);
            return cell;
        }
        return null;
    }

    @Override
    public ItemStack fill(FluidStack fluidStack, ItemStack stack, ItemInventoryFluid inv) {
        return fill(fluidStack, stack);
    }


    @Override
    public void drain(ItemStack stack, int slot, TileEntityFluidContainer tile) {
        int saturation = stack.getData().getInteger("depleted");
        int capacity = tile.getFluidCapacityForSlot(slot);
        if(saturation == 0){
            return;
        }
        FluidStack fluidStack = tile.getFluidInSlot(slot);
        if(fluidStack != null){
            int amount = fluidStack.amount;
            if(amount + saturation > capacity){
                int remainder = (amount+saturation)-capacity;
                fluidStack.amount = capacity;
                stack.getData().putInt("depleted",remainder);
            } else {
                fluidStack.amount += saturation;
                stack.getData().putInt("depleted",0);
            }
        } else {
            if(saturation > capacity){
                int remainder = saturation-capacity;
                FluidStack fluid = new FluidStack(SIBlocks.burntSignalumFlowing,capacity);
                tile.setFluidInSlot(slot,fluid);
                stack.getData().putInt("depleted",remainder);
            } else {
                FluidStack fluid = new FluidStack(SIBlocks.burntSignalumFlowing,saturation);
                tile.setFluidInSlot(slot,fluid);
                stack.getData().putInt("depleted",0);
            }
        }
    }

    @Override
    public void drain(ItemStack stack, int slot, IFluidInventory tile) {
        int saturation = stack.getData().getInteger("depleted");
        int capacity = tile.getFluidCapacityForSlot(slot);
        if(saturation == 0){
            return;
        }
        FluidStack fluidStack = tile.getFluidInSlot(slot);
        if(fluidStack != null){
            int amount = fluidStack.amount;
            if(amount + saturation > capacity){
                int remainder = (amount+saturation)-capacity;
                fluidStack.amount = capacity;
                stack.getData().putInt("depleted",remainder);
            } else {
                fluidStack.amount += saturation;
                stack.getData().putInt("depleted",0);
            }
        } else {
            if(saturation > capacity){
                int remainder = saturation-capacity;
                FluidStack fluid = new FluidStack(SIBlocks.burntSignalumFlowing,capacity);
                tile.setFluidInSlot(slot,fluid);
                stack.getData().putInt("depleted",remainder);
            } else {
                FluidStack fluid = new FluidStack(SIBlocks.burntSignalumFlowing,saturation);
                tile.setFluidInSlot(slot,fluid);
                stack.getData().putInt("depleted",0);
            }
        }
    }

    @Override
    public void drain(ItemStack stack, int slot, ItemInventoryFluid inv) {
        int saturation = stack.getData().getInteger("depleted");
        int capacity = inv.getFluidCapacityForSlot(slot);
        if(saturation == 0){
            return;
        }
        FluidStack fluidStack = inv.getFluidInSlot(slot);
        if(fluidStack != null){
            int amount = fluidStack.amount;
            if(amount + saturation > capacity){
                int remainder = (amount+saturation)-capacity;
                fluidStack.amount = capacity;
                stack.getData().putInt("depleted",remainder);
            } else {
                fluidStack.amount += saturation;
                stack.getData().putInt("depleted",0);
            }
        } else {
            if(saturation > capacity){
                int remainder = saturation-capacity;
                FluidStack fluid = new FluidStack(SIBlocks.burntSignalumFlowing,capacity);
                inv.setFluidInSlot(slot,fluid);
                stack.getData().putInt("depleted",remainder);
            } else {
                FluidStack fluid = new FluidStack(SIBlocks.burntSignalumFlowing,saturation);
                stack.getData().putInt("depleted",0);
            }
        }
    }

    @Override
    public FluidStack drain(ItemStack stack, int amount) {
        return null;
    }

    @Override
    public String getDescription(ItemStack itemStack) {
        int fuel = itemStack.getData().getInteger("fuel");
        int depleted = itemStack.getData().getInteger("depleted");
        String text = "Fuel: " +
                TextFormatting.RED +
                fuel +
                TextFormatting.WHITE +
                " | " +
                TextFormatting.BROWN +
                depleted +
                TextFormatting.WHITE +
                " Depletion: " +
                TextFormatting.LIGHT_GRAY +
                String.format("%.2f", ((float) depleted / getCapacity(itemStack)) * 100) +
                "%" +
                "\n" +
                "Capacity: " +
                TextFormatting.RED +
                getCapacity(itemStack);
        return text;
    }
}
