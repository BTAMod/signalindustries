package sunsetsatellite.signalindustries.containers;


import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.catalyst.fluids.util.SlotFluid;
import sunsetsatellite.signalindustries.inventories.machines.TileEntityStoneworks;

public class ContainerStoneworks extends ContainerMachine {

    private final TileEntityStoneworks machine = ((TileEntityStoneworks) tile);

    public ContainerStoneworks(IInventory iInventory, TileEntityFluidItemContainer tileEntity){
        super(iInventory, tileEntity);
        tile = tileEntity;

        SlotFluid slot = new SlotFluid(tileEntity, 0, 56,53); //116 35
        addFluidSlot(slot);

        this.addFluidSlot(new SlotFluid(tileEntity, 1, 56, 17));
        this.addSlot(new Slot(tileEntity, 0, 116, 35)); //56 53
        this.addFluidSlot(new SlotFluid(tileEntity, 2, 28, 17));

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(iInventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(iInventory, k, 8 + k * 18, 142));
        }
    }

    

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }
}