package sunsetsatellite.signalindustries.inventories.machines.multiblocks.basic;

import net.minecraft.core.block.Block;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.catalyst.multiblocks.MultiblockInstance;
import sunsetsatellite.signalindustries.SIRecipes;
import sunsetsatellite.signalindustries.inventories.base.TileEntityTieredMultiblock;
import sunsetsatellite.signalindustries.util.Tier;

public class TileEntityInductionSmelter extends TileEntityTieredMultiblock {

    @Override
    public void init(Block block) {
        usesEnergy = true;
        usesItemInput = true;
        usesItemOutput = true;
        minimumEnergyTier = Tier.BASIC;
        minimumItemInputTier = Tier.BASIC;
        minimumItemOutputTier = Tier.BASIC;

        recipeGroup = SIRecipes.INDUCTION_SMELTER;

        multiblock = new MultiblockInstance(this, Multiblock.multiblocks.get("basicInductionSmelter"));

        parallel = 16;
    }

    /*public MultiblockInstance multiblock;
    public TileEntityItemBus input;
    public TileEntityItemBus output;
    public TileEntityEnergyConnector energy;
    public RecipeGroup<RecipeEntryFurnace> recipeGroup;
    public RecipeEntryFurnace currentRecipe;
    private final int ticks = 100;
    public Random random = new Random();
    private final int cost = 40;

    public TileEntityInductionSmelter(){
        itemContents = new ItemStack[0];
        fluidContents = new FluidStack[0];
        fluidCapacity = new int[0];
        recipeGroup = Registries.RECIPES.FURNACE;
        progressMaxTicks = ticks;
        multiblock = new MultiblockInstance(this,Multiblock.multiblocks.get("basicInductionSmelter"));
    }

    @Override
    public void init(Block block) {
        multiblock = new MultiblockInstance(this,Multiblock.multiblocks.get("basicInductionSmelter"));
    }

    @Override
    public MultiblockInstance getMultiblock() {
        return multiblock;
    }

    @Override
    public void tick() {
        if(multiblock == null){
            return;
        }
        super.tick();
        worldObj.markBlocksDirty(x,y,z,x,y,z);
        BlockContainerTiered block = (BlockContainerTiered) getBlockType();
        input = null;
        output = null;
        energy = null;
        if(multiblock.isValid()){
            Direction dir = Direction.getDirectionFromSide(getMovedData());
            ArrayList<BlockInstance> tileEntities = multiblock.data.getTileEntities(worldObj,new Vec3i(x,y,z), dir);
            for (BlockInstance tileEntity : tileEntities) {
                if (tileEntity.tile instanceof IMultiblockPart) {
                    if(tileEntity.tile instanceof TileEntityItemBus && tileEntity.block == SIBlocks.basicItemInputBus){
                        input = (TileEntityItemBus) tileEntity.tile;
                    } else if(tileEntity.tile instanceof TileEntityItemBus && tileEntity.block == SIBlocks.basicItemOutputBus){
                        output = (TileEntityItemBus) tileEntity.tile;
                    } else if(tileEntity.tile instanceof TileEntityEnergyConnector && tileEntity.block == SIBlocks.basicEnergyConnector){
                        energy = (TileEntityEnergyConnector) tileEntity.tile;
                    }
                    ((IMultiblockPart) tileEntity.tile).connect(this);
                }
            }
            if(block != null && input != null && output != null && energy != null){
                setCurrentRecipe();
                work();
            }
        }
    }

    public void work(){
        if(input != null && output != null && energy != null && multiblock.isValid()){
            boolean update = false;
            if(fuelBurnTicks > 0){
                fuelBurnTicks--;
            }
            ArrayList<ItemStack> inputContents = getInputContents();
            if(inputContents.isEmpty()){
                progressTicks = 0;
            } else if(canProcess()) {
                progressMaxTicks = (int) (ticks / speedMultiplier);
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
    private ArrayList<ItemStack> getInputContents() {
        return SignalIndustries.condenseList(Arrays.asList(input.itemContents));
    }

    private void processItem() {
        //TODO: this is broken -> possibly works without actually processing anything, will not work if the first slot is full but others are not
        if (currentRecipe instanceof RecipeEntryFurnace && multiblock.isValid() && canProcess()) {
            ItemStack stack = currentRecipe.getOutput() == null ? null : currentRecipe.getOutput().copy();
            int parallelAmount = 1;
            int k = 0;
            if (stack != null) {
                ItemStack recipeInputStack = currentRecipe.getInput().resolve().get(0);
                for (int i = 0; i < recipeInputStack.stackSize; i++) {
                    ItemStack[] contents = input.itemContents;
                    for (int j = 0; j < contents.length; j++) {
                        ItemStack currentInputStack = contents[j];
                        if (currentInputStack != null && currentInputStack.isItemEqual(recipeInputStack)) {
                            parallelAmount = Math.min(currentInputStack.stackSize,16);
                            currentInputStack.stackSize -= parallelAmount;
                            k++;
                            if (currentInputStack.stackSize <= 0) {
                                contents[j] = null;
                            }
                            break;
                        }
                    }
                }
                if(k == recipeInputStack.stackSize) {
                    int multiplier = 1;
                    float fraction = Float.parseFloat("0."+(String.valueOf(yield).split("\\.")[1]));
                    if(fraction <= 0) fraction = 1;
                    if(yield > 1 && random.nextFloat() <= fraction){
                        multiplier = (int) Math.ceil(yield);
                    }
                    ItemStack[] itemStacks = this.output.itemContents;
                    for (int i = 0; i < itemStacks.length; i++) {
                        ItemStack outputStack = itemStacks[i];
                        if (outputStack != null && outputStack.isItemEqual(stack)) {
                            outputStack.stackSize += ((stack.stackSize * parallelAmount) * multiplier);
                            break;
                        } else if (outputStack == null) {
                            stack.stackSize *= parallelAmount;
                            stack.stackSize *= multiplier;
                            output.setInventorySlotContents(i,stack);
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean fuel(){
        int burn = SignalIndustries.getEnergyBurnTime(energy.fluidContents[0]);
        if(burn > 0 && canProcess() && currentRecipe != null){
            if(energy.fluidContents[0].amount >= cost){
                progressMaxTicks = (int) (ticks / speedMultiplier);
                fuelMaxBurnTicks = fuelBurnTicks = burn;
                energy.fluidContents[0].amount -= cost;
                if (energy.fluidContents[0].amount == 0) {
                    energy.fluidContents[0] = null;
                }
                return true;
            }
        }
        return false;
    }

    private boolean canProcess() {
        if(input != null && output != null && multiblock.isValid()){
            ArrayList<ItemStack> inputContents = getInputContents();
            if(inputContents.isEmpty()){
                return false;
            }
            List<RecipeEntryFurnace> list = Registries.RECIPES.getAllFurnaceRecipes();
            ItemStack recipeOutput = null;
            label:
            for (RecipeEntryFurnace recipe : list) {
                if(recipe != null){
                    for (ItemStack inputContent : inputContents) {
                        if(recipe.matches(inputContent)){
                            recipeOutput = recipe.getOutput().copy();
                            break label;
                        }
                    }
                }
            }
            if(recipeOutput == null){
                return false;
            }
            if(Arrays.stream(this.output.itemContents).noneMatch(Objects::nonNull)){
                return true;
            }
            boolean can = false;
            for (ItemStack outputStack : this.output.itemContents) {
                if(outputStack != null && outputStack.isItemEqual(recipeOutput)){
                    int parallelAmount = Math.min(outputStack.stackSize,8);
                    recipeOutput.stackSize *= parallelAmount;
                    if(yield > 1){
                        int n = recipeOutput.stackSize+(outputStack.stackSize*((int) Math.ceil(yield)));
                        if (((n <= getInventoryStackLimit()) || n <= recipeOutput.getMaxStackSize()) && n <= outputStack.getMaxStackSize()) {
                            can = true;
                            break;
                        }
                    } else {
                        int n = recipeOutput.stackSize + outputStack.stackSize;
                        if (((n <= getInventoryStackLimit()) || n <= recipeOutput.getMaxStackSize()) && n <= outputStack.getMaxStackSize()) {
                            can = true;
                            break;
                        }
                    }
                } else if(outputStack == null){
                    can = true;
                    break;
                }
            }
            return can;
        }
        return false;
    }


    public void setCurrentRecipe(){
        if(input != null && multiblock.isValid()){
            ArrayList<ItemStack> inputContents = getInputContents();
            List<RecipeEntryFurnace> list = Registries.RECIPES.getAllFurnaceRecipes();
            for (RecipeEntryFurnace recipe : list) {
                if(recipe != null){
                    for (ItemStack inputContent : inputContents) {
                        if(recipe.matches(inputContent)){
                            currentRecipe = recipe;
                            return;
                        }
                    }
                }
            }
        }
    }*/
}
