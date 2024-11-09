package sunsetsatellite.signalindustries.mixin;


import com.mojang.nbt.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumSleepStatus;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.signalindustries.SIAchievements;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.SIWeather;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.interfaces.mixins.IPlayerPowerSuit;
import sunsetsatellite.signalindustries.interfaces.mixins.IWarpPlayer;
import sunsetsatellite.signalindustries.items.ItemSignalumPowerSuit;
import sunsetsatellite.signalindustries.items.attachments.ItemAttachment;
import sunsetsatellite.signalindustries.items.attachments.ItemNVGAttachment;
import sunsetsatellite.signalindustries.powersuit.SignalumPowerSuit;

@Mixin(
        value = EntityPlayer.class,
        remap = false
)
public abstract class EntityPlayerMixin extends EntityLiving implements IPlayerPowerSuit, IWarpPlayer {

    @Unique
    public SignalumPowerSuit powerSuit = null;

    @Shadow public abstract void sendTranslatedChatMessage(String message);

    @Shadow public InventoryPlayer inventory;

    @Shadow protected float baseSpeed;

    @Shadow public Container inventorySlots;

    @Unique
    public boolean warping = false;
    @Unique
    public int warpingToDim = 0;

    public EntityPlayerMixin(World world) {
        super(world);
    }


    @Inject(
            method = "sleepInBedAt",
            at = @At("HEAD"),
            cancellable = true
    )
    public void sleepInBedAt(int x, int y, int z, CallbackInfoReturnable<EnumSleepStatus> cir) {
        if (!world.isClientSide) {
            if(world.getCurrentWeather() == SIWeather.weatherBloodMoon){
                sendTranslatedChatMessage("bed.bloodMoon");
                cir.setReturnValue(EnumSleepStatus.NOT_POSSIBLE_NOW);
            }
        }
    }

    @Override
    public SignalumPowerSuit getPowerSuit() {
        return powerSuit;
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At("HEAD")
    )
    public void powerSuitUpdate(CallbackInfo ci) {
        ItemStack[] armorInventory = inventory.armorInventory;
        for (ItemStack itemStack : armorInventory) {
            if(itemStack == null){
                powerSuit = null;
                ItemNVGAttachment.disable();
                return;
            } else if(!(itemStack.getItem() instanceof ItemSignalumPowerSuit)){
                ItemNVGAttachment.disable();
                powerSuit = null;
                return;
            }
        }
        if(powerSuit == null){
            powerSuit = new SignalumPowerSuit(armorInventory,((EntityPlayer) (Object) this));
            ((EntityPlayer) (Object) this).triggerAchievement(SIAchievements.POWER_SUIT);
        } else {
            powerSuit.tick();
        }
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At("TAIL")
    )
    public void updateSpeed(CallbackInfo ci) {
        if(powerSuit != null && powerSuit.active){
            if(powerSuit.hasAttachment((ItemAttachment) SIItems.movementBoosters, Catalyst.listOf("bootBackL","bootBackR"))){
                if(powerSuit.getAttachment((ItemAttachment) SIItems.movementBoosters) != null && powerSuit.getAttachment((ItemAttachment) SIItems.movementBoosters).getData().getBoolean("active")){
                    speed += (float) (baseSpeed * 0.3);
                }
            }
        }
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At("HEAD")
    )
    public void warpPlayer(CallbackInfo ci) {
        if(warping){
            if(!world.isClientSide && vehicle != null)
            {
                startRiding(null);
            }
            Minecraft mc = Minecraft.getMinecraft(SignalIndustries.class);
            if(mc.currentScreen != null)
            {
                mc.displayGuiScreen(null);
            }
            SignalIndustries.usePortal(warpingToDim);
            finishWarping();
        }
    }

    @Inject(
            method = "damageEntity",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void damageEntity(int damage, DamageType damageType, CallbackInfo ci) {
        float protection = 1.0f - this.inventory.getTotalProtectionAmount(damageType);
        protection = Math.max(protection, 0.01f);
        double d = (float)damage * protection;
        int newDamage = (int)((double)this.random.nextFloat() > 0.5 ? Math.floor(d) : Math.ceil(d));
        int preventedDamage = damage - newDamage;
        if (powerSuit != null && powerSuit.active && powerSuit.status != SignalumPowerSuit.Status.OVERHEAT ) {
            if(powerSuit.getEnergy() >= newDamage){
                if (damageType != null && damageType.shouldDamageArmor()) {
                    int armorDamage = (int)Math.ceil((double)preventedDamage / 4.0);
                    this.inventory.damageArmor(armorDamage);
                }
                powerSuit.decrementEnergy(newDamage);
                ci.cancel();
            }
            if(damageType == DamageType.FIRE){
                powerSuit.temperature += 0.5f;
            }
        }
        if(inventory.armorItemInSlot(2) != null && inventory.armorItemInSlot(2).getData().getBoolean("active_shield") && damageType == DamageType.COMBAT){
            ci.cancel();
        }
    }

    @Inject(
            method = "addAdditionalSaveData",
            at = @At("HEAD")
    )
    public void saveSuitData(CompoundTag nbttagcompound, CallbackInfo ci) {
        if(powerSuit != null){
            powerSuit.saveToStacks();
        }
    }

    @Override
    public void warp(int dim) {
        warping = true;
        warpingToDim = dim;
    }

    @Override
    public void finishWarping() {
        warping = false;
        warpingToDim = 0;
    }

    @Override
    public boolean isWarping() {
        return warping;
    }
}
