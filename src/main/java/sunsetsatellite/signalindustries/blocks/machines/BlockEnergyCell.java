package sunsetsatellite.signalindustries.blocks.machines;


import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.fluids.impl.containers.ContainerFluidTank;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.blocks.base.BlockMachineBase;
import sunsetsatellite.signalindustries.gui.GuiEnergyCell;
import sunsetsatellite.signalindustries.inventories.TileEntityEnergyCell;
import sunsetsatellite.signalindustries.util.Tier;

public class BlockEnergyCell extends BlockMachineBase {

    public BlockEnergyCell(String key, int i, Tier tier, Material material) {
        super(key, i, tier, material);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityEnergyCell();
    }

    @Override
    public boolean onBlockRightClicked(World world, int i, int j, int k, EntityPlayer entityplayer, Side side, double xHit, double yHit) {
        if (super.onBlockRightClicked(world, i, j, k, entityplayer, side, xHit, yHit)) {
            return true;
        }
        if (world.isClientSide) {
            return true;
        } else {
            TileEntityEnergyCell tile = (TileEntityEnergyCell) world.getBlockTileEntity(i, j, k);
            if (tile != null) {
                SignalIndustries.displayGui(entityplayer, () -> new GuiEnergyCell(entityplayer.inventory, tile), new ContainerFluidTank(entityplayer.inventory, tile), tile, i, j, k);
            }
            return true;
        }
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }
}
