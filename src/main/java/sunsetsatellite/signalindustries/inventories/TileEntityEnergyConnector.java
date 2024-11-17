package sunsetsatellite.signalindustries.inventories;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.interfaces.IMultiblockPart;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredContainer;
import sunsetsatellite.signalindustries.inventories.machines.multiblocks.reinforced.TileEntitySignalumReactor;


public class TileEntityEnergyConnector extends TileEntityTieredContainer implements IMultiblockPart {

    public TileEntity connectedTo;

    public TileEntityEnergyConnector(){
        itemContents = new ItemStack[1];
        fluidContents = new FluidStack[1];
        fluidCapacity = new int[1];
        fluidCapacity[0] = 16000;
        acceptedFluids.get(0).add(SIBlocks.energyFlowing);
        fluidConnections.put(Direction.Y_POS, Connection.OUTPUT);
    }

    @Override
    public void tick() {
        super.tick();
        if(isConnected()){
            if(connectedTo instanceof TileEntitySignalumReactor){
                if(((TileEntitySignalumReactor) connectedTo).state != TileEntitySignalumReactor.State.INACTIVE){
                    extractFluids();
                }
            } else {
                extractFluids();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return connectedTo != null;
    }

    @Override
    public TileEntity getConnectedTileEntity() {
        return connectedTo;
    }

    @Override
    public boolean connect(TileEntity tileEntity) {
        connectedTo = tileEntity;
        return true;
    }
}
