package sunsetsatellite.signalindustries.items;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.signalindustries.items.applications.ItemWithUtility;
import sunsetsatellite.signalindustries.powersuit.SignalumPowerSuit;
import sunsetsatellite.signalindustries.util.Tier;

public class ItemSmartWatch extends ItemWithUtility {
    public ItemSmartWatch(String name, int id, Tier tier) {
        super(name, id, tier);
    }

    @Override
    public void activate(ItemStack stack, SignalumPowerSuit signalumPowerSuit, EntityPlayer player, World world) {

    }
}