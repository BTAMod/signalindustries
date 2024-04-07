package sunsetsatellite.signalindustries.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryTrommel;
import net.minecraft.core.item.ItemStack;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.client.render.TextureFX;
import net.minecraft.core.player.inventory.slot.SlotGuidebook;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiRenderFakeItem extends Gui
{
    static ItemEntityRenderer itemRenderer = new ItemEntityRenderer();

    Minecraft mc;

    public GuiRenderFakeItem(Minecraft mc)
    {
        this.mc = mc;
    }

    public void render(ItemStack itemStack, int x, int y, boolean isSelected, Slot slot)
    {
        boolean hasDrawnSlotBackground = false;
        float alpha = 0.5f;

        // Do setup
        Lighting.enableInventoryLight();
        GL11.glColor4f(1, 1, 1, alpha);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        // Draw slot background
        if (slot != null)
        {
            final int iconIndex = slot.getBackgroundIconIndex();
            if (iconIndex >= 0 && itemStack == null)
            {
                GL11.glDisable(GL11.GL_LIGHTING);
                mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
                drawTexturedModalRect(x, y,
                        (iconIndex % Global.TEXTURE_ATLAS_WIDTH_TILES) * TextureFX.tileWidthItems,
                        (iconIndex / Global.TEXTURE_ATLAS_WIDTH_TILES) * TextureFX.tileWidthItems,
                        16, 16,
                        TextureFX.tileWidthItems,
                        1 / (float) (Global.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthItems));
                GL11.glEnable(GL11.GL_LIGHTING);
                hasDrawnSlotBackground = true;
            }
        }

        // Draw item
        if (!hasDrawnSlotBackground)
        {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x, y, 1.0f, alpha);
            if(slot instanceof SlotGuidebook){
                if(((SlotGuidebook) slot).recipe instanceof RecipeEntryTrommel && ((SlotGuidebook) slot).item != null && ((SlotGuidebook) slot).isOutput){
                    WeightedRandomBag<WeightedRandomLootObject> loot = ((RecipeEntryTrommel)((SlotGuidebook) slot).recipe).getOutput();
                    int index = slot.id;
                    if(((SlotGuidebook) slot).recipeAmount > 8){
                        index = slot.id + (9 * ((SlotGuidebook) slot).recipeIndex);
                    }
                    WeightedRandomLootObject lootObject = loot.getEntries().get(index);
                    String s;
                    if(lootObject.isRandom()){
                        s = String.format("%d-%d",lootObject.getMinYield(),lootObject.getMaxYield());
                    } else {
                        s = String.valueOf(lootObject.getFixedYield());
                    }
                    itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x, y, s, alpha);
                } else {
                    itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x, y, null, alpha);
                }
            } else {
                itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x, y, null, alpha);
            }
            GL11.glDisable(GL11.GL_DEPTH_TEST);
        }

        // Draw selection overlay
        if (isSelected)
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            drawRect(x, y, x + 16, y + 16, 0x80FFFFFF);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }

        // Clean up
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        Lighting.disable();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public void render(ItemStack itemStack, int x, int y, boolean isSelected)
    {
        render(itemStack, x, y, isSelected, null);
    }

    public void render(ItemStack itemStack, int x, int y)
    {
        render(itemStack, x, y, false);
    }
}
