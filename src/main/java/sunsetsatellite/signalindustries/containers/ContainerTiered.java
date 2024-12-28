package sunsetsatellite.signalindustries.containers;


import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.fluids.impl.ContainerFluid;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredContainer;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMachineBase;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.List;

public class ContainerTiered extends ContainerFluid {
    public ContainerTiered(IInventory iInventory, TileEntityTieredContainer tileEntityTieredMachine) {
        super(iInventory, tileEntityTieredMachine);
    }

    protected TileEntityTieredContainer tile = (TileEntityTieredContainer) super.tile;
    protected TileEntityTieredMachineBase machine = (TileEntityTieredMachineBase) tile;
    public Tier tier = Tier.PROTOTYPE;

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        int lastDeviceSlot = tile.getSizeInventory() - 1;
        if (slot.id <= lastDeviceSlot) {
            return getSlots(lastDeviceSlot+1, 36, true);
        }
        return getSlots(0, Math.max(lastDeviceSlot+1,1), false);
    }
}
