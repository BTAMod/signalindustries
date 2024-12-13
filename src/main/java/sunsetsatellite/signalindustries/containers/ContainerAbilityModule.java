package sunsetsatellite.signalindustries.containers;


import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.fluids.impl.ContainerItemFluid;
import sunsetsatellite.catalyst.fluids.impl.ItemInventoryFluid;
import sunsetsatellite.signalindustries.items.attachments.ItemAbilityModule;
import sunsetsatellite.catalyst.fluids.util.NBTHelper;
import sunsetsatellite.signalindustries.util.SlotApplication;

import java.util.List;

public class ContainerAbilityModule extends ContainerItemFluid {
    public ContainerAbilityModule(IInventory inventoryPlayer, ItemInventoryFluid inv) {
        super(inventoryPlayer, inv);

        NBTHelper.loadInvFromNBT(inv.item,inv,9,0);

        for(int k = 0; k < 9; k++)
        {
            addSlot(new SlotApplication(inv, k, 8 + k * 18, 32, ((ItemAbilityModule)(inv.item.getItem())).getTier()));
        }

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(inventoryPlayer, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(inventoryPlayer, k, 8 + k * 18, 142));
        }
    }


    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        int lastDeviceSlot = inv.getSizeInventory() - 1;
        if (slot.id <= lastDeviceSlot) {
            return getSlots(lastDeviceSlot+1, 36, true);
        }
        return getSlots(0, Math.max(lastDeviceSlot+1,1), false);
    }
}
