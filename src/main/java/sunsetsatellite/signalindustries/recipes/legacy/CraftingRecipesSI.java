package sunsetsatellite.signalindustries.recipes.legacy;


import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.legacy.CraftingManager;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import sunsetsatellite.signalindustries.SignalIndustries;
@Deprecated
public class CraftingRecipesSI {

    public void addRecipes() {
        createRecipe(new ItemStack(SignalIndustries.ironPlateHammer, 1),new Object[]{"012","345","678",'1',new ItemStack(Item.ingotIron,1,0),'4',new ItemStack(Item.stick,1,0),'5',new ItemStack(Item.ingotIron,1,0),'6',new ItemStack(Item.stick,1,0)});
        createRecipe(new ItemStack(SignalIndustries.diamondCuttingGear, 1),new Object[]{"012","345","678",'1',new ItemStack(Item.diamond,1,0),'3',new ItemStack(Item.diamond,1,0),'5',new ItemStack(Item.diamond,1,0),'7',new ItemStack(Item.diamond,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeMachineCore, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(SignalIndustries.stonePlate,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(SignalIndustries.stonePlate,1,0),'4',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'5',new ItemStack(SignalIndustries.stonePlate,1,0),'6',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'7',new ItemStack(SignalIndustries.stonePlate,1,0),'8',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createShapelessRecipe(new ItemStack(SignalIndustries.cobblestonePlate, 1),new Object[]{new ItemStack(SignalIndustries.ironPlateHammer,1,0),new ItemStack(Block.cobbleStone,1,0)});
        createShapelessRecipe(new ItemStack(SignalIndustries.stonePlate, 1),new Object[]{new ItemStack(SignalIndustries.ironPlateHammer,1,0),new ItemStack(Block.stone,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeConduit, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.stonePlate,1,0),'1',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'2',new ItemStack(SignalIndustries.stonePlate,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(Block.glass,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.stonePlate,1,0),'7',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'8',new ItemStack(SignalIndustries.stonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeFluidConduit, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(SignalIndustries.stonePlate,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(Block.glass,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'7',new ItemStack(SignalIndustries.stonePlate,1,0),'8',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeFluidTank, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(SignalIndustries.stonePlate,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(SignalIndustries.stonePlate,1,0),'4',new ItemStack(Block.glass,1,0),'5',new ItemStack(SignalIndustries.stonePlate,1,0),'6',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'7',new ItemStack(SignalIndustries.stonePlate,1,0),'8',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeExtractor, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'6',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'7',new ItemStack(Block.furnaceStoneIdle,1,0),'8',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeEnergyCell, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'1',new ItemStack(Block.glass,1,0),'2',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'7',new ItemStack(Block.glass,1,0),'8',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeCrusher, 1),new Object[]{"012","345","678",'0',new ItemStack(Item.flint,1,0),'1',new ItemStack(Item.flint,1,0),'2',new ItemStack(Item.flint,1,0),'3',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'7',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeAlloySmelter, 1),new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'3',new ItemStack(Block.furnaceBlastIdle,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(Block.furnaceBlastIdle,1,0),'7',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypePlateFormer, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(SignalIndustries.ironPlateHammer,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(SignalIndustries.ironPlateHammer,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(SignalIndustries.ironPlateHammer,1,0),'6',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'7',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'8',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypeCrystalCutter, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(SignalIndustries.diamondCuttingGear,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(SignalIndustries.diamondCuttingGear,1,0),'6',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'7',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'8',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicMachineCore, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.steelPlate,1,0),'1',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.steelPlate,1,0),'3',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.signalumCrystal,1,0),'5',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.steelPlate,1,0),'7',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.steelPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicConduit, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(SignalIndustries.prototypeConduit,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicFluidConduit, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.steelPlate,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(SignalIndustries.prototypeFluidConduit,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.steelPlate,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.evilCatalyst, 1),new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.monsterShard,1,0),'1',new ItemStack(SignalIndustries.monsterShard,1,0),'2',new ItemStack(SignalIndustries.monsterShard,1,0),'3',new ItemStack(SignalIndustries.monsterShard,1,0),'4',new ItemStack(Item.ammoChargeExplosive,1,0),'5',new ItemStack(SignalIndustries.monsterShard,1,0),'6',new ItemStack(SignalIndustries.monsterShard,1,0),'7',new ItemStack(SignalIndustries.monsterShard,1,0),'8',new ItemStack(SignalIndustries.monsterShard,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicWrathBeacon, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.monsterShard,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(Item.bone,1,0),'4',new ItemStack(Block.mobspawnerDeactivated,1,0),'5',new ItemStack(Item.bone,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.basicMachineCore,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.rawCrystalBlock, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'1',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'2',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'3',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'4',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'5',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'6',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'7',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0),'8',new ItemStack(SignalIndustries.rawSignalumCrystal,1,0)});
        createRecipe(new ItemStack(SignalIndustries.dilithiumBlock, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.dilithiumShard,1,0),'1',new ItemStack(SignalIndustries.dilithiumShard,1,0),'2',new ItemStack(SignalIndustries.dilithiumShard,1,0),'3',new ItemStack(SignalIndustries.dilithiumShard,1,0),'4',new ItemStack(SignalIndustries.dilithiumShard,1,0),'5',new ItemStack(SignalIndustries.dilithiumShard,1,0),'6',new ItemStack(SignalIndustries.dilithiumShard,1,0),'7',new ItemStack(SignalIndustries.dilithiumShard,1,0),'8',new ItemStack(SignalIndustries.dilithiumShard,1,0)});
        createRecipe(new ItemStack(SignalIndustries.prototypePump, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'1',new ItemStack(Item.bucket,1,0),'2',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'3',new ItemStack(SignalIndustries.stonePlate,1,0),'4',new ItemStack(SignalIndustries.prototypeMachineCore,1,0),'5',new ItemStack(SignalIndustries.stonePlate,1,0),'6',new ItemStack(SignalIndustries.cobblestonePlate,1,0),'7',new ItemStack(SignalIndustries.prototypeFluidConduit,1,0),'8',new ItemStack(SignalIndustries.cobblestonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedCasing, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedGlass, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(Block.glass,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(Block.glass,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(Block.glass,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedMachineCore, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'4',new ItemStack(SignalIndustries.basicMachineCore,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedConduit, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(SignalIndustries.basicConduit,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.dilithiumRail, 8), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.dilithiumShard,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'4',new ItemStack(Item.stick,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.dilithiumShard,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicEnergyCore, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.crystalChip,1,0),'4',new ItemStack(Item.ingotGold,1,0),'7',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedEnergyCore, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.pureCrystalChip,1,0),'4',new ItemStack(Item.dustRedstone,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.infernalEye, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.infernalFragment,1,0),'1',new ItemStack(SignalIndustries.infernalFragment,1,0),'2',new ItemStack(SignalIndustries.infernalFragment,1,0),'3',new ItemStack(SignalIndustries.infernalFragment,1,0),'4',new ItemStack(SignalIndustries.evilCatalyst,1,0),'5',new ItemStack(SignalIndustries.infernalFragment,1,0),'6',new ItemStack(SignalIndustries.infernalFragment,1,0),'7',new ItemStack(SignalIndustries.infernalFragment,1,0),'8',new ItemStack(SignalIndustries.infernalFragment,1,0)});
        createRecipe(new ItemStack(SignalIndustries.emptyCrystalBlock, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'1',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'3',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'4',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'5',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'7',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyIngot,1,0)});
        createShapelessRecipe(new ItemStack(SignalIndustries.dilithiumShard,9),new Object[]{new ItemStack(SignalIndustries.dilithiumBlock,1)});
        createShapelessRecipe(new ItemStack(SignalIndustries.rawSignalumCrystal,9),new Object[]{new ItemStack(SignalIndustries.rawCrystalBlock,1)});
        createShapelessRecipe(new ItemStack(SignalIndustries.crystalAlloyIngot,9),new Object[]{new ItemStack(SignalIndustries.emptyCrystalBlock,1)});
        createRecipe(new ItemStack(SignalIndustries.basicDrillCasing, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'5',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.dilithiumControlCore, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.dilithiumChip,1,0),'3',new ItemStack(SignalIndustries.dilithiumShard,1,0),'4',new ItemStack(SignalIndustries.dilithiumPlate,1,0),'5',new ItemStack(SignalIndustries.dilithiumShard,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.warpManipulatorCircuit, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.dimensionalChip,1,0),'4',new ItemStack(SignalIndustries.dimensionalShard,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.fluidManipulationCircuit, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.stonePlate,1,0),'1',new ItemStack(Item.dye,1,14),'2',new ItemStack(SignalIndustries.stonePlate,1,0),'3',new ItemStack(Item.dye,1,4),'4',new ItemStack(Item.bucket,1,0),'5',new ItemStack(Item.dye,1,14),'6',new ItemStack(SignalIndustries.stonePlate,1,0),'7',new ItemStack(Item.dye,1,4),'8',new ItemStack(SignalIndustries.stonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.fuelCell, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.reinforcedGlass,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.nullTrigger, 1), new Object[]{"012","345","678",'1',new ItemStack(Block.buttonStone,1,0),'3',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.basicEnergyCore,1,0),'5',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.pulsarShell, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.pulsarInnerCore, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.rawCrystalBlock,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.pulsarOuterCore, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.signalumCrystal,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.pulsar, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.pulsarInnerCore,1,0),'3',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'4',new ItemStack(SignalIndustries.pulsarShell,1,0),'5',new ItemStack(SignalIndustries.pulsarOuterCore,1,0),'7',new ItemStack(SignalIndustries.fuelCell,1,0)});
        createRecipe(new ItemStack(SignalIndustries.signalumPrototypeHarnessGoggles, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.basicEnergyCore,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'4',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'6',new ItemStack(Block.glass,1,0),'8',new ItemStack(Block.glass,1,0)});
        createRecipe(new ItemStack(SignalIndustries.signalumSaber, 1), new Object[]{"012","345","678",'2',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.warpOrb, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.dimensionalShard,1,0),'1',new ItemStack(SignalIndustries.dimensionalShard,1,0),'2',new ItemStack(SignalIndustries.dimensionalShard,1,0),'3',new ItemStack(SignalIndustries.dimensionalShard,1,0),'4',new ItemStack(SignalIndustries.emptyCrystalBlock,1,0),'5',new ItemStack(SignalIndustries.dimensionalShard,1,0),'6',new ItemStack(SignalIndustries.dimensionalShard,1,0),'7',new ItemStack(SignalIndustries.dimensionalShard,1,0),'8',new ItemStack(SignalIndustries.dimensionalShard,1,0)});

        createRecipe(new ItemStack(SignalIndustries.basicEnergyInjector, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.signalumCrystal,1,0),'5',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.basicEnergyCore,1,0),'7',new ItemStack(SignalIndustries.basicMachineCore,1,0),'8',new ItemStack(SignalIndustries.basicEnergyCore,1,0)});        createRecipe(new ItemStack(SignalIndustries.basicEnergyCell, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.prototypeEnergyCell,1,0),'5',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.basicMachineCore,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicCrusher, 1), new Object[]{"012","345","678",'1',new ItemStack(Item.flint,1,0),'3',new ItemStack(Item.flint,1,0),'4',new ItemStack(SignalIndustries.prototypeCrusher,1,0),'5',new ItemStack(Item.flint,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.basicMachineCore,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicExtractor, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.signalumCrystal,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(Block.furnaceStoneIdle,1,0),'4',new ItemStack(SignalIndustries.prototypeExtractor,1,0),'5',new ItemStack(Block.furnaceStoneIdle,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.basicMachineCore,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicAutomaticMiner, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.basicEnergyCore,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(Item.toolPickaxeSteel,1,0),'4',new ItemStack(SignalIndustries.basicMachineCore,1,0),'5',new ItemStack(Item.toolPickaxeSteel,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicCrystalChamber, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.signalumCrystal,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.prototypePlateFormer,1,0),'4',new ItemStack(SignalIndustries.basicMachineCore,1,0),'5',new ItemStack(SignalIndustries.prototypeAlloySmelter,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.signalumCrystal,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicAlloySmelter, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.basicMachineCore,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(Block.furnaceBlastIdle,1,0),'4',new ItemStack(SignalIndustries.prototypeAlloySmelter,1,0),'5',new ItemStack(Block.furnaceBlastIdle,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicInfuser, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'4',new ItemStack(SignalIndustries.basicMachineCore,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumCrystalDust,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(Item.bucket,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicDrillBit, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'2',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'3',new ItemStack(SignalIndustries.steelPlate,1,0),'4',new ItemStack(SignalIndustries.steelPlate,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'7',new ItemStack(SignalIndustries.steelPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicSignalumDrill, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.steelPlate,1,0),'2',new ItemStack(SignalIndustries.basicDrillBit,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'4',new ItemStack(SignalIndustries.basicDrillCasing,1,0),'5',new ItemStack(SignalIndustries.steelPlate,1,0),'6',new ItemStack(SignalIndustries.basicEnergyCore,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedDrillCasing, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.basicDrillCasing,1,0),'4',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedDrillBit, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.basicDrillBit,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedSignalumDrill, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.reinforcedDrillBit,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.reinforcedDrillCasing,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'7',new ItemStack(SignalIndustries.saturatedSignalumAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedFluidConduit, 4), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(Block.glass,1,0),'4',new ItemStack(SignalIndustries.basicFluidConduit,1,0),'5',new ItemStack(Block.glass,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.itemManipulationCircuit, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.stonePlate,1,0),'1',new ItemStack(Item.dye,1,4),'2',new ItemStack(SignalIndustries.stonePlate,1,0),'3',new ItemStack(Item.dye,1,14),'4',new ItemStack(SignalIndustries.steelPlate,1,0),'5',new ItemStack(Item.dye,1,4),'6',new ItemStack(SignalIndustries.stonePlate,1,0),'7',new ItemStack(Item.dye,1,14),'8',new ItemStack(SignalIndustries.stonePlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.externalIo, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.itemManipulationCircuit,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.itemManipulationCircuit,1,0),'4',new ItemStack(SignalIndustries.basicMachineCore,1,0),'5',new ItemStack(SignalIndustries.itemManipulationCircuit,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.itemManipulationCircuit,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedItemInputBus, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.itemManipulationCircuit,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedItemOutputBus, 1), new Object[]{"012","345","678",'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'7',new ItemStack(SignalIndustries.itemManipulationCircuit,1,0)});
        createRecipe(new ItemStack(SignalIndustries.dimensionalAnchor, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.warpOrb,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.dimensionalShard,1,0),'4',new ItemStack(SignalIndustries.warpManipulatorCircuit,1,0),'5',new ItemStack(SignalIndustries.dimensionalShard,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedIgnitor, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(Item.toolFirestrikerSteel,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'4',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'5',new ItemStack(SignalIndustries.saturatedSignalumAlloyIngot,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedEnergyConnector, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'3',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'5',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'7',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedFluidInputHatch, 1), new Object[]{"012","345","678",'1',new ItemStack(SignalIndustries.fluidManipulationCircuit,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedFluidOutputHatch, 1), new Object[]{"012","345","678",'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'7',new ItemStack(SignalIndustries.fluidManipulationCircuit,1,0)});
        createRecipe(new ItemStack(SignalIndustries.dilithiumStabilizer, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.dilithiumControlCore,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.dilithiumShard,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'5',new ItemStack(SignalIndustries.dilithiumShard,1,0),'6',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.dilithiumShard,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.dilithiumBooster, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.dilithiumBlock,1,0),'2',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'5',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'6',new ItemStack(SignalIndustries.dilithiumShard,1,0),'7',new ItemStack(SignalIndustries.dilithiumControlCore,1,0),'8',new ItemStack(SignalIndustries.dilithiumShard,1,0)});
        createRecipe(new ItemStack(SignalIndustries.reinforcedCentrifuge, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.fuelCell,1,0),'2',new ItemStack(SignalIndustries.signalumCrystal,1,0),'3',new ItemStack(SignalIndustries.fuelCell,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'5',new ItemStack(SignalIndustries.fuelCell,1,0),'6',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'7',new ItemStack(SignalIndustries.fuelCell,1,0),'8',new ItemStack(SignalIndustries.reinforcedCrystalAlloyPlate,1,0)});
        createRecipe(new ItemStack(SignalIndustries.signalumReactorCore, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.reinforcedCasing,1,0),'1',new ItemStack(SignalIndustries.rawCrystalBlock,1,0),'2',new ItemStack(SignalIndustries.reinforcedCasing,1,0),'3',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'4',new ItemStack(SignalIndustries.reinforcedMachineCore,1,0),'5',new ItemStack(SignalIndustries.reinforcedEnergyCore,1,0),'6',new ItemStack(SignalIndustries.reinforcedCasing,1,0),'7',new ItemStack(SignalIndustries.energyCatalyst,1,0),'8',new ItemStack(SignalIndustries.reinforcedCasing,1,0)});
        createRecipe(new ItemStack(SignalIndustries.basicCrystalCutter, 1), new Object[]{"012","345","678",'0',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'1',new ItemStack(SignalIndustries.diamondCuttingGear,1,0),'2',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'3',new ItemStack(SignalIndustries.signalumCrystal,1,0),'4',new ItemStack(SignalIndustries.prototypeCrystalCutter,1,0),'5',new ItemStack(SignalIndustries.signalumCrystal,1,0),'6',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0),'7',new ItemStack(SignalIndustries.basicMachineCore,1,0),'8',new ItemStack(SignalIndustries.crystalAlloyPlate,1,0)});
    }
    @Deprecated
    private void createRecipe(ItemStack output, Object[] inputs){
        CraftingManager.getInstance().addRecipe(output,inputs);
        //((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddRecipe(output, inputs);
        //IRecipe recipe = RecipeHelper.craftingManager.getRecipeList().get(RecipeHelper.craftingManager.getRecipeList().size()-1);

    }
    @Deprecated
    private void createShapelessRecipe(ItemStack output, Object[] inputs){
        CraftingManager.getInstance().addShapelessRecipe(output,inputs);
        //((CraftingManagerAccessor) RecipeHelper.craftingManager).callAddShapelessRecipe(output, inputs);
        //IRecipe recipe = RecipeHelper.craftingManager.getRecipeList().get(RecipeHelper.craftingManager.getRecipeList().size()-1);
    }
}