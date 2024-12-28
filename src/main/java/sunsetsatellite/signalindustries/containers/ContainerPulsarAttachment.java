package sunsetsatellite.signalindustries.containers;


import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.fluids.impl.ContainerItemFluid;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.inventories.item.InventoryPulsar;
import sunsetsatellite.signalindustries.items.attachments.ItemPulsarAttachment;
import sunsetsatellite.catalyst.fluids.util.NBTHelper;

import java.util.List;

public class ContainerPulsarAttachment extends ContainerItemFluid {

    ItemStack pulsar;

    public ContainerPulsarAttachment(InventoryPlayer inventoryPlayer, ItemStack pulsar){
        super(inventoryPlayer,new InventoryPulsar(pulsar));
        this.pulsar = pulsar;

        if(pulsar.getItem() instanceof ItemPulsarAttachment){
            NBTHelper.loadInvFromNBT(pulsar,inv,1,1);
        }

        addSlot(new Slot(inv,0,80,33));

        //addFluidSlot(new SlotFluid(inv,0,80,55));

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
    public FluidStack clickFluidSlot(int slotID, int button, boolean shift, boolean control, EntityPlayer entityplayer) {
        return super.clickFluidSlot(slotID, button, shift, control, entityplayer);
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        int lastDeviceSlot = inv.getSizeInventory() - 1;
        if (slot.id <= lastDeviceSlot) {
            return getSlots(lastDeviceSlot+1, 36, true);
        }
        return getSlots(0, Math.max(lastDeviceSlot+1,1), false);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

}
