package sunsetsatellite.signalindustries.api.impl.guidebookpp.recipes;

import net.minecraft.src.ItemStack;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.signalindustries.api.impl.guidebookpp.recipes.RecipeBase;

import java.util.ArrayList;

public class RecipeInfuser extends RecipeBase {

    public RecipeInfuser(ArrayList<ItemStack> itemInputs, ArrayList<FluidStack> fluidInputs, ArrayList<ItemStack> itemOutputs, ArrayList<FluidStack> fluidOutputs) {
        super(itemInputs, fluidInputs, itemOutputs, fluidOutputs);
    }
}