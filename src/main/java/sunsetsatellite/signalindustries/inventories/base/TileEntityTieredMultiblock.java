package sunsetsatellite.signalindustries.inventories.base;

import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.catalyst.core.util.BlockInstance;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.catalyst.core.util.mixin.interfaces.ITileEntityInit;
import sunsetsatellite.catalyst.fluids.util.FluidStack;
import sunsetsatellite.catalyst.fluids.util.RecipeExtendedSymbol;
import sunsetsatellite.catalyst.multiblocks.IMultiblock;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SignalIndustries;
import sunsetsatellite.signalindustries.blocks.BlockFluidInputHatch;
import sunsetsatellite.signalindustries.blocks.BlockFluidOutputHatch;
import sunsetsatellite.signalindustries.blocks.BlockInputBus;
import sunsetsatellite.signalindustries.blocks.BlockOutputBus;
import sunsetsatellite.signalindustries.blocks.base.BlockContainerTiered;
import sunsetsatellite.signalindustries.interfaces.IMultiblockPart;
import sunsetsatellite.signalindustries.interfaces.ITiered;
import sunsetsatellite.signalindustries.inventories.TileEntityEnergyConnector;
import sunsetsatellite.signalindustries.inventories.TileEntityFluidHatch;
import sunsetsatellite.signalindustries.inventories.TileEntityItemBus;
import sunsetsatellite.signalindustries.recipes.RecipeGroupSI;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachine;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntryMachineFluid;
import sunsetsatellite.signalindustries.recipes.entry.RecipeEntrySI;
import sunsetsatellite.signalindustries.util.RecipeProperties;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.*;

public abstract class TileEntityTieredMultiblock extends TileEntityTieredMachineBase implements IMultiblock, ITileEntityInit {

    public MultiblockInstance multiblock;
    public TileEntityItemBus itemInput;
    public TileEntityItemBus itemOutput;
    public TileEntityFluidHatch fluidInput;
    public TileEntityFluidHatch fluidOutput;
    public TileEntityEnergyConnector energy;
    public boolean usesItemInput = false;
    public boolean usesItemOutput = false;
    public boolean usesFluidInput = false;
    public boolean usesFluidOutput = false;
    public boolean usesEnergy = false;
    public Tier minimumItemInputTier = Tier.BASIC;
    public Tier minimumItemOutputTier = Tier.BASIC;
    public Tier minimumFluidInputTier = Tier.BASIC;
    public Tier minimumFluidOutputTier = Tier.BASIC;
    public Tier minimumEnergyTier = Tier.BASIC;
    public RecipeGroupSI<?> recipeGroup;
    public RecipeEntrySI<?, ?, RecipeProperties> currentRecipe;
    public Random random = new Random();


    public TileEntityTieredMultiblock(){
        super();
        itemContents = new ItemStack[0];
        fluidContents = new FluidStack[0];
        fluidCapacity = new int[0];
    }

    @Override
    public void tick() {
        if(multiblock == null){
            return;
        }
        super.tick();
        worldObj.markBlocksDirty(x,y,z,x,y,z);
        BlockContainerTiered block = (BlockContainerTiered) getBlockType();
        itemInput = null;
        itemOutput = null;
        fluidInput = null;
        fluidOutput = null;
        energy = null;
        if(multiblock.isValid()){
            Direction dir = Direction.getDirectionFromSide(getMovedData());
            ArrayList<BlockInstance> tileEntities = multiblock.data.getTileEntities(worldObj,new Vec3i(x,y,z), dir);
            for (BlockInstance tileEntity : tileEntities) {
                if (tileEntity.tile instanceof IMultiblockPart && tileEntity.block instanceof ITiered) {
                    if(tileEntity.block instanceof BlockInputBus){
                        if(((ITiered) tileEntity.block).getTier().ordinal() >= minimumItemInputTier.ordinal()){
                            itemInput = (TileEntityItemBus) tileEntity.tile;
                        }
                    } else if(tileEntity.block instanceof BlockOutputBus){
                        if(((ITiered) tileEntity.block).getTier().ordinal() >= minimumItemOutputTier.ordinal()){
                            itemOutput = (TileEntityItemBus) tileEntity.tile;
                        }
                    } else if(tileEntity.tile instanceof TileEntityEnergyConnector){
                        if(((ITiered) tileEntity.block).getTier().ordinal() >= minimumEnergyTier.ordinal()){
                            energy = (TileEntityEnergyConnector) tileEntity.tile;
                        }
                    } else if(tileEntity.block instanceof BlockFluidInputHatch){
                        if(((ITiered) tileEntity.block).getTier().ordinal() >= minimumFluidInputTier.ordinal()){
                            fluidInput = (TileEntityFluidHatch) tileEntity.tile;
                        }
                    } else if(tileEntity.block instanceof BlockFluidOutputHatch){
                        if(((ITiered) tileEntity.block).getTier().ordinal() >= minimumFluidOutputTier.ordinal()){
                            fluidOutput = (TileEntityFluidHatch) tileEntity.tile;
                        }
                    }
                    ((IMultiblockPart) tileEntity.tile).connect(this);
                }
            }
            if(block != null && allPartsPresent()) {
                setCurrentRecipe();
                work();
            }

            if(isBurning()){
                ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
                for (BlockInstance structBlock : blocks) {
                    if(structBlock.block == SIBlocks.reinforcedCasing2 || structBlock.block == SIBlocks.awakenedSocketCasing || structBlock.block == SIBlocks.awakenedCasing2) {
                        if(structBlock.pos.getBlockMetadata(worldObj) != 1){
                            worldObj.setBlockMetadata(structBlock.pos.x, structBlock.pos.y, structBlock.pos.z, 1);
                        }
                    }
                }
            } else {
                ArrayList<BlockInstance> blocks = multiblock.data.getBlocks(new Vec3i(x, y, z), dir);
                for (BlockInstance structBlock : blocks) {
                    if(structBlock.block == SIBlocks.reinforcedCasing2 || structBlock.block == SIBlocks.awakenedSocketCasing || structBlock.block == SIBlocks.awakenedCasing2) {
                        if(structBlock.pos.getBlockMetadata(worldObj) == 1){
                            worldObj.setBlockMetadata(structBlock.pos.x, structBlock.pos.y, structBlock.pos.z, 0);
                        }
                    }
                }
            }
        }
    }

    public boolean allPartsPresent(){
        return (itemInput != null || !usesItemInput)
                && (itemOutput != null || !usesItemOutput)
                && (fluidInput != null || !usesFluidInput)
                && (fluidOutput != null || !usesFluidOutput)
                && (energy != null || !usesEnergy);
    }

    public void work(){
        if(multiblock.isValid() && allPartsPresent()){
            boolean update = false;
            if(fuelBurnTicks > 0){
                fuelBurnTicks--;
            }
            ArrayList<ItemStack> inputContents = getItemInputContents();
            if(inputContents.isEmpty()){
                progressTicks = 0;
            } else if(canProcess()) {
                progressMaxTicks = (int) (currentRecipe.getData().ticks / speedMultiplier);
            }
            if(!worldObj.isClientSide) {
                if (progressTicks == 0 && canProcess() && fuelBurnTicks < 2) {
                    update = fuel();
                }
                if (isBurning() && canProcess()) {
                    progressTicks++;
                    if (progressTicks >= progressMaxTicks) {
                        progressTicks = 0;
                        processItem();
                        update = true;
                    }
                } else if (canProcess()) {
                    fuel();
                    if (fuelBurnTicks > 0) {
                        fuelBurnTicks++;
                    }
                }
            }

            if(update) {
                this.onInventoryChanged();
            }
        }
    }

    @Override
    public boolean isBurning() {
        return super.isBurning() && multiblock.isValid();
    }

    @NotNull
    private ArrayList<ItemStack> getItemInputContents() {
        if(!allPartsPresent() || !usesItemInput){
            return new ArrayList<>();
        }
        return SignalIndustries.condenseList(Arrays.asList(itemInput.itemContents));
    }

    @NotNull
    private ArrayList<ItemStack> getItemOutputContents() {
        if(!allPartsPresent() || !usesItemOutput){
            return new ArrayList<>();
        }
        return SignalIndustries.condenseList(Arrays.asList(itemOutput.itemContents));
    }

