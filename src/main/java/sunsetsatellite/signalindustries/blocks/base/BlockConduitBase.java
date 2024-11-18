package sunsetsatellite.signalindustries.blocks.base;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.*;
import sunsetsatellite.catalyst.fluids.impl.tiles.TileEntityFluidPipe;
import sunsetsatellite.signalindustries.inventories.TileEntityItemConduit;
import sunsetsatellite.signalindustries.items.ItemConfigurationTablet;
import sunsetsatellite.signalindustries.util.ConfigurationTabletMode;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.Objects;

public abstract class BlockConduitBase extends BlockContainerTiered implements IConduitBlock, ISideInteractable {
    public BlockConduitBase(String key, int i, Tier tier, Material material) {
        super(key, i, tier, material);
        setBlockBounds(0.3f, 0.3f, 0.3f, 0.7f, 0.7f, 0.7f);
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    @Override
    public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
        if (!isPlayerHoldingWrench(player)) {
            return false;
        }

        Pair<Direction, BlockSection> pair = Catalyst.getBlockSurfaceClickPosition(world, player, Minecraft.getMinecraft(this).objectMouseOver);
        Side playerFacing = Catalyst.calculatePlayerFacing(player.yRot);
        if (pairIsInvalid(pair)) {
            return false;
        }

        if (isPlayerHoldingConfigTablet(player)) {
            handleConfigTabletAction(player, pair, world, x, y, z, playerFacing);
        }
        return true;
    }

    private boolean isPlayerHoldingWrench(EntityPlayer player) {
        return player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof IWrench;
    }

    private boolean pairIsInvalid(Pair<Direction, BlockSection> pair) {
        return pair == null || pair.getLeft() == null || pair.getRight() == null;
    }

    private boolean isPlayerHoldingConfigTablet(EntityPlayer player) {
        return player.getCurrentEquippedItem().getItem() instanceof ItemConfigurationTablet;
    }

    private void handleConfigTabletAction(EntityPlayer player, Pair<Direction, BlockSection> pair,
                                          World world, int x, int y, int z, Side playerFacing) {

        ConfigurationTabletMode mode = ConfigurationTabletMode.values()[player.getCurrentEquippedItem().getData().getInteger("mode")];
        if (Objects.requireNonNull(mode) == ConfigurationTabletMode.DISCONNECTOR) {
            handlePipeDisconnect(pair, world, x, y, z, playerFacing, player);
        }
    }

    private void handlePipeDisconnect(Pair<Direction, BlockSection> pair, World world, int x, int y, int z, Side playerFacing, EntityPlayer player) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile instanceof TileEntityItemConduit) {
            Direction dir = pair.getRight().toDirection(pair.getLeft(), playerFacing);
            ((TileEntityItemConduit) tile).noConnectDirections.put(dir, !((TileEntityItemConduit) tile).noConnectDirections.get(dir));
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(WorldSource world, int x, int y, int z) {
        float bx = 0.3f, by = 0.3f, bz = 0.3f;
        float tx = 0.7f, ty = 0.7f, tz = 0.7f;
        // Loop de-loop
        for (Direction dir : Direction.values()) {
            Vec3i v = dir.getVec();
            TileEntity te = world.getBlockTileEntity(x + v.x, y + v.y, z + v.z);
            Block b = world.getBlock(x + v.x, y + v.y, z + v.z);
            if (
                te == null || !(te instanceof TileEntityFluidPipe) ||
                getConduitCapability() != ((IConduitBlock) b).getConduitCapability()
            ) continue;
            if (v.x > 0) tx = 1.0f;
            else if (v.x < 0) bx = 0.0f;
            if (v.z > 0) tz = 1.0f;
            else if (v.z < 0) bz = 0.0f;
            if (v.y > 0) ty = 1.0f;
            else if (v.y < 0) by = 0.0f;
        }
        setBlockBounds(bx, by, bz, tx, ty, tz);
    }

    @Override
    public AABB getCollisionBoundingBoxFromPool(WorldSource world, int x, int y, int z) {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
}
