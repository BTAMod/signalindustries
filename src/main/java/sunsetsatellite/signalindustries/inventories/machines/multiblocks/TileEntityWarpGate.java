package sunsetsatellite.signalindustries.inventories.machines.multiblocks;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Axis;
import sunsetsatellite.catalyst.core.util.*;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.multiblocks.IMultiblock;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.SIAchievements;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIDimensions;
import sunsetsatellite.signalindustries.api.impl.catalyst.fluid.SIFluids;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.entities.ExplosionEnergy;
import sunsetsatellite.signalindustries.interfaces.IMultiblockPart;
import sunsetsatellite.signalindustries.interfaces.IStabilizable;
import sunsetsatellite.signalindustries.interfaces.mixins.IWarpPlayer;
import sunsetsatellite.signalindustries.inventories.TileEntityEnergyConnector;
import sunsetsatellite.signalindustries.inventories.TileEntityFluidHatch;
import sunsetsatellite.signalindustries.inventories.TileEntityItemBus;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMachineBase;
import sunsetsatellite.signalindustries.inventories.machines.TileEntityStabilizer;
import sunsetsatellite.signalindustries.items.ItemWarpOrb;

import java.util.ArrayList;
import java.util.List;

public class TileEntityWarpGate extends TileEntityTieredMachineBase implements IMultiblock, IStabilizable {

    public MultiblockInstance multiblock;
    public TileEntityItemBus itemInput;
    public TileEntityItemBus itemOutput;
    public TileEntityFluidHatch fluidInput;
    public TileEntityFluidHatch fluidOutput;
    public TileEntityEnergyConnector energy;
    public List<TileEntityStabilizer> stabilizers = new ArrayList<>();
    private final TickTimer chargeTimer = new TickTimer(this,this::charge,10,true);
    private final TickTimer stabilityVerifyTimer = new TickTimer(this,this::verifyStability,20,true);
    public State state = State.IDLE;

    public TileEntityWarpGate(){
        itemContents = new ItemStack[1];
        fluidContents = new FluidStack[0];
        fluidCapacity = new int[0];
        multiblock = new MultiblockInstance(this,Multiblock.multiblocks.get("warpGate"));
    }

    public enum State {
        IDLE,
        CHARGING,
        CONNECTED_ONE_WAY,
        CONNECTED_TWO_WAY,
        STABILIZATION_FAILURE,
        POWER_FAILURE

    }

    @Override
    public void tick() {
        super.tick();
        BlockContainerTiered block = (BlockContainerTiered) getBlockType();
        itemInput = null;
        itemOutput = null;
        energy = null;
        chargeTimer.tick();
        stabilizers.clear();
        if(multiblock.isValid()) {
            Direction dir = Direction.getDirectionFromSide(getMovedData());
            ArrayList<BlockInstance> tileEntities = multiblock.data.getTileEntities(worldObj, new Vec3i(x, y, z), dir);
            for (BlockInstance tileEntity : tileEntities) {
                if (tileEntity.tile instanceof IMultiblockPart) {
                    if (tileEntity.tile instanceof TileEntityItemBus && tileEntity.block == SIBlocks.reinforcedItemInputBus) {
                        itemInput = (TileEntityItemBus) tileEntity.tile;
                    } else if (tileEntity.tile instanceof TileEntityItemBus && tileEntity.block == SIBlocks.reinforcedItemOutputBus) {
                        itemOutput = (TileEntityItemBus) tileEntity.tile;
                    } else if (tileEntity.tile instanceof TileEntityFluidHatch && tileEntity.block == SIBlocks.reinforcedFluidInputHatch) {
                        fluidInput = (TileEntityFluidHatch) tileEntity.tile;
                    } else if (tileEntity.tile instanceof TileEntityFluidHatch && tileEntity.block == SIBlocks.reinforcedFluidOutputHatch) {
                        fluidOutput = (TileEntityFluidHatch) tileEntity.tile;
                    } else if (tileEntity.tile instanceof TileEntityEnergyConnector && tileEntity.block == SIBlocks.awakenedEnergyConnector) {
                        energy = (TileEntityEnergyConnector) tileEntity.tile;
                    }
                    if(tileEntity.tile instanceof TileEntityStabilizer){
                        stabilizers.add((TileEntityStabilizer) tileEntity.tile);
                    }
                    ((IMultiblockPart) tileEntity.tile).connect(this);
                }
            }
            stabilityVerifyTimer.tick();
            if (block != null && itemInput != null && itemOutput != null && fluidInput != null && fluidOutput != null && energy != null) {
                if(state == State.IDLE && (worldObj.isBlockIndirectlyGettingPowered(x,y,z) || worldObj.isBlockGettingPowered(x,y,z)) && warpOrbInserted()){
                    state = State.CHARGING;
                } else if (state != State.IDLE && (!worldObj.isBlockIndirectlyGettingPowered(x,y,z) && !worldObj.isBlockGettingPowered(x,y,z))) {
                    state = State.IDLE;
                }
            }
            if(state == State.CONNECTED_ONE_WAY || state == State.CONNECTED_TWO_WAY){
                //aabbs just don't work how i want them to so we're gonna do it the old-fashioned way
                Axis axis = dir.shiftAxis().getAxis();
                Vec3f offset = dir.getVecF().multiply(4);
                Vec3f min = new Vec3f(x,y,z).subtract(offset);
                Vec3f max = new Vec3f(x,y,z).subtract(offset);
                min.y -= 3;
                max.y += 3;
                min.set(axis,min.get(axis)-3);
                max.set(axis,max.get(axis)+3);
                EntityPlayer closestPlayer = worldObj.getClosestPlayer(x, y, z, 8);
                if(closestPlayer != null && warpOrbInserted()){
                    if(closestPlayer.x >= min.x && closestPlayer.y >= min.y && closestPlayer.z >= min.z){
                        if(closestPlayer.x <= max.x+1 && closestPlayer.y <= max.y && closestPlayer.z <= max.z+1){
                            //FIXME: this crashes the goddamn game for some reason
                            ExplosionEnergy ex = new ExplosionEnergy(worldObj,closestPlayer,closestPlayer.x,closestPlayer.y,closestPlayer.z,3f);
                            ex.doExplosionA();
                            ex.doExplosionB(true,0.7f,0.0f,0.7f);
                            CompoundTag data = itemContents[0].getData();
                            CompoundTag warpPosition = data.getCompound("position");
                            if(warpPosition.containsKey("x") && warpPosition.containsKey("y") && warpPosition.containsKey("z")){
                                closestPlayer.triggerAchievement(SIAchievements.TELEPORT_SUCCESS);
                                if(closestPlayer.dimension == SIDimensions.dimEternity.id){
                                    closestPlayer.triggerAchievement(SIAchievements.FALSE_ETERNITY);
                                }
                                if(data.getInteger("dim") != closestPlayer.dimension){
                                    ((IWarpPlayer) closestPlayer).warp(data.getInteger("dim"));
                                }
                                closestPlayer.setPos(warpPosition.getInteger("x"),warpPosition.getInteger("y"),warpPosition.getInteger("z"));
                                ex = new ExplosionEnergy(worldObj,closestPlayer,closestPlayer.x,closestPlayer.y,closestPlayer.z,3f);
                                ex.doExplosionA();
                                ex.doExplosionB(true,0.7f,0.0f,0.7f);
                            } else {
                                closestPlayer.triggerAchievement(SIAchievements.TELEPORT_FAIL);
                                ((IWarpPlayer) closestPlayer).warp(SIDimensions.dimEternity.id);
                            }
                        }
                    }
                }
            }
        } else {
            state = State.IDLE;
        }
    }

    public void verifyStability(){
        if(state == State.CHARGING && checkIfStabilizersReady() && warpOrbInserted() && getEnergyAmount() > 0 && areCasingsCharged()){
            state = State.CONNECTED_ONE_WAY;
        }
        if(state == State.CONNECTED_ONE_WAY || state == State.CONNECTED_TWO_WAY){
            if(!warpOrbInserted()){
                state = State.IDLE;
            }
            if(!checkIfStabilizersReady()){
                state = State.STABILIZATION_FAILURE;
            } else if (getEnergyAmount() <= 0) {
                state = State.POWER_FAILURE;
            }
        }
    }

    public void charge(){
        if(state == State.CHARGING){
            Direction dir = Direction.getDirectionFromSide(getMovedData());
            ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
            for (BlockInstance block : blocks) {
                if(block.block == SIBlocks.reinforcedCasing2 || block.block == SIBlocks.awakenedSocketCasing || block.block == SIBlocks.awakenedCasing2) {
                    if(block.pos.getBlockMetadata(worldObj) != 1){
                        worldObj.setBlockMetadata(block.pos.x, block.pos.y, block.pos.z, 1);
                        break;
                    }
                }
            }

        } else if (state == State.IDLE || state == State.POWER_FAILURE || state == State.STABILIZATION_FAILURE) {
            Direction dir = Direction.getDirectionFromSide(getMovedData());
            ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
            for (BlockInstance block : blocks) {
                if(block.block == SIBlocks.reinforcedCasing2 || block.block == SIBlocks.awakenedSocketCasing || block.block == SIBlocks.awakenedCasing2) {
                    if(block.pos.getBlockMetadata(worldObj) != 0){
                        worldObj.setBlockMetadata(block.pos.x, block.pos.y, block.pos.z, 0);
                        break;
                    }
                }
            }
        }
    }

    public boolean warpOrbInserted(){
        return itemContents[0] != null && itemContents[0].getItem() instanceof ItemWarpOrb;
    }

    public boolean checkIfStabilizersReady(){
        boolean ready = true;
        for (TileEntityStabilizer stabilizer : stabilizers) {
            if (!stabilizer.canProcess()) {
                ready = false;
            }
        }
        return ready;
    }

    public boolean areCasingsCharged(){
        Direction dir = Direction.getDirectionFromSide(getMovedData());
        ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
        for (BlockInstance block : blocks) {
            if (block.block == SIBlocks.reinforcedCasing2 || block.block == SIBlocks.awakenedSocketCasing || block.block == SIBlocks.awakenedCasing2) {
                if (block.pos.getBlockMetadata(worldObj) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getEnergyAmount(){
        if(energy == null){
            return 0;
        } else {
            if (energy.fluidContents[0] != null && energy.fluidContents[0].getType() == SIFluids.SIGNALUM_ENERGY) {
                return energy.fluidContents[0].getAmount();
            } else {
                return 0;
            }
        }
    }

    @Override
    public MultiblockInstance getMultiblock() {
        return multiblock;
    }

    @Override
    public boolean isBurning() {
        return isActive();
    }

    @Override
    public boolean isActive() {
        return state == State.CONNECTED_ONE_WAY || state == State.CONNECTED_TWO_WAY;
    }

    @Override
    public boolean isReady() {
        return state == State.CHARGING;
    }
}
