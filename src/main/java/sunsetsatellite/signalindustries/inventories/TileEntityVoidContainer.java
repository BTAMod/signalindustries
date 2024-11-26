package sunsetsatellite.signalindustries.inventories;

import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.CatalystFluids;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.catalyst.fluids.util.FluidStack;

import java.util.ArrayList;

public class TileEntityVoidContainer extends TileEntityFluidItemContainer {

    public TileEntityVoidContainer(){
        for (Direction dir : Direction.values()) {
            fluidContents = new FluidStack[1];
            fluidCapacity = new int[]{Integer.MAX_VALUE};
            itemContents = new ItemStack[1];
            itemConnections.put(dir, Connection.INPUT);
            fluidConnections.put(dir, Connection.INPUT);
            activeItemSlots.put(dir,0);
            activeFluidSlots.put(dir,0);
            acceptedFluids.clear();
            for (FluidStack ignored : fluidContents) {
                acceptedFluids.add(new ArrayList<>(CatalystFluids.CONTAINERS.getAllFluids()));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        extractFluids();
        worldObj.markBlockDirty(x,y,z);
        fluidContents[0] = null;
        itemContents[0] = null;
    }
}
