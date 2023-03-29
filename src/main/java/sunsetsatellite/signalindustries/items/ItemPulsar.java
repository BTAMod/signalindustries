package sunsetsatellite.signalindustries.items;

import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.Tiers;
import sunsetsatellite.signalindustries.containers.ContainerPulsar;
import sunsetsatellite.signalindustries.gui.GuiPulsar;
import sunsetsatellite.signalindustries.tiles.InventoryPulsar;

public class ItemPulsar extends ItemTiered {
    public ItemPulsar(int i, Tiers tier) {
        super(i, tier);
    }

    @Override
    public String getDescription(ItemStack stack) {
        String text = super.getDescription(stack);
        String ability = getAbility(stack);
        text += "\nCharge: "+ (stack.tag.getByte("charge") >= 100 ? ChatColor.red : ChatColor.lightGray) +stack.tag.getByte("charge")+"%"+ChatColor.white+" | Ability: "+ability;
        return text;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, double heightPlaced) {
        if(entityplayer.isSneaking() && !itemstack.tag.getBoolean("charging")){
            SignalIndustries.displayGui(entityplayer,new GuiPulsar(entityplayer.inventory,entityplayer.inventory.getCurrentItem()),new ContainerPulsar(entityplayer.inventory,entityplayer.inventory.getCurrentItem()),new InventoryPulsar(entityplayer.inventory.getCurrentItem()));
            return true;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!itemstack.tag.getBoolean("charging") && itemstack.tag.getByte("charge") < 100 && !entityplayer.isSneaking() && getFluidStack(0,itemstack).getInteger("amount") > 0) {
            itemstack.tag.setBoolean("charging", true);
            return itemstack;
        }
        if(itemstack.tag.getByte("charge") >= 100){
            itemstack.tag.setByte("charge", (byte) 0);
            return itemstack;
        }
        return itemstack;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        boolean charging = itemstack.tag.getBoolean("charging");
        byte charge = itemstack.tag.getByte("charge");
        int energy = getFluidStack(0,itemstack).getInteger("amount");
        if(itemstack.tag.getBoolean("charging")){
            if(charge < 100){
                if(energy <= 0){
                    getFluidStack(0,itemstack).setInteger("amount",0);
                    itemstack.tag.setBoolean("charging",false);
                    return;
                }
                if(getItemIdFromSlot(0,itemstack) == SignalIndustries.warpOrb.itemID){
                    getFluidStack(0,itemstack).setInteger("amount",energy-80); //charging takes 100 ticks
                } else {
                    getFluidStack(0,itemstack).setInteger("amount",energy-40); //charging takes 100 ticks
                }
                itemstack.tag.setByte("charge", (byte) (charge+1));
            } else {
                itemstack.tag.setBoolean("charging",false);
            }

        }
        super.onUpdate(itemstack, world, entity, i, flag);
    }

    @Override
    public int getIconIndex(ItemStack itemstack) {
        if(getFluidStack(0,itemstack).getInteger("amount") <= 0 && itemstack.tag.getByte("charge") <= 0){
            return Item.iconCoordToIndex(SignalIndustries.pulsarTex[0][0],SignalIndustries.pulsarTex[0][1]);
        }
        int tex = Item.iconCoordToIndex(SignalIndustries.pulsarTex[1][0],SignalIndustries.pulsarTex[1][1]);
        if(itemstack.tag.getByte("charge") >= 100){
            tex = Item.iconCoordToIndex(SignalIndustries.pulsarTex[2][0],SignalIndustries.pulsarTex[2][1]);
        }
        if(getItemIdFromSlot(0,itemstack) == SignalIndustries.warpOrb.itemID){
            tex = Item.iconCoordToIndex(SignalIndustries.pulsarTex[3][0],SignalIndustries.pulsarTex[3][1]);
            if(itemstack.tag.getByte("charge") >= 100){
                tex = Item.iconCoordToIndex(SignalIndustries.pulsarTex[4][0],SignalIndustries.pulsarTex[4][1]);
            }
        }
        return tex;
    }

    public int getItemIdFromSlot(int id, ItemStack stack){
        return stack.tag.getCompoundTag("inventory").getCompoundTag(String.valueOf(id)).getShort("id");
    }

    public NBTTagCompound getFluidStack(int id, ItemStack stack){
        return stack.tag.getCompoundTag("fluidInventory").getCompoundTag(String.valueOf(id));
    }

    public String getAbility(ItemStack stack){
        return getItemIdFromSlot(0,stack) == SignalIndustries.warpOrb.itemID ? ChatColor.purple+"Warp" : ChatColor.red+"Pulse";
    }

}