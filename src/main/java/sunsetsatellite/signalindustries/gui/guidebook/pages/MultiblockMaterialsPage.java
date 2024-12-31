package sunsetsatellite.signalindustries.gui.guidebook.pages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiRenderItem;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.client.gui.guidebook.GuiGuidebook;
import net.minecraft.client.gui.guidebook.GuidebookPage;
import net.minecraft.client.gui.guidebook.GuidebookSection;
import net.minecraft.client.gui.guidebook.GuidebookSections;
import net.minecraft.client.gui.guidebook.PageManager;
import net.minecraft.client.gui.guidebook.search.SearchPage;
import net.minecraft.client.render.FontRenderer;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotGuidebook;
import net.minecraft.core.util.helper.Color;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.core.util.IColorOverride;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.signalindustries.SignalIndustries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiblockMaterialsPage extends GuidebookPage {
    public final Multiblock multiblock;
    private final GuiTooltip guiTooltip;
    private final GuiRenderItem guiRenderItem;
    private static final Minecraft mc = Minecraft.getMinecraft(GuidebookPage.class);
    public List<SlotGuidebook> slots = new ArrayList<>();
    private static long ticks = 0;


    public MultiblockMaterialsPage(GuidebookSection section, Multiblock multiblock) {
        super(section);
        this.multiblock = multiblock;
        guiTooltip = new GuiTooltip(mc);
        guiRenderItem = new GuiRenderItem(mc);

        List<ItemStack> blocksUncondensed = multiblock
                .getBlocks()
                .stream()
                .map((B) -> new ItemStack(B.block, 1, B.meta == -1 ? 0 : B.meta))
                .collect(Collectors.toList());
        List<ItemStack> blocks = SignalIndustries.condenseList(blocksUncondensed);
        ItemStack origin = new ItemStack(multiblock.getOrigin().block,1, multiblock.getOrigin().meta == -1 ? 0 : multiblock.getOrigin().meta);

        // Annoying special case for when the origin isn't that special
        boolean matched = false;
        for (ItemStack is : blocks) {
            if (origin.isItemEqual(is)) {
                is.stackSize += 1;
                matched = true;
                break;
            }
        }
        if (!matched) {
            blocks.add(origin);
        }

        int i = 0;
        int maxSlotsInRow = 7;
        for (ItemStack block : blocks) {
            slots.add(new SlotGuidebook(i,18 + 18 * (i % maxSlotsInRow),24 + 18 * (i / maxSlotsInRow),new RecipeSymbol(block),false,null));
            i++;
        }

    }


    @Override
    protected void renderForeground(RenderEngine re, FontRenderer fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if(multiblock != null){
            drawStringCenteredNoShadow(fr, I18n.getInstance().translateNameKey(multiblock.translateKey), x + 158 / 2, y + 10, 0x000000);
        } else {
            drawStringCenteredNoShadow(fr,"No results :(" ,x+width/2,y+height/2,0xFF808080);
        }
        SlotGuidebook mouseOverSlot = null;
        ticks++;
        for (SlotGuidebook slot : slots) {
            if(slot.item != null){
                slot.setDiscovered(mc.statsCounter.readStat(StatList.pickUpItemStats[slot.item.itemID]) > 0);
            }
            if(mc.thePlayer.gamemode == Gamemode.creative) slot.setDiscovered(true);
            if(ticks > 100) {
                slot.showRandomItem();
                if(slots.get(slots.size()-1) == slot){
                    ticks = 0;
                }
            }
            drawSlot(re,x+slot.xDisplayPosition-1,y+slot.yDisplayPosition-1,0xFFFFFFFF);
            if(getIsMouseOverSlot(slot,x,y,mouseX,mouseY)) mouseOverSlot = slot;
            if(slot.item != null && slot.item.itemID < 16384 && (Block.getBlock(slot.item.itemID) == Block.fluidWaterFlowing || Block.getBlock(slot.item.itemID) == Block.fluidWaterStill) && mc.gameSettings.biomeWater.value){
                BlockModel<?> blockModel = BlockModelDispatcher.getInstance().getDispatch(Block.getBlock(slot.item.itemID));
                ItemModel itemModel = ItemModelDispatcher.getInstance().getDispatch(slot.getStack().getItem());
                int waterColor = BlockColorDispatcher.getInstance().getDispatch(Block.fluidWaterFlowing).getWorldColor(mc.theWorld, (int) mc.thePlayer.x, (int) mc.thePlayer.y, (int) mc.thePlayer.z);
                Color c = new Color().setARGB(waterColor);
                c.setRGBA(c.getRed(),c.getGreen(),c.getBlue(),0x40);
                ((IColorOverride)blockModel).overrideColor(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
                
                guiRenderItem.render(slot.getStack(),x+slot.xDisplayPosition,y+slot.yDisplayPosition,mouseOverSlot == slot,slot);
                ((IColorOverride)blockModel).overrideColor(1,1,1,1);
                
            } else {
                guiRenderItem.render(slot.getStack(),x+slot.xDisplayPosition,y+slot.yDisplayPosition,mouseOverSlot == slot,slot);
            }
        }
    }

    public boolean getIsMouseOverSlot(final Slot slot, int x, int y, int mouseX, int mouseY)
    {
        return mouseX >= x+slot.xDisplayPosition - 1 && mouseX < x+slot.xDisplayPosition + 16 + 1 && mouseY >= y+slot.yDisplayPosition - 1 && mouseY < y+slot.yDisplayPosition + 16 + 1;
    }

    @Override
    public void keyTyped(char c, int key, int x, int y, int mouseX, int mouseY) {
        super.keyTyped(c, key, x, y, mouseX, mouseY);
        if(mc.gameSettings.keyShowRecipe.isKeyboardKey(key)){
            SlotGuidebook hoveringSlot= null;
            for (SlotGuidebook slot : slots) {
                if(getIsMouseOverSlot(slot,x,y,mouseX,mouseY)) hoveringSlot = slot;
            }
            if(hoveringSlot != null){
                if(hoveringSlot.hasStack()){
                    String query = "r:"+hoveringSlot.getStack().getDisplayName()+"!";
                    PageManager.searchQuery = SearchQuery.resolve(query);
                    SearchPage.searchField.setText(query);
                    GuiGuidebook.getPageManager().updatePages();
                    GuiGuidebook.getPageManager().setCurrentPage(GuiGuidebook.getPageManager().getSectionIndex(GuidebookSections.CRAFTING), true);
                }
            }
        } else if (mc.gameSettings.keyShowUsage.isKeyboardKey(key)) {
            SlotGuidebook hoveringSlot= null;
            for (SlotGuidebook slot : slots) {
                if(getIsMouseOverSlot(slot,x,y,mouseX,mouseY)) hoveringSlot = slot;
            }
            if(hoveringSlot != null) {
                if (hoveringSlot.hasStack()) {
                    String query = "u:" + hoveringSlot.getStack().getDisplayName() + "!";
                    PageManager.searchQuery = SearchQuery.resolve(query);
                    SearchPage.searchField.setText(query);
                    GuiGuidebook.getPageManager().updatePages();
                    GuiGuidebook.getPageManager().setCurrentPage(GuiGuidebook.getPageManager().getSectionIndex(GuidebookSections.CRAFTING), true);
                }
            }
        }
    }

    @Override
    protected void renderOverlay(RenderEngine re, FontRenderer fr, int x, int y, int mouseX, int mouseY, float partialTicks) {
        super.renderOverlay(re, fr, x, y, mouseX, mouseY, partialTicks);
        SlotGuidebook mouseOverSlot = null;
        for (SlotGuidebook slot : slots) {
            if(getIsMouseOverSlot(slot,x,y,mouseX,mouseY)) mouseOverSlot = slot;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (mouseOverSlot != null && mouseOverSlot.hasStack())
            {
                boolean showDescription = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
                String str = guiTooltip.getTooltipText(mouseOverSlot.getStack(), showDescription, mouseOverSlot);
                if(!str.isEmpty())
                {
                    guiTooltip.render(str, mouseX, mouseY, 8, -8);
                }
            }
        }
    }


}
