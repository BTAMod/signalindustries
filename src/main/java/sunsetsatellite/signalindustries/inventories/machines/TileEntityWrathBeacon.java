package sunsetsatellite.signalindustries.inventories.machines;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.enums.Difficulty;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkPosition;
import sunsetsatellite.catalyst.core.util.TickTimer;
import sunsetsatellite.signalindustries.SIAchievements;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.entities.ExplosionEnergy;
import sunsetsatellite.signalindustries.inventories.base.TileEntityWrathBeaconBase;
import sunsetsatellite.signalindustries.util.Wave;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class TileEntityWrathBeacon extends TileEntityWrathBeaconBase {
    public Random random = new Random();
    public boolean intermission = false;
    public int wave = 0;
    public int currentMaxAmount = 0;
    public boolean started = false;
    public ArrayList<EntityLiving> enemiesLeft = new ArrayList<>();
    public static ArrayList<Wave> waves = new ArrayList<>();
    public TickTimer spawnTimer = new TickTimer(this,this::spawn,20,true);
    public TickTimer intermissionTimer = new TickTimer(this,this::startWave,300,false);
    {
            spawnTimer.pause();
            intermissionTimer.pause();
    }

    public TileEntityWrathBeacon(){
        ArrayList<Class<? extends EntityMonster>> mobList = new ArrayList<>();
        mobList.add(EntityZombie.class);
        mobList.add(EntitySkeleton.class);
        waves.add(new Wave(mobList,3,6,20));
        waves.add(new Wave(mobList,5,10,20));
        waves.add(new Wave(mobList,6,12,20));
        mobList = new ArrayList<>();
        mobList.add(EntityCreeper.class);
        waves.add(new Wave(mobList,2,4,20));
        mobList = new ArrayList<>();
        mobList.add(EntityZombie.class);
        mobList.add(EntitySkeleton.class);
        mobList.add(EntitySpider.class);
        waves.add(new Wave(mobList,8,10,20));
        mobList = new ArrayList<>();
        mobList.add(EntityZombie.class);
        mobList.add(EntitySkeleton.class);
        mobList.add(EntitySpider.class);
        mobList.add(EntityCreeper.class);
        waves.add(new Wave(mobList,10,16,20));
        //final wave, boss not included
        waves.add(new Wave(mobList,10,16,20));

    }

    @Override
    public void tick() {
        worldObj.markBlocksDirty(x,y,z,x,y,z);
        if(active){
            spawnTimer.tick();
            intermissionTimer.tick();
        }

        enemiesLeft.removeIf((E)-> !E.isAlive());
        if(active && worldObj.difficultySetting == Difficulty.PEACEFUL.id()){
            Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("The wrath beacon loses all its strength suddenly..");
            worldObj.setBlockWithNotify(x,y,z,0);
        }
        if(active && started && enemiesLeft.isEmpty() && wave < 5){
            for (EntityPlayer player : worldObj.players) {
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("Wave "+wave+" complete! Next wave in: "+(intermissionTimer.max/20)+"s.");
            }
            started = false;
            intermissionTimer.unpause();
            intermission = true;
            wave++;
        } else if (active && started && enemiesLeft.isEmpty() && wave == 5) {
            for (EntityPlayer player : worldObj.players) {
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("Challenge complete!!");
                player.triggerAchievement(SIAchievements.VICTORY);
            }
            active = false;
            started = false;
            intermission = false;
            spawnTimer.pause();
            intermissionTimer.pause();
            wave = 0;
            currentMaxAmount = 0;
            worldObj.setBlockWithNotify(x,y,z,0);
            ExplosionEnergy explosion = new ExplosionEnergy(worldObj, null, x, y, z, 3);
            explosion.doExplosionA();
            explosion.doExplosionB(true);
            EntityItem entityitem = new EntityItem(worldObj, (float) x, (float) y, (float) z, new ItemStack(SIItems.energyCatalyst, 1));
            worldObj.entityJoinedWorld(entityitem);
        }
        if(active){
            for (float y1 = y; y1 < 256; y1+=0.1f) {
                worldObj.spawnParticle("reddust",x+0.5,y1,z+0.5,0,0,0,0);
            }
        }
        if(worldObj != null && getBlockType() != null){
            tier = ((BlockContainerTiered)getBlockType()).tier;
        }
        //SignalIndustries.LOGGER.info(String.valueOf(enemiesLeft.size()));
        //SignalIndustries.LOGGER.info(String.valueOf(intermissionTimer.value));

    }

    public void activate(EntityPlayer activator){
        if(!active){
            if(worldObj.difficultySetting == Difficulty.PEACEFUL.id()){
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("This world is far too peaceful..");
                return;
            }
            if(worldObj.isDaytime()){
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("Now is not the time..");
                return;
            }
            /*for (int x1 = x-7; x < x+7; x++) {
                for (int y1 = y; y1 < y+8; y1++) {
                    for (int z1 = z-7; z < z+7; z++) {
                        int id = worldObj.getBlockId(x1,y1,z1);
                        int idUnder = worldObj.getBlockId(x1,y1-1,z1);
                        if (id != 0 && (x1 != x || y1 != y || z1 != z)) {
                            Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("The wrath beacon desires more space..");
                            return;
                        }
                    }
                }
            }*/
            if(Minecraft.getMinecraft(Minecraft.class).thePlayer.inventory.getCurrentItem() != null && Minecraft.getMinecraft(Minecraft.class).thePlayer.inventory.getCurrentItem().getItem().id == SIItems.evilEye.id){
                Minecraft.getMinecraft(Minecraft.class).thePlayer.inventory.getCurrentItem().consumeItem(Minecraft.getMinecraft(Minecraft.class).thePlayer);
                for (EntityPlayer player : worldObj.players) {
                    player.sendTranslatedChatMessage("event.signalindustries.wrathBeaconActivated");
                    player.triggerAchievement(SIAchievements.CHALLENGE);
                }
                active = true;
                startWave();
            } else {
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("The wrath beacon needs a catalyst..");
            }
        }
    }

    public void startWave(){
        if(active){
            for (EntityPlayer player : worldObj.players) {
                Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("WAVE "+wave);
                if(wave == 5){
                    Minecraft.getMinecraft(Minecraft.class).ingameGUI.addChatMessage("FINAL WAVE!");
                }
            }
            intermission = false;
            intermissionTimer.pause();
            spawnTimer.unpause();
            spawnTimer.max = waves.get(wave).spawnFrequency;
            currentMaxAmount = waves.get(wave).lowerBound + random.nextInt(waves.get(wave).upperBound-waves.get(wave).lowerBound);
        }
    }

    public void spawn(){
        if(enemiesLeft.size() < currentMaxAmount){
            started = true;
            ChunkPosition randomPos = getRandomSpawningPointInChunk(worldObj, this.x, this.z);
            EntityMonster mob;
            try {
                mob = waves.get(wave).chooseRandomMob().getConstructor(World.class).newInstance(worldObj);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            mob.setPos(randomPos.x, randomPos.y, randomPos.z);
            mob.setRot(worldObj.rand.nextFloat() * 360.0F, 0.0F);
            mob.spawnInit();
            worldObj.entityJoinedWorld(mob);
            enemiesLeft.add(mob);
        } else {
            spawnTimer.pause();
        }

    }

    public ChunkPosition getRandomSpawningPointInChunk(World worldObj, int i, int j) {
        int k = i + worldObj.rand.nextInt(8);
        int l = this.y;
        int i1 = j + worldObj.rand.nextInt(8);
        return new ChunkPosition(k, l, i1);
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
