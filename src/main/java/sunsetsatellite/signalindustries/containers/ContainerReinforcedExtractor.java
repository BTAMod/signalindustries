package sunsetsatellite.signalindustries.containers;

import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredContainer;

public class ContainerReinforcedExtractor extends ContainerMachine {
    public ContainerReinforcedExtractor(IInventory iInventory, TileEntityTieredContainer tile) {
        super(iInventory, tile);
        this.tile = tile;
        this.addSlot(new Slot(tile, 0, 80, 35));

        int k;
        for(k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(iInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(iInventory, k, 8 + k * 18, 142));
        }
    }


}
