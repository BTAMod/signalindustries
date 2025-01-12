package sunsetsatellite.signalindustries.items.attachments;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.shader.Shaders;
import net.minecraft.client.render.shader.ShadersRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.powersuit.SignalumPowerSuit;
import sunsetsatellite.signalindustries.render.ShadersRendererSI;
import sunsetsatellite.signalindustries.util.AttachmentPoint;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.List;

public class ItemNVGAttachment extends ItemTieredAttachment {
    public ItemNVGAttachment(String name, int id, List<AttachmentPoint> attachmentPoints, Tier tier) {
        super(name, id, attachmentPoints, tier);
    }

    @Override
    public void activate(ItemStack stack, SignalumPowerSuit signalumPowerSuit, EntityPlayer player, World world) {
        super.activate(stack, signalumPowerSuit, player, world);
        if (Global.isServer) return;
        Minecraft mc = Minecraft.getMinecraft(this);
        if(signalumPowerSuit.getEnergy() >= 1 ) {
            if (Shaders.enableShaders) {
                if (mc.renderer instanceof ShadersRendererSI) {
                    mc.setRenderer(new ShadersRenderer(mc));
                    mc.renderer.reload();
                    mc.fullbright = false;
                    mc.renderGlobal.loadRenderers();
                } else {
                    mc.setRenderer(new ShadersRendererSI(mc, "nightvision/", signalumPowerSuit));
                    mc.renderer.reload();
                    mc.fullbright = true;
                    mc.renderGlobal.loadRenderers();
                }
            } else {
                mc.ingameGUI.addChatMessage("Can't activate: Shaders are disabled.");
            }
        }
    }

    @Override
    public void tick(ItemStack stack, SignalumPowerSuit signalumPowerSuit, EntityPlayer player, World world, int slot) {
        super.tick(stack, signalumPowerSuit, player, world, slot);
        if (Global.isServer) return;
        Minecraft mc = Minecraft.getMinecraft(this);
        if(mc.renderer instanceof ShadersRendererSI){
            if(signalumPowerSuit.getEnergy() < 1 ) {
                mc.setRenderer(new ShadersRenderer(mc));
                mc.renderer.reload();
                mc.fullbright = false;
                mc.renderGlobal.loadRenderers();
            }
//            } else {
//                signalumPowerSuit.decrementEnergy(1);
//            }
        }
    }

    public static void disable(){
        if (Global.isServer) return;
        Minecraft mc = Minecraft.getMinecraft(SignalIndustries.class);
        if (mc.renderer instanceof ShadersRendererSI) {
            mc.setRenderer(new ShadersRenderer(mc));
            mc.renderer.reload();
            mc.fullbright = false;
            mc.renderGlobal.loadRenderers();
        }
    }
}
