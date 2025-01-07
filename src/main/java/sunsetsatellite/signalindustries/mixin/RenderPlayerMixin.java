package sunsetsatellite.signalindustries.mixin;


import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.signalindustries.interfaces.mixins.IPlayerPowerSuit;
import sunsetsatellite.signalindustries.items.ItemArmorTiered;
import sunsetsatellite.signalindustries.items.ItemSignalumPrototypeHarness;
import sunsetsatellite.signalindustries.items.attachments.ItemAttachment;
import sunsetsatellite.signalindustries.items.attachments.ItemSuitColorizer;
import sunsetsatellite.signalindustries.powersuit.SignalumPowerSuit;


@Mixin(
        value = PlayerRenderer.class,
        remap = false
)
public class RenderPlayerMixin extends LivingRenderer<EntityPlayer> {


    @Final
    @Shadow private ModelBiped modelArmorChestplate;

    @Final
    @Shadow private ModelBiped modelArmor;

    @Shadow private ModelBiped modelBipedMain;

    public RenderPlayerMixin(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Inject(
            method = "setArmorModel",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/core/item/ItemStack;getItem()Lnet/minecraft/core/item/Item;", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    protected void setArmorModel(EntityPlayer entityplayer, int i, float f, CallbackInfoReturnable<Boolean> cir, ItemStack itemstack) {
        Item item = itemstack.getItem();
        if(item instanceof ItemArmorTiered){
            ItemArmor itemarmor = (ItemArmor)item;
            if(itemarmor instanceof ItemSignalumPrototypeHarness && (i == 0 || i == 1) && entityplayer.inventory.armorItemInSlot(2) .getData().getBoolean("active_shield")){
                if(entityplayer.inventory.armorItemInSlot(3) != null && entityplayer.inventory.armorItemInSlot(3).getItem() instanceof ItemSignalumPrototypeHarness){
                    this.loadTexture("/assets/signalindustries/armor/signalumprototypeharness_shield_1.png");
                } else {
                    this.loadTexture("/assets/signalindustries/armor/signalumprototypeharness_shield_no_goggles_1.png");
                }
                ModelBiped modelbiped = this.modelArmor;
                modelbiped.bipedHead.showModel = true;
                modelbiped.bipedHeadOverlay.showModel = true;
                modelbiped.bipedBody.showModel = true;
                modelbiped.bipedRightArm.showModel = true;
                modelbiped.bipedLeftArm.showModel = true;
                modelbiped.bipedRightLeg.showModel = true;
                modelbiped.bipedLeftLeg.showModel = true;
                this.setRenderPassModel(modelbiped);
                cir.setReturnValue(true);
            } else {
                String path = "/assets/signalindustries/armor/" + itemarmor.material.identifier.value + "_" + (i != 2 ? 1 : 2) + ".png";
                SignalumPowerSuit powerSuit = ((IPlayerPowerSuit)entityplayer).getPowerSuit();
                if(powerSuit != null && powerSuit.hasAttachment((ItemAttachment) SIItems.awakenedAbilityModule)){
                    ItemStack stack = powerSuit.getAttachment((ItemAttachment) SIItems.awakenedAbilityModule);
                    if(stack != null){
                        path = "assets/signalindustries/armor/signalumpowersuit_awakened" + "_" + (i != 2 ? 1 : 2) + ".png";
                    }
                }
                if(powerSuit != null && powerSuit.hasAttachmentClass(ItemSuitColorizer.class)){
                    ItemStack stack = powerSuit.getAttachmentClass(ItemSuitColorizer.class);
                    if(stack != null){
                        ItemSuitColorizer suitColorizer = (ItemSuitColorizer) stack.getItem();
                        path = suitColorizer.path + "_" + (i != 2 ? 1 : 2) + ".png";
                    }
                }
                this.loadTexture(path);
                ModelBiped modelbiped = i != 2 ? this.modelArmorChestplate : this.modelArmor;
                modelbiped.bipedHead.showModel = i == 0;
                modelbiped.bipedHeadOverlay.showModel = i == 0;
                modelbiped.bipedBody.showModel = i == 1 || i == 2;
                modelbiped.bipedRightArm.showModel = i == 1;
                modelbiped.bipedLeftArm.showModel = i == 1;
                modelbiped.bipedRightLeg.showModel = i == 2 || i == 3;
                modelbiped.bipedLeftLeg.showModel = i == 2 || i == 3;
                this.setRenderPassModel(modelbiped);
                cir.setReturnValue(true);
            }
        }
    }

    /*@Inject(
            method = "renderSpecials",
            at = @At(value = "INVOKE",target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V",ordinal = 7,shift = At.Shift.BEFORE)
    )
    protected void renderPulsar(EntityPlayer entityplayer, float f, CallbackInfo ci) {
        ItemStack stack = entityplayer.inventory.getCurrentItem();
        if(stack.getItem() instanceof ItemPulsar){
            GL11.glRotatef(160f, 0.0F, -1.2F, 1.55F);
            GL11.glTranslatef(-0.15F, 0.15f, 0.25F);
        }
    }*/

    @Inject(
            method = "renderSpecials",
            at = @At("HEAD")
    )
    protected void renderSpecials(EntityPlayer entityplayer, float f, CallbackInfo ci) {
        SignalumPowerSuit powerSuit = ((IPlayerPowerSuit)entityplayer).getPowerSuit();
        if(powerSuit != null){
            for (ItemStack content : powerSuit.helmet.contents) {
                if(content != null){
                    GL11.glPushMatrix();
                    ((ItemAttachment)content.getItem()).renderWhenAttached(entityplayer, powerSuit, modelBipedMain, content);
                    GL11.glPopMatrix();
                }
            }
            for (ItemStack content : powerSuit.chestplate.contents) {
                if(content != null){
                    GL11.glPushMatrix();
                    ((ItemAttachment)content.getItem()).renderWhenAttached(entityplayer, powerSuit, modelBipedMain, content);
                    GL11.glPopMatrix();
                }
            }
            for (ItemStack content : powerSuit.leggings.contents) {
                if(content != null){
                    GL11.glPushMatrix();
                    ((ItemAttachment)content.getItem()).renderWhenAttached(entityplayer, powerSuit, modelBipedMain, content);
                    GL11.glPopMatrix();
                }
            }
            for (ItemStack content : powerSuit.boots.contents) {
                if(content != null){
                    GL11.glPushMatrix();
                    ((ItemAttachment)content.getItem()).renderWhenAttached(entityplayer, powerSuit, modelBipedMain, content);
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
