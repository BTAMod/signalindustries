package sunsetsatellite.signalindustries.items.attachments;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.util.AttachmentPoint;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.ArrayList;
import java.util.List;

public class ItemSuitColorizer extends ItemTieredAttachment {

    public final String path;

    public ItemSuitColorizer(String name, int id, List<AttachmentPoint> attachmentPoints, Tier tier, String path) {
        super(name, id, attachmentPoints, tier);
        this.path = path
        ;
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        List<ItemSuitColorizer> list = new ArrayList<>();
        list.add(SIItems.suitColorizerWhite);
        list.add(SIItems.suitColorizerBlue);
        list.add(SIItems.suitColorizerPurple);
        list.add(SIItems.suitColorizerInverted);
        list.add(SIItems.suitColorizerTransparent);
        int i = list.indexOf(itemstack.getItem());
        if(i != -1){
            ItemSuitColorizer colorizer = list.get((i + 1) % list.size());
            itemstack.itemID = colorizer.id;
        }
        return super.onUseItem(itemstack, world, entityplayer);
    }
}
