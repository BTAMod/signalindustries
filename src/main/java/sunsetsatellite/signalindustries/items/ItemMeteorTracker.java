package sunsetsatellite.signalindustries.items;

import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import sunsetsatellite.catalyst.core.util.ICustomDescription;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.util.MeteorLocation;

public class ItemMeteorTracker extends Item implements ICustomDescription {


    public ItemMeteorTracker(String name, int id) {
        super(name, id);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(itemstack.getMetadata() == 0){
            itemstack.setMetadata(1);
        } else {

            ChunkCoordinates chunk = null;
            double distance = Double.MAX_VALUE;
            MeteorLocation.Type type = null;
            for (MeteorLocation meteorLocation : SignalIndustries.meteorLocations) {
                ChunkCoordinates location = meteorLocation.location;
                if(location.getSqDistanceTo((int) entityplayer.x, (int) entityplayer.y, (int) entityplayer.z) < distance){
                    distance = location.getSqDistanceTo((int) entityplayer.x, (int) entityplayer.y, (int) entityplayer.z);
                    chunk = location;
                    type = meteorLocation.type;
                }
            }
            if(chunk != null){
                if(entityplayer.isSneaking() && distance < 5){
                    entityplayer.sendStatusMessage("This meteor will no longer be tracked.");
                    final ChunkCoordinates finalChunk = chunk;
                    SignalIndustries.meteorLocations.removeIf((L)->L.location == finalChunk);
                } else {
                    entityplayer.sendStatusMessage(String.format("Distance: %.0f blocks | Type: %s", distance,type));
                }
            } else {
                entityplayer.sendStatusMessage("No meteors detected nearby.");
            }
        }
        return super.onUseItem(itemstack, world, entityplayer);
    }

    @Override
    public String getDescription(ItemStack stack) {
        if(stack.getMetadata() != 1){
            return "Uncalibrated!\n"+ TextFormatting.GRAY +"Right-click while holding in your hand to calibrate.";
        }
        return "Calibrated.";
    }
}
