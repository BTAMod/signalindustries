package sunsetsatellite.signalindustries.blocks.machines;


import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.helper.Sides;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidPipe;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.containers.ContainerAlloySmelter;
import sunsetsatellite.signalindustries.containers.ContainerDimAnchor;
import sunsetsatellite.signalindustries.gui.GuiAlloySmelter;
import sunsetsatellite.signalindustries.gui.GuiDimAnchor;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMachineBase;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMachineSimple;
import sunsetsatellite.signalindustries.inventories.machines.TileEntityAlloySmelter;
import sunsetsatellite.signalindustries.inventories.machines.TileEntityInductionSmelter;
import sunsetsatellite.signalindustries.misc.SignalIndustriesAchievementPage;
import sunsetsatellite.signalindustries.util.IOPreview;
import sunsetsatellite.signalindustries.util.Tier;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.ArrayList;
import java.util.Random;

public class BlockInductionSmelter extends BlockContainerTiered {
    public BlockInductionSmelter(String key, int i, Tier tier, Material material) {
        super(key, i, tier, material);
        hasOverbright = true;
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityInductionSmelter();
    }

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isClientSide)
        {
            return true;
        } else
        {
            TileEntityInductionSmelter tile = (TileEntityInductionSmelter) world.getBlockTileEntity(i, j, k);
            if(tile.getMultiblock() != null && tile.getMultiblock().isValidAt(world,new BlockInstance(this,new Vec3i(i,j,k),tile),Direction.getDirectionFromSide(world.getBlockMetadata(i,j,k)))){
                //SignalIndustries.displayGui(entityplayer,() -> new GuiDimAnchor(entityplayer.inventory, tile),new ContainerDimAnchor(entityplayer.inventory,tile),tile,i,j,k);
                entityplayer.triggerAchievement(SignalIndustriesAchievementPage.HORIZONS);
            } else {
                entityplayer.addChatMessage("event.signalindustries.invalidMultiblock");
            }
            return true;
        }

    }

    @Override
    public int getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        TileEntityTieredMachineBase tile = (TileEntityTieredMachineBase) blockAccess.getBlockTileEntity(x,y,z);
        int meta = blockAccess.getBlockMetadata(x,y,z);
        int index = Sides.orientationLookUpHorizontal[6 * meta + side.getId()];
        if(tile.isBurning() && tile.tier == tier){
            return SignalIndustries.textures.get(tile.tier.name()+".inductionSmelter.active").getTexture(Side.getSideById(index));
        }
        return this.atlasIndices[index];
    }

    @Override
    public String getDescription(ItemStack stack) {
        String s = super.getDescription(stack);
        return s+"\n"+ TextFormatting.YELLOW+"Multiblock"+ TextFormatting.WHITE;
    }

    @Override
    public int getBlockOverbrightTexture(WorldSource blockAccess, int x, int y, int z, int side) {
        TileEntityTieredMachineBase tile = (TileEntityTieredMachineBase) blockAccess.getBlockTileEntity(x,y,z);
        if(tile.preview != IOPreview.NONE){
            Direction dir = Direction.getDirectionFromSide(side);
            Connection con = Connection.NONE;
            switch (tile.preview){
                case ITEM: {
                    con = tile.itemConnections.get(dir);
                    break;
                }
                case FLUID: {
                    con = tile.fluidConnections.get(dir);
                    break;
                }
            }
            switch (con){
                case INPUT:
                    return TextureHelper.getOrCreateBlockTextureIndex(SignalIndustries.MOD_ID,"input_overlay.png");
                case OUTPUT:
                    return TextureHelper.getOrCreateBlockTextureIndex(SignalIndustries.MOD_ID,"output_overlay.png");
                case BOTH:
                    return TextureHelper.getOrCreateBlockTextureIndex(SignalIndustries.MOD_ID,"both_io_overlay.png");
                case NONE:
                    return -1;
            }
        } else {
            int meta = blockAccess.getBlockMetadata(x, y, z);
            int index = Sides.orientationLookUpHorizontal[6 * meta + side];
            if (tile.isBurning() && tile.tier == tier) {
                return SignalIndustries.textures.get(tile.tier.name() + ".inductionSmelter.active.overlay").getTexture(Side.getSideById(index));
            }
            return -1;
        }
        return -1;
    }
}
