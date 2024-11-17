package sunsetsatellite.signalindustries.inventories.machines.multiblocks.waking;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.SIRecipes;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMultiblock;
import sunsetsatellite.signalindustries.util.Tier;

public class TileEntityWakingAlloySmelter extends TileEntityTieredMultiblock {
    @Override
    public void init(Block block) {
        usesEnergy = true;
        usesItemInput = true;
        usesItemOutput = true;
        minimumEnergyTier = Tier.AWAKENED;
        minimumItemInputTier = Tier.REINFORCED;
        minimumItemOutputTier = Tier.REINFORCED;

        recipeGroup = SIRecipes.WAKING_ALLOY_SMELTER;

        multiblock = new MultiblockInstance(this, Multiblock.multiblocks.get("wakingAlloySmelter"));
    }
}
