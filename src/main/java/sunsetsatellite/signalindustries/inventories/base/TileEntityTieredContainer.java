package sunsetsatellite.signalindustries.inventories.base;

import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.util.Tier;

public class TileEntityTieredContainer extends TileEntityCoverable {
    public Tier tier = Tier.PROTOTYPE;

    @Override
    public void tick() {
        super.tick();
        if(worldObj != null && getBlockType() != null){
            tier = ((BlockContainerTiered)getBlockType()).tier;
        }
    }

    public Tier getTier() {
        return tier;
    }
}
