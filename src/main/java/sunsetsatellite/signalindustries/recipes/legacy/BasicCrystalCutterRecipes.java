package sunsetsatellite.signalindustries.recipes.legacy;


import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.signalindustries.SIItems;

import java.util.ArrayList;
@Deprecated
public class BasicCrystalCutterRecipes extends CrystalCutterRecipes {
    private static final BasicCrystalCutterRecipes instance = new BasicCrystalCutterRecipes();

    public static BasicCrystalCutterRecipes getInstance() {
        return instance;
    }

    protected BasicCrystalCutterRecipes() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(new FluidStack((BlockFluid) Block.fluidWaterFlowing,500));
        list.add(new ItemStack(SIItems.rawSignalumCrystal,4));
        list.add(1);
        addRecipe(list,new ItemStack(SIItems.crystalChip.id,1,0));
        list = new ArrayList<>();
        list.add(new FluidStack((BlockFluid) Block.fluidWaterFlowing,2000));
        list.add(new ItemStack(SIItems.signalumCrystal,1));
        list.add(0);
        addRecipe(list,new ItemStack(SIItems.pureCrystalChip.id,2,0));
        //addRecipe(new ArrayList<Object>{new FluidStack((BlockFluid) Block.waterMoving,1000),new ItemStack(mod_SignalIndustries.rawSignalumCrystal,8)},new ItemStack(mod_SignalIndustries.signalumCrystal,1));
    }

}