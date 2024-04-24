package sunsetsatellite.signalindustries.blocks;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidPipe;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.containers.ContainerCrusher;
import sunsetsatellite.signalindustries.containers.ContainerFilter;
import sunsetsatellite.signalindustries.gui.GuiCrusher;
import sunsetsatellite.signalindustries.gui.GuiFilter;
import sunsetsatellite.signalindustries.inventories.TileEntityFilter;
import sunsetsatellite.signalindustries.inventories.machines.TileEntityCrusher;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.ArrayList;
import java.util.Random;

public class BlockFilter extends BlockContainerTiered {
    public BlockFilter(String key, int i, Tier tier, Material material) {
        super(key, i, tier, material);
    }

    @Override
    public void onBlockRemoved(World world, int i, int j, int k, int data) {
        TileEntityFilter tile = (TileEntityFilter) world.getBlockTileEntity(i, j, k);
        if (tile != null) {
            Random random = new Random();
            for (int l = 0; l < tile.getSizeInventory(); ++l) {
                ItemStack itemstack = tile.getStackInSlot(l);
                if (itemstack != null) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    float f2 = random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int i1 = random.nextInt(21) + 10;
                        if (i1 > itemstack.stackSize) {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        EntityItem entityitem = new EntityItem(world, (float) i + f, (float) j + f1, (float) k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                        float f3 = 0.05F;
                        entityitem.xd = (float) random.nextGaussian() * f3;
                        entityitem.yd = (float) random.nextGaussian() * f3 + 0.2F;
                        entityitem.zd = (float) random.nextGaussian() * f3;
                        world.entityJoinedWorld(entityitem);
                    }
                }
            }
        }

        super.onBlockRemoved(world, i, j, k, data);
    }

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isClientSide)
        {
            return true;
        } else
        {
            TileEntityFilter tile = (TileEntityFilter) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                SignalIndustries.displayGui(entityplayer,() -> new GuiFilter(entityplayer.inventory, tile),new ContainerFilter(entityplayer.inventory,tile),tile,i,j,k);
            }
            return true;
        }
    }

    @Override
    public void setDefaultDirection(World world, int i, int j, int k) {
        if (!world.isClientSide) {
            world.setBlockMetadataWithNotify(i, j, k, 0);
        }
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        world.setBlockMetadataWithNotify(x, y, z, 0);
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityFilter();
    }
}
