package sunsetsatellite.signalindustries.containers;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.fluids.impl.ContainerFluid;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidItemContainer;

import java.util.List;

public abstract class ContainerMachine extends ContainerFluid {
    public ContainerMachine(IInventory iInventory, TileEntityFluidItemContainer tileEntityFluidItemContainer) {
        super(iInventory, tileEntityFluidItemContainer);
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        int lastDeviceSlot = tile.getSizeInventory() - 1;
        if (slot.id <= lastDeviceSlot) {
            return getSlots(lastDeviceSlot+1, 36, true);
        }
        return getSlots(0, Math.max(lastDeviceSlot+1,1), false);
    }
}
