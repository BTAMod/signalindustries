package sunsetsatellite.signalindustries.inventories.machines.multiblocks.waking;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.SIRecipes;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMultiblock;
import sunsetsatellite.signalindustries.util.Tier;

public class TileEntityWakingInfuser extends TileEntityTieredMultiblock {
    @Override
    public void init(Block block) {
        usesEnergy = true;
        usesItemInput = true;
        usesItemOutput = true;
        usesFluidInput = true;
        minimumFluidInputTier = Tier.REINFORCED;
        minimumEnergyTier = Tier.AWAKENED;
        minimumItemInputTier = Tier.REINFORCED;
        minimumItemOutputTier = Tier.REINFORCED;

        recipeGroup = SIRecipes.WAKING_INFUSER;

        multiblock = new MultiblockInstance(this, Multiblock.multiblocks.get("wakingInfuser"));
    }
}
