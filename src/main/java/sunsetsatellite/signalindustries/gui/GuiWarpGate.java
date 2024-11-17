package sunsetsatellite.signalindustries.gui;


import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.catalyst.fluids.impl.GuiFluid;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.containers.ContainerWarpGate;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredContainer;
import sunsetsatellite.signalindustries.inventories.machines.multiblocks.awakened.TileEntityWarpGate;
import sunsetsatellite.signalindustries.items.ItemWarpOrb;

public class GuiWarpGate extends GuiFluid {

    public String name = "Warp Gate";
    public EntityPlayer entityplayer;
    public TileEntityWarpGate tile;


    public GuiWarpGate(InventoryPlayer inventoryPlayer, TileEntity tile) {
        super(new ContainerWarpGate(inventoryPlayer, (TileEntityTieredContainer) tile),inventoryPlayer);
        this.ySize = 192;
        this.tile = (TileEntityWarpGate) tile;
        this.entityplayer = inventoryPlayer.player;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {
        int i2 = this.mc.renderEngine.getTexture("/assets/signalindustries/gui/warp_gate.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(i2);
        int i3 = (this.width - this.xSize) / 2;
        int i4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        int color = 0xFFFFFFFF;
        switch (((BlockContainerTiered)tile.getBlockType()).tier){
            case PROTOTYPE:
                break;
            case BASIC:
                color = 0xFFFF8080;
                break;
            case REINFORCED:
                color = 0xFFFF0000;
                break;
            case AWAKENED:
                color = 0xFFFFA500;
                break;
        }
        fontRenderer.drawCenteredString(name, 90, 6, color);
        if(tile.itemContents[0] != null && tile.itemContents[0].getItem() instanceof ItemWarpOrb){
            fontRenderer.drawCenteredString(((ItemWarpOrb) tile.itemContents[0].getItem()).getLocationString(tile.itemContents[0]),90,20,0xFFEEEEEE);
        } else {
            fontRenderer.drawCenteredString("No location",90,20,0xFF808080);
        }
        switch (tile.state){
            case IDLE:
                fontRenderer.drawCenteredString("Idle",89,70,0xFFEEEEEE);
                break;
            case CHARGING:
                fontRenderer.drawCenteredString("Charging...",89,70,0xFFFF8000);
                break;
            case CONNECTED_ONE_WAY:
                fontRenderer.drawCenteredString("Active! (One-way)",89,70,0xFF00FF00);
                break;
            case CONNECTED_TWO_WAY:
                fontRenderer.drawCenteredString("Active! (Two-way)",89,70,0xFF00FF00);
                break;
            case STABILIZATION_FAILURE:
                fontRenderer.drawCenteredString("/!\\ STABILIZATION FAILURE /!\\",89,70,0xFFFF0000);
                break;
            case POWER_FAILURE:
                fontRenderer.drawCenteredString("/!\\ POWER FAILURE /!\\",89,70,0xFFFF0000);
                break;
        }

    }

    public void init()
    {
        super.init();
    }
}