    @NotNull
    private ArrayList<FluidStack> getFluidInputContents() {
        if(!allPartsPresent() || !usesFluidInput){
            return new ArrayList<>();
        }
        return SignalIndustries.condenseFluidList(Arrays.asList(fluidInput.fluidContents));
    }

    @NotNull
    private ArrayList<FluidStack> getFluidOutputContents() {
        if(!allPartsPresent() || !usesFluidOutput){
            return new ArrayList<>();
        }
        return SignalIndustries.condenseFluidList(Arrays.asList(fluidOutput.fluidContents));
    }

    @Override
    public MultiblockInstance getMultiblock() {
        return multiblock;
    }

    public boolean fuel(){
        if(allPartsPresent()){
            int burn = SignalIndustries.getEnergyBurnTime(energy.getFluidInSlot(0));
            if(burn > 0 && canProcess() && currentRecipe != null){
                if(energy.getFluidInSlot(0).amount >= currentRecipe.getData().cost){
                    progressMaxTicks = (int) (currentRecipe.getData().ticks / speedMultiplier);
                    fuelMaxBurnTicks = fuelBurnTicks = burn;
                    energy.getFluidInSlot(0).amount -= currentRecipe.getData().cost;
                    if (energy.getFluidInSlot(0).amount <= 0) {
                        energy.setFluidInSlot(0,null);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void setCurrentRecipe(){
        if(allPartsPresent()){
            List<Object> objs = new ArrayList<>();
            if(usesItemInput) objs.addAll(getItemInputContents());
            if(usesFluidInput) objs.addAll(getFluidInputContents());
            RecipeExtendedSymbol[] symbols = RecipeExtendedSymbol.arrayOf(objs);
            currentRecipe = recipeGroup.findRecipe(symbols,tier);
        }
    }


    public boolean canProcess(){
        if(allPartsPresent()) {
            if (currentRecipe instanceof RecipeEntryMachine) {
                RecipeEntryMachine recipe = ((RecipeEntryMachine) currentRecipe);
                ItemStack stack = recipe.getOutput();
                if (stack == null) {
                    return false;
                }
                return areItemOutputsValid(stack);
            } else if (currentRecipe instanceof RecipeEntryMachineFluid) {
                RecipeEntryMachineFluid recipe = ((RecipeEntryMachineFluid) currentRecipe);
                FluidStack fluidStack = recipe.getOutput();
                if (fluidStack == null) {
                    return false;
                }
                return areFluidOutputsValid(fluidStack);
            }
        }
        return false;
    }

    public boolean areItemOutputsValid(ItemStack stack){
        if(!usesItemOutput) return true;
        int outputAmountRemaining;
        if(yield > 1){
            outputAmountRemaining = (stack.stackSize*((int) Math.ceil(yield)));
        } else {
            outputAmountRemaining = stack.stackSize;
        }
        if(outputAmountRemaining <= 0) return true;
        for (ItemStack outputStack : itemOutput.itemContents) {
            if (outputStack != null) {
                if(outputStack.isItemEqual(stack)){
                    int maxFreeAmountInSlot = itemOutput.getInventoryStackLimit() - outputStack.stackSize;
                    int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                    outputAmountRemaining -= willTake;
                }
            } else {
                int maxFreeAmountInSlot = itemOutput.getInventoryStackLimit();
                int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                outputAmountRemaining -= willTake;
            }
            if(outputAmountRemaining <= 0){
                break;
            }
        }

        return outputAmountRemaining <= 0;
    }

    public boolean areFluidOutputsValid(FluidStack stack){
        if(!usesFluidOutput) return true;
        int outputAmountRemaining;
        if(yield > 1){
            outputAmountRemaining = (stack.amount*((int) Math.ceil(yield)));
        } else {
            outputAmountRemaining = stack.amount;
        }
        if(outputAmountRemaining <= 0) return true;
        FluidStack[] contents = fluidOutput.fluidContents;
        for (int i = 0; i < contents.length; i++) {
            FluidStack outputStack = contents[i];
            if (outputStack != null) {
                if (outputStack.isFluidEqual(stack)) {
                    int maxFreeAmountInSlot = fluidOutput.getFluidCapacityForSlot(i) - outputStack.amount;
                    int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                    outputAmountRemaining -= willTake;
                }
            } else {
                int maxFreeAmountInSlot = fluidOutput.getFluidCapacityForSlot(i);
                int willTake = Math.min(outputAmountRemaining, maxFreeAmountInSlot);
                outputAmountRemaining -= willTake;
            }
            if (outputAmountRemaining <= 0) {
                break;
            }
        }

        return outputAmountRemaining <= 0;
    }

    public void processItem(){
        if(canProcess()){
            if(currentRecipe instanceof RecipeEntryMachine){
                RecipeEntryMachine recipe = ((RecipeEntryMachine) currentRecipe);
                ItemStack stack = recipe.getOutput() == null ? null : recipe.getOutput().copy();
                if (stack != null) {
                    consumeInputs();
                    if(random.nextFloat() <= recipe.getData().chance){
                        int multiplier = 1;
                        float fraction = Float.parseFloat("0."+(String.valueOf(yield).split("\\.")[1]));
                        if(fraction <= 0) fraction = 1;
                        if(yield > 1 && random.nextFloat() <= fraction){
                            multiplier = (int) Math.ceil(yield);
                        }
                        int outputAmountRemaining = stack.stackSize * multiplier;
                        for (int i = 0; i < itemOutput.itemContents.length; i++) {
                            ItemStack outputStack = itemOutput.itemContents[i];
                            if (outputStack == null) {
                                int maxAmountInSlot = stack.getMaxStackSize();
                                if(maxAmountInSlot <= 0) continue;
                                int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                                if(willTake <= 0) continue;
                                ItemStack copy = stack.copy();
                                copy.stackSize = willTake;
                                itemOutput.setInventorySlotContents(i,copy);
                                outputAmountRemaining -= willTake;
                                if(outputAmountRemaining <= 0){
                                    break;
                                }
                            } else if (outputStack.isItemEqual(stack)) {
                                int maxAmountInSlot = stack.getMaxStackSize() - outputStack.stackSize;
                                if(maxAmountInSlot <= 0) continue;
                                int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                                if(willTake <= 0) continue;
                                outputStack.stackSize += willTake;
                                outputAmountRemaining -= willTake;
                                if(outputAmountRemaining <= 0){
                                    break;
                                }
                            }
                        }
                    }
                }
            } else if (currentRecipe instanceof RecipeEntryMachineFluid) {
                RecipeEntryMachineFluid recipe = ((RecipeEntryMachineFluid) currentRecipe);
                FluidStack fluidStack = recipe.getOutput() == null ? null : recipe.getOutput().copy();
                if (fluidStack != null) {
                    consumeInputs();
                    if(random.nextFloat() <= recipe.getData().chance) {
                        int multiplier = 1;
                        float fraction = Float.parseFloat("0."+(String.valueOf(yield).split("\\.")[1]));
                        if(fraction <= 0) fraction = 1;
                        if(yield > 1 && random.nextFloat() <= fraction){
                            multiplier = (int) Math.ceil(yield);
                        }
                        int outputAmountRemaining = fluidStack.amount * multiplier;
                        for (int i = 0; i < fluidOutput.itemContents.length; i++) {
                            FluidStack outputStack = fluidOutput.fluidContents[i];
                            if (outputStack == null) {
                                int maxAmountInSlot = fluidOutput.getFluidInSlot(i).amount;
                                if(maxAmountInSlot <= 0) continue;
                                int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                                if(willTake <= 0) continue;
                                FluidStack copy = fluidStack.copy();
                                copy.amount = willTake;
                                fluidOutput.setFluidInSlot(i,copy);
                                outputAmountRemaining -= willTake;
                                if(outputAmountRemaining <= 0){
                                    break;
                                }
                            } else if (outputStack.isFluidEqual(fluidStack)) {
                                int maxAmountInSlot = fluidOutput.getFluidCapacityForSlot(i) - outputStack.amount;
                                if(maxAmountInSlot <= 0) continue;
                                int willTake = Math.min(outputAmountRemaining, maxAmountInSlot);
                                if(willTake <= 0) continue;
                                outputStack.amount += willTake;
                                outputAmountRemaining -= willTake;
                                if(outputAmountRemaining <= 0){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void consumeInputs(){
        if(currentRecipe instanceof RecipeEntryMachine) {
            RecipeEntryMachine recipe = ((RecipeEntryMachine) currentRecipe);
            if(usesItemInput){
                for (int i = 0; i < itemInput.itemContents.length; i++) {
                    ItemStack inputStack = itemInput.getStackInSlot(i);
                    if(inputStack != null && inputStack.getItem().hasContainerItem() && !recipe.getData().consumeContainers){
                        setInventorySlotContents(i, new ItemStack(inputStack.getItem().getContainerItem()));
                    } else if (inputStack != null) {
                        Optional<ItemStack> recipeStack = Arrays.stream(recipe.getInput())
                                .flatMap(symbol -> symbol.resolve().stream())
                                .filter(Objects::nonNull)
                                .filter(stack -> stack.isItemEqual(inputStack))
                                .findFirst();
                        if(inputStack.getItem().hasContainerItem() && !recipe.getData().consumeContainers){
                            itemInput.setInventorySlotContents(i, inputStack.getItem().getContainerItem().getDefaultStack());
                        } else {
                            recipeStack.ifPresent(stack -> inputStack.stackSize -= stack.stackSize);
                            if (inputStack.stackSize <= 0) {
                                itemInput.setInventorySlotContents(i, null);
                            }
                        }

                    }
                }
            }
            if(usesFluidInput){
                for (int i = 0; i < fluidInput.fluidContents.length; i++) {
                    FluidStack inputStack = fluidInput.getFluidInSlot(i);
                    if(inputStack != null){
                        Optional<FluidStack> recipeStack = Arrays.stream(recipe.getInput())
                                .flatMap(symbol -> symbol.resolveFluids().stream())
                                .filter(Objects::nonNull)
                                .filter(stack -> stack.isFluidEqual(inputStack))
                                .findFirst();
                        recipeStack.ifPresent(stack -> inputStack.amount -= stack.amount);
                        if (inputStack.amount <= 0) {
                            fluidInput.setFluidInSlot(i, null);
                        }
                    }
                }
            }

        } else if (currentRecipe instanceof RecipeEntryMachineFluid) {
            RecipeEntryMachineFluid recipe = ((RecipeEntryMachineFluid) currentRecipe);
            if(usesItemInput) {
                for (int i = 0; i < itemInput.itemContents.length; i++) {
                    ItemStack inputStack = itemInput.getStackInSlot(i);
                    if (inputStack != null && inputStack.getItem().hasContainerItem() && !recipe.getData().consumeContainers) {
                        setInventorySlotContents(i, new ItemStack(inputStack.getItem().getContainerItem()));
                    } else if (inputStack != null) {
                        Optional<ItemStack> recipeStack = Arrays.stream(recipe.getInput())
                                .flatMap(symbol -> symbol.resolve().stream())
                                .filter(Objects::nonNull)
                                .filter(stack -> stack.isItemEqual(inputStack))
                                .findFirst();
                        if (inputStack.getItem().hasContainerItem() && !recipe.getData().consumeContainers) {
                            itemInput.setInventorySlotContents(i, inputStack.getItem().getContainerItem().getDefaultStack());
                        } else {
                            recipeStack.ifPresent(stack -> inputStack.stackSize -= stack.stackSize);
                            if (inputStack.stackSize <= 0) {
                                itemInput.setInventorySlotContents(i, null);
                            }
                        }
                    }
                }
            }
            if(usesFluidInput){
                for (int i = 0; i < fluidInput.fluidContents.length; i++) {
                    FluidStack inputStack = fluidInput.getFluidInSlot(i);
                    if(inputStack != null){
                        Optional<FluidStack> recipeStack = Arrays.stream(recipe.getInput())
                                .flatMap(symbol -> symbol.resolveFluids().stream())
                                .filter(Objects::nonNull)
                                .filter(stack -> stack.isFluidEqual(inputStack))
                                .findFirst();
                        recipeStack.ifPresent(stack -> inputStack.amount -= stack.amount);
                        if (inputStack.amount <= 0) {
                            fluidInput.setFluidInSlot(i, null);
                        }
                    }
                }
            }
        }
    }
}
