package sunsetsatellite.signalindustries.api.impl.vintagequesting;

import net.minecraft.client.Minecraft;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.signalindustries.SIBlocks;
import sunsetsatellite.signalindustries.SIDimensions;
import sunsetsatellite.signalindustries.SIItems;
import sunsetsatellite.vintagequesting.VintageQuesting;
import sunsetsatellite.vintagequesting.interfaces.IHasQuests;
import sunsetsatellite.vintagequesting.quest.template.ChapterTemplate;
import sunsetsatellite.vintagequesting.quest.template.QuestTemplate;
import sunsetsatellite.vintagequesting.quest.template.RewardTemplate;
import sunsetsatellite.vintagequesting.quest.template.TaskTemplate;
import sunsetsatellite.vintagequesting.quest.template.reward.ItemRewardTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.ClickTaskTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.RetrievalTaskTemplate;
import sunsetsatellite.vintagequesting.quest.template.task.VisitDimensionTaskTemplate;
import sunsetsatellite.vintagequesting.registry.ChapterRegistry;
import sunsetsatellite.vintagequesting.registry.QuestRegistry;
import sunsetsatellite.vintagequesting.registry.RewardRegistry;
import sunsetsatellite.vintagequesting.registry.TaskRegistry;
import sunsetsatellite.vintagequesting.util.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static sunsetsatellite.signalindustries.SIBlocks.*;
import static sunsetsatellite.signalindustries.SIItems.*;
import static sunsetsatellite.vintagequesting.VintageQuesting.listOf;
import static sunsetsatellite.vintagequesting.VintageQuesting.zip;


public class VintageQuestingSIPlugin {

    private static final Logger log = LoggerFactory.getLogger(VintageQuestingSIPlugin.class);

    public void initializePlugin() {

        List<QuestTemplate> prototypeQuests = addPrototypeQuests();
        List<QuestTemplate> basicQuests = addBasicQuests();
        List<QuestTemplate> reinforcedQuests = addReinforcedQuests();
        List<QuestTemplate> awakenedQuests = addAwakenedQuests();

        new ChapterTemplate("signalindustries:prototype",0, prototypeMachineCore,"chapter.signalindustries.prototype","chapter.signalindustries.prototype",
                prototypeQuests);
        new ChapterTemplate("signalindustries:basic",1, basicMachineCore,"chapter.signalindustries.basic","chapter.signalindustries.basic",
                basicQuests);
        new ChapterTemplate("signalindustries:reinforced",2, reinforcedMachineCore,"chapter.signalindustries.reinforced","chapter.signalindustries.reinforced",
                reinforcedQuests);
        new ChapterTemplate("signalindustries:awakened",3, awakenedMachineCore,"chapter.signalindustries.awakened","chapter.signalindustries.awakened",
                awakenedQuests);

        for (QuestTemplate quest : VintageQuesting.QUESTS) {
            if(Objects.equals(quest.getTranslatedName(), quest.getName() + ".name") || Objects.equals(quest.getTranslatedDescription(), quest.getDescription() + ".desc")) {
                System.out.printf(quest.getName()+".name="+quest.getIcon().getDefaultStack().getDisplayName()+"\n");
                System.out.printf(quest.getDescription()+".desc="+"\n");
            }
        }

        VintageQuesting.LOGGER.info("Loaded quests from: signalindustries!");

    }

    public List<QuestTemplate> addPrototypeQuests(){
        QuestTemplate welcome = new QuestTemplate("signalindustries:welcome","quest.signalindustries.welcome", rawSignalumCrystal, Logic.AND, Logic.AND)
                .setTasks(listOf(new ClickTaskTemplate("signalindustries:welcome/click")));

        QuestTemplate genesis = simpleQuest("genesis",signalumOre,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(rawSignalumCrystal,32)
                        )),
                zip(listOf("reward"),
                        listOf(
                                new ItemStack(rawSignalumCrystal,32)
                        )),
                listOf(welcome),welcome,
                0,
                64);

        QuestTemplate tome = simpleQuest("tome",raziel,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(raziel,1)
                        )),
                listOf(genesis),genesis,
                -64,
                0);

        QuestTemplate hammer = simpleQuest("hammer",ironPlateHammer,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(ironPlateHammer,1)
                        )),
                listOf(welcome),genesis,
                0,
                64);

        QuestTemplate cobblePlates = simpleQuest("cobblePlates",cobblestonePlate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(cobblestonePlate,4)
                        )),
                listOf(hammer),hammer,
                -64,
                64);

        QuestTemplate stonePlates = simpleQuest("stonePlates",stonePlate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(stonePlate,4)
                        )),
                listOf(hammer),hammer,
                64,
                64);

        QuestTemplate tablet = simpleQuest("tablet",configurationTablet,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(configurationTablet,1)
                        )),
                listOf(stonePlates,genesis),stonePlates,
                64,
                0);

        QuestTemplate ioConfig = simpleClickQuest("ioConfig",itemManipulationCircuit,
                listOf(tablet),tablet,
                64,
                0);

        QuestTemplate prototypeCore = simpleQuest("prototypeCore",prototypeMachineCore,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeMachineCore,1)
                        )),
                listOf(genesis,cobblePlates,stonePlates),hammer,
                0,
                128);

        QuestTemplate protoExtract = simpleQuest("prototypeExtractor", prototypeExtractor,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeExtractor,1)
                        )),
                listOf(prototypeCore),prototypeCore,
                0,
                64);

        QuestTemplate energy = simpleClickQuest("energy",energyFlowing,
                listOf(protoExtract),protoExtract,
                0,
                64);

        QuestTemplate protoPlate = simpleQuest("prototypePlateFormer", prototypePlateFormer,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypePlateFormer,1)
                        )),
                listOf(prototypeCore),prototypeCore,
                64,
                64);

        QuestTemplate protoSmelter = simpleQuest("prototypeAlloySmelter", prototypeAlloySmelter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeAlloySmelter,1)
                        )),
                listOf(prototypeCore),prototypeCore,
                -64,
                64);

        QuestTemplate protoCrusher = simpleQuest("prototypeCrusher", prototypeCrusher,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeCrusher,1)
                        )),
                listOf(prototypeCore),prototypeCore,
                128,
                64);

        QuestTemplate diamondGear = simpleQuest("diamondGear", diamondCuttingGear,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(diamondCuttingGear,1)
                        )),
                listOf(),prototypeCore,
                -128-64,
                64);

        QuestTemplate protoCutter = simpleQuest("prototypeCrystalCutter", prototypeCrystalCutter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeCrystalCutter,1)
                        )),
                listOf(prototypeCore,diamondGear),prototypeCore,
                -128,
                64);

        QuestTemplate energyConduit = simpleQuest("energyConduit", prototypeConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeConduit,4)
                        )),
                listOf(prototypeCore),prototypeCore,
                64*4,
                64);

        QuestTemplate fluidConduit = simpleQuest("fluidConduit", prototypeFluidConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeFluidConduit,4)
                        )),
                listOf(prototypeCore),prototypeCore,
                64*5,
                64);

        QuestTemplate itemConduit = simpleQuest("itemConduit", prototypeItemConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeItemConduit,4)
                        )),
                listOf(prototypeCore),prototypeCore,
                64*6,
                64);

        QuestTemplate energyCell = simpleQuest("energyCell", prototypeEnergyCell,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeEnergyCell,1)
                        )),
                listOf(energyConduit),energyConduit,
                0,
                64);

        QuestTemplate fluidTank = simpleQuest("fluidTank", prototypeFluidTank,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeFluidTank,1)
                        )),
                listOf(fluidConduit),fluidConduit,
                0,
                64);

        QuestTemplate pump = simpleQuest("pump", prototypePump,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypePump,1)
                        )),
                listOf(fluidConduit),fluidConduit,
                0,
                128);

        QuestTemplate inserter = simpleQuest("inserter", prototypeInserter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeInserter,1)
                        )),
                listOf(itemConduit),itemConduit,
                0,
                64);

        QuestTemplate storageContainer = simpleQuest("storageContainer", prototypeStorageContainer,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(prototypeStorageContainer,1)
                        )),
                listOf(prototypeCore),itemConduit,
                64,
                64);

        QuestTemplate crystal = simpleQuest("crystal", signalumCrystal,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(signalumCrystal,1)
                        )),
                listOf(protoCutter),energy,
                -16,
                64).setWidth(64).setHeight(64).setIconSize(2);

        QuestTemplate crystalBattery = simpleQuest("crystalBattery", signalumCrystalBattery,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(signalumCrystalBattery,1)
                        )),
                listOf(crystal),crystal,
                80,
                16);

        return listOf(
                welcome,genesis,tome,hammer,cobblePlates,stonePlates,
                tablet,ioConfig,prototypeCore,protoPlate,diamondGear,protoCutter,
                protoCrusher,protoExtract,protoSmelter,energy,energyConduit,
                fluidConduit,itemConduit,energyCell,fluidTank,pump,inserter,
                storageContainer,crystal,crystalBattery
        );
    }

    public List<QuestTemplate> addBasicQuests(){
        QuestTemplate emptyCrystal = new QuestTemplate("signalindustries:emptyCrystal","quest.signalindustries.emptyCrystal", signalumCrystalEmpty, Logic.AND, Logic.AND)
                .setTasks(listOf(new RetrievalTaskTemplate("signalindustries:emptyCrystal/retrieval",signalumCrystalEmpty.getDefaultStack())))
                .setPreRequisites(listOf(getQuest("prototypeCrystalCutter")));
        QuestTemplate steel = new QuestTemplate("signalindustries:steel","quest.signalindustries.steel", Item.ingotSteel, Logic.AND, Logic.AND)
                .setTasks(listOf(new RetrievalTaskTemplate("signalindustries:steel/retrieval", Item.ingotSteel.getDefaultStack())))
                .setX(64);
        QuestTemplate crystalDust = simpleQuest("crystalDust", emptySignalumCrystalDust,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(emptySignalumCrystalDust,1)
                        )),
                listOf(emptyCrystal),emptyCrystal,
                0,
                64);
        QuestTemplate crystalAlloy = simpleQuest("crystalAlloy", crystalAlloyIngot,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(crystalAlloyIngot,1)
                        )),
                listOf(crystalDust,steel),crystalDust,
                0,
                64);
        QuestTemplate crystalAlloyPlates = simpleQuest("crystalAlloyPlates", crystalAlloyPlate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(crystalAlloyPlate,1)
                        )),
                listOf(crystalAlloy),crystalAlloy,
                0,
                64);
        QuestTemplate meteorCompass = simpleQuest("meteorCompass", meteorTracker,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(meteorTracker,1)
                        )),
                listOf(crystalAlloy,getQuest("crystal")),crystalAlloy,
                -64,
                0);
        QuestTemplate steelPlates = simpleQuest("steelPlates", steelPlate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(steelPlate,1)
                        )),
                listOf(steel),crystalAlloyPlates,
                64,
                0);
        QuestTemplate basicCore = simpleQuest("basicCore", basicMachineCore,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicMachineCore,1)
                        )),
                listOf(crystalAlloyPlates,steelPlates,getQuest("crystal")),crystalAlloyPlates,
                -16,
                64).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate basicExtractor = simpleQuest("basicExtractor", SIBlocks.basicExtractor,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicExtractor,1)
                        )),
                listOf(basicCore),basicCore,
                80,
                16);
        QuestTemplate basicCrusher = simpleQuest("basicCrusher", SIBlocks.basicCrusher,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicCrusher,1)
                        )),
                listOf(basicCore),basicCore,
                16,
                80+32);
        QuestTemplate netherCoalDust = simpleQuest("netherCoalDust", SIItems.netherCoalDust,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.netherCoalDust,1)
                        )),
                listOf(basicCrusher),basicCrusher,
                0,
                64);
        QuestTemplate cheaperSteel = simpleQuest("cheaperSteel", tinyNetherCoalDust,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(tinyNetherCoalDust,1)
                        )),
                listOf(basicCrusher,netherCoalDust),netherCoalDust,
                0,
                80);
        QuestTemplate basicSmelter = simpleQuest("basicSmelter", basicAlloySmelter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicAlloySmelter,1)
                        )),
                listOf(basicCore),basicCrusher,
                -64,
                0);
        QuestTemplate basicCutter = simpleQuest("basicCutter", basicCrystalCutter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicCrystalCutter,1)
                        )),
                listOf(basicCore),basicCrusher,
                64,
                0);
        QuestTemplate basicInfuser = simpleQuest("basicInfuser", SIBlocks.basicInfuser,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicInfuser,1)
                        )),
                listOf(basicCore),basicCutter,
                64,
                0);
        QuestTemplate basicPress = simpleQuest("basicPress", basicPlateFormer,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicPlateFormer,1)
                        )),
                listOf(basicCore),basicInfuser,
                64,
                0);
        QuestTemplate saturatedAlloy = simpleQuest("saturatedAlloy", saturatedSignalumAlloyIngot,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(saturatedSignalumAlloyIngot,1)
                        )),
                listOf(basicInfuser),basicInfuser,
                0,
                64);
        QuestTemplate saturatedAlloyPlate = simpleQuest("saturatedAlloyPlate", saturatedSignalumAlloyPlate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(saturatedSignalumAlloyPlate,1)
                        )),
                listOf(saturatedAlloy,basicPress),basicPress,
                0,
                64);
        QuestTemplate signalumAlloyMesh = simpleQuest("signalumAlloyMesh", SIItems.signalumAlloyMesh,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.signalumAlloyMesh,1)
                        )),
                listOf(saturatedAlloyPlate,basicCutter),saturatedAlloyPlate,
                0,
                64);
        QuestTemplate basicCollector = simpleQuest("basicCollector", SIBlocks.basicCollector,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicCollector,1)
                        )),
                listOf(basicCore,signalumAlloyMesh),signalumAlloyMesh,
                0,
                64);
        QuestTemplate basicChamber = simpleQuest("basicChamber", basicCrystalChamber,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicCrystalChamber,1)
                        )),
                listOf(basicCore),basicSmelter,
                -64,
                0);
        QuestTemplate basicChip = simpleQuest("basicChip", crystalChip,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(crystalChip,1)
                        )),
                listOf(basicCutter),basicCutter,
                0,
                64);
        QuestTemplate basicEnergyCore = simpleQuest("basicEnergyCore", SIItems.basicEnergyCore,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.basicEnergyCore,1)
                        )),
                listOf(basicChip, crystalAlloyPlates),basicChip,
                -16,
                64).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate basicAutoMiner = simpleQuest("basicAutoMiner", basicAutomaticMiner,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicAutomaticMiner,1)
                        )),
                listOf(basicCore,basicEnergyCore),basicEnergyCore,
                16,
                80+48);
        QuestTemplate basicAssembler = simpleQuest("basicAssembler", SIBlocks.basicAssembler,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicAssembler,1)
                        )),
                listOf(basicCore,basicEnergyCore),basicAutoMiner,
                -64,
                0);
        QuestTemplate basicInjector = simpleQuest("basicInjector", basicEnergyInjector,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicEnergyInjector,1)
                        )),
                listOf(basicCore,basicEnergyCore),basicAssembler,
                -64,
                0);
        QuestTemplate basicDynamo = simpleQuest("basicDynamo", basicSignalumDynamo,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicSignalumDynamo,1)
                        )),
                listOf(basicCore,basicEnergyCore),basicAutoMiner,
                64,
                0);
        QuestTemplate basicCatalystConduit = simpleQuest("basicCatalystConduit", SIBlocks.basicCatalystConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicCatalystConduit,1)
                        )),
                listOf(basicDynamo),basicDynamo,
                0,
                64);
        QuestTemplate precisionChip = simpleQuest("precisionChip", precisionControlChip,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(precisionControlChip,1)
                        )),
                listOf(basicAutoMiner),basicAutoMiner,
                0,
                64);
        QuestTemplate basicEnergyCell = simpleQuest("basicEnergyCell", SIBlocks.basicEnergyCell,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicEnergyCell,1)
                        )),
                listOf(basicCore),basicCore,
                -80-128,
                16);
        QuestTemplate basicEnergyConduit = simpleQuest("basicEnergyConduit", basicConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicConduit,1)
                        )),
                listOf(basicEnergyCell),basicEnergyCell,
                -64,
                0);
        QuestTemplate basicFluidTank = simpleQuest("basicFluidTank", SIBlocks.basicFluidTank,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicFluidTank,1)
                        )),
                listOf(basicCore),basicEnergyCell,
                0,
                -64);
        QuestTemplate basicFluidConduit = simpleQuest("basicFluidConduit", SIBlocks.basicFluidConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicFluidConduit,1)
                        )),
                listOf(basicFluidTank),basicFluidTank,
                -64,
                0);
        QuestTemplate basicInserter = simpleQuest("basicInserter", SIBlocks.basicInserter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicInserter,1)
                        )),
                listOf(basicCore),basicFluidTank,
                0,
                -64);
        QuestTemplate basicItemConduit = simpleQuest("basicItemConduit", SIBlocks.basicItemConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicItemConduit,1)
                        )),
                listOf(basicInserter),basicInserter,
                -64,
                0);
        QuestTemplate basicRestrictItemConduit = simpleQuest("basicRestrictItemConduit", SIBlocks.basicRestrictItemConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicRestrictItemConduit,1)
                        )),
                listOf(basicInserter),basicItemConduit,
                0,
                -64);
        QuestTemplate basicSensorItemConduit = simpleQuest("basicSensorItemConduit", SIBlocks.basicSensorItemConduit,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicSensorItemConduit,1)
                        )),
                listOf(basicInserter),basicItemConduit,
                0,
                -128);
        QuestTemplate basicContainer = simpleQuest("basicContainer", basicStorageContainer,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicStorageContainer,1)
                        )),
                listOf(basicCore),basicItemConduit,
                -64,
                0);
        QuestTemplate covers = simpleQuest("covers", blankCover,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(blankCover,1)
                        )),
                listOf(crystalAlloyPlates,steelPlates),steelPlates,
                64*2,
                0);
        QuestTemplate conveyorCover = simpleQuest("conveyorCover", SIItems.conveyorCover,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.conveyorCover,1)
                        )),
                listOf(covers),covers,
                64,
                0);
        QuestTemplate pumpCover = simpleQuest("pumpCover", SIItems.pumpCover,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.pumpCover,1)
                        )),
                listOf(covers),covers,
                64,
                64);
        QuestTemplate voidCover = simpleQuest("voidCover", SIItems.voidCover,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.voidCover,1)
                        )),
                listOf(covers),covers,
                64,
                -64);
        QuestTemplate redstoneCover = simpleQuest("redstoneCover", SIItems.redstoneCover,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.redstoneCover,1)
                        )),
                listOf(covers),covers,
                64,
                -128);
        QuestTemplate switchCover = simpleQuest("switchCover", SIItems.switchCover,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.switchCover,1)
                        )),
                listOf(covers),covers,
                64,
                128);
        QuestTemplate glowingObsidian = simpleQuest("glowingObsidian", SIBlocks.glowingObsidian,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.glowingObsidian,1)
                        )),
                listOf(basicInfuser),cheaperSteel,
                -256,
                -16).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate basicDrill = simpleQuest("basicDrill", basicSignalumDrill,
                zip(listOf("retrieval","retrieval2","retrieval3"),
                        listOf(
                                new ItemStack(basicDrillCasing,1),
                                new ItemStack(basicDrillBit,1),
                                new ItemStack(basicSignalumDrill,1)
                        )),
                listOf(steelPlates,crystalAlloyPlates,saturatedAlloyPlate),saturatedAlloyPlate,
                64,
                0);
        QuestTemplate signaliteAlloyCoil = simpleQuest("signaliteAlloyCoil", signalumAlloyCoil,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(signalumAlloyCoil,1)
                        )),
                listOf(saturatedAlloyPlate,crystalAlloy),saturatedAlloyPlate,
                128+64,
                128);
        QuestTemplate basicInductionSmelter = simpleQuest("basicInductionSmelter", SIBlocks.basicInductionSmelter,
                zip(listOf("retrieval","retrieval2","retrieval3","retrieval4","retrieval5","retrieval6"),
                        listOf(
                                new ItemStack(SIBlocks.basicInductionSmelter,1),
                                new ItemStack(basicCasing,14),
                                new ItemStack(signalumAlloyCoil,8),
                                new ItemStack(basicItemInputBus,1),
                                new ItemStack(basicItemOutputBus,1),
                                new ItemStack(basicEnergyConnector,1)
                        )),
                listOf(signaliteAlloyCoil),signaliteAlloyCoil,
                -16,
                64).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate basicEnergyConnector = simpleQuest("basicEnergyConnector", SIBlocks.basicEnergyConnector,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicEnergyConnector,1)
                        )),
                listOf(basicCore,basicEnergyCore),basicInductionSmelter,
                16,
                80 + 16);
        QuestTemplate basicItemInput = simpleQuest("basicItemInput", basicItemInputBus,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicItemInputBus,1)
                        )),
                listOf(basicCore),basicInductionSmelter,
                16 - 64,
                16);
        QuestTemplate basicItemOutput = simpleQuest("basicItemOutput", SIBlocks.basicItemOutputBus,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(basicItemOutputBus,1)
                        )),
                listOf(basicCore),basicInductionSmelter,
                16 + 64,
                16);
        QuestTemplate basicCasing = simpleQuest("basicCasing", SIBlocks.basicCasing,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.basicCasing,1)
                        )),
                listOf(crystalAlloyPlates),basicEnergyConnector,
                64,
                0);
        QuestTemplate redstoneBooster = simpleQuest("redstoneBooster", SIBlocks.redstoneBooster,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.redstoneBooster,1)
                        )),
                listOf(basicCore,basicEnergyCore),basicEnergyCore,
                -80-32,
                16);
        return listOf(
                emptyCrystal, steel, crystalDust, crystalAlloy, crystalAlloyPlates, meteorCompass,
                steelPlates, basicCore, basicExtractor, basicCollector, basicCrusher, netherCoalDust,
                cheaperSteel, basicSmelter, basicInfuser, basicCutter, basicPress, saturatedAlloy,
                saturatedAlloyPlate, signalumAlloyMesh, basicChamber, basicAutoMiner, precisionChip,
                basicChip, basicEnergyCore, basicEnergyCell, basicFluidTank, basicInserter,
                basicItemConduit, basicRestrictItemConduit, basicSensorItemConduit, basicContainer,
                basicEnergyConduit, basicFluidConduit, basicAssembler, basicDynamo, basicCatalystConduit,
                basicInjector, covers, conveyorCover, pumpCover, redstoneCover, switchCover, voidCover,
                glowingObsidian, basicDrill, signaliteAlloyCoil, basicInductionSmelter, basicEnergyConnector,
                basicItemInput, basicItemOutput, basicCasing, redstoneBooster
        );
    }

    public List<QuestTemplate> addReinforcedQuests(){
        QuestTemplate reinforcedAlloy = new QuestTemplate("signalindustries:reinforcedAlloy","quest.signalindustries.reinforcedAlloy", reinforcedCrystalAlloyIngot, Logic.AND, Logic.AND)
                .setTasks(listOf(new RetrievalTaskTemplate("signalindustries:reinforcedAlloy/retrieval",reinforcedCrystalAlloyIngot.getDefaultStack())))
                .setPreRequisites(listOf(getQuest("basicSmelter"),getQuest("glowingObsidian")));
        QuestTemplate reinforcedAlloyPlates = new QuestTemplate("signalindustries:reinforcedAlloyPlates","quest.signalindustries.reinforcedAlloyPlates", reinforcedCrystalAlloyPlate, Logic.AND, Logic.AND)
                .setTasks(listOf(new RetrievalTaskTemplate("signalindustries:reinforcedAlloyPlates/retrieval",reinforcedCrystalAlloyPlate.getDefaultStack())))
                .setPreRequisites(listOf(reinforcedAlloy,getQuest("basicPress")))
                .setY(reinforcedAlloy,64);
        QuestTemplate signaliteGear = new QuestTemplate("signalindustries:signaliteGear","quest.signalindustries.signaliteGear", signalumCuttingGear, Logic.AND, Logic.AND)
                .setTasks(listOf(new RetrievalTaskTemplate("signalindustries:signaliteGear/retrieval",signalumCuttingGear.getDefaultStack())))
                .setPreRequisites(listOf(getQuest("crystal")))
                .setY(reinforcedAlloyPlates,128)
                .setX(reinforcedAlloyPlates,-64);
        QuestTemplate reinforcedCore = new QuestTemplate("signalindustries:reinforcedCore","quest.signalindustries.reinforcedCore", reinforcedMachineCore, Logic.AND, Logic.AND)
                .setTasks(listOf(new RetrievalTaskTemplate("signalindustries:reinforcedCore/retrieval",reinforcedMachineCore.getDefaultStack())))
                .setPreRequisites(listOf(reinforcedAlloyPlates,getQuest("saturatedAlloy"),getQuest("basicCore")))
                .setY(reinforcedAlloyPlates,48)
                .setX(reinforcedAlloyPlates,-16)
                .setWidth(64).setHeight(64).setIconSize(2);;
        QuestTemplate reinforcedCutter = simpleQuest("reinforcedCutter", reinforcedCrystalCutter,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedCrystalCutter,1)
                        )),
                listOf(reinforcedCore,signaliteGear),reinforcedCore,
                16,
                80+64);
        QuestTemplate pureChip = simpleQuest("pureChip", pureCrystalChip,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(pureCrystalChip,1)
                        )),
                listOf(reinforcedCutter,getQuest("crystal")),reinforcedCutter,
                64,
                0);
        QuestTemplate reinforcedEnergyCore = simpleQuest("reinforcedEnergyCore", SIItems.reinforcedEnergyCore,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.reinforcedEnergyCore,1)
                        )),
                listOf(pureChip),pureChip,
                80,
                -16).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate reinforcedDrill = simpleQuest("reinforcedDrill", reinforcedSignalumDrill,
                zip(listOf("retrieval","retrieval2","retrieval3"),
                        listOf(
                                new ItemStack(reinforcedSignalumDrill,1),
                                new ItemStack(reinforcedDrillBit,1),
                                new ItemStack(reinforcedDrillCasing,1)
                        )),
                listOf(reinforcedEnergyCore),reinforcedEnergyCore,
                16,
                -48);
        QuestTemplate dilithiumOre = simpleQuest("dilithiumOre", SIBlocks.dilithiumOre,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(dilithiumShard,1)
                        )),
                listOf(reinforcedDrill),reinforcedDrill,
                80,
                0);
        QuestTemplate reinforcedTracker = simpleQuest("reinforcedTracker", reinforcedMeteorTracker,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedMeteorTracker,1)
                        )),
                listOf(dilithiumOre),dilithiumOre,
                0,
                -64);
        QuestTemplate dilithiumPlate = simpleQuest("dilithiumPlate", SIItems.dilithiumPlate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.dilithiumPlate,1)
                        )),
                listOf(dilithiumOre),dilithiumOre,
                64,
                0);
        QuestTemplate blueprint = simpleQuest("blueprint", SIItems.blueprint,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.blueprint,1)
                        )),
                listOf(),reinforcedEnergyCore,
                16-64,
                80+64);
        QuestTemplate builder = simpleQuest("builder", reinforcedBuilder,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedBuilder,1)
                        )),
                listOf(reinforcedEnergyCore,blueprint),reinforcedEnergyCore,
                16,
                80+64);
        QuestTemplate dimensionalOre = simpleQuest("dimensionalOre", dimensionalShardOre,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(dimensionalShard,1)
                        )),
                listOf(reinforcedDrill),reinforcedDrill,
                80,
                -128);
        QuestTemplate warpOrb = simpleQuest("warpOrb", SIItems.warpOrb,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.warpOrb,1)
                        )),
                listOf(dimensionalOre),dimensionalOre,
                64,
                0);
        QuestTemplate reinforcedEnergyConnector = simpleQuest("reinforcedEnergyConnector", SIBlocks.reinforcedEnergyConnector,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.reinforcedEnergyConnector,1)
                        )),
                listOf(reinforcedEnergyCore,reinforcedCore),reinforcedCore,
                -128+16,
                16);
        QuestTemplate reinforcedItemInput = simpleQuest("reinforcedItemInput", reinforcedItemInputBus,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedItemInputBus,1)
                        )),
                listOf(reinforcedEnergyCore,reinforcedCore),reinforcedEnergyConnector,
                -64,
                0);
        QuestTemplate reinforcedItemOutput = simpleQuest("reinforcedItemOutput", reinforcedItemOutputBus,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedItemOutputBus,1)
                        )),
                listOf(reinforcedEnergyCore,reinforcedCore),reinforcedEnergyConnector,
                -128,
                0);
        QuestTemplate reinforcedFluidInput = simpleQuest("reinforcedFluidInput", reinforcedFluidInputHatch,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedFluidInputHatch,1)
                        )),
                listOf(reinforcedEnergyCore,reinforcedCore),reinforcedEnergyConnector,
                -64,
                64);
        QuestTemplate reinforcedFluidOutput = simpleQuest("reinforcedFluidOutput", reinforcedFluidOutputHatch,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(reinforcedFluidOutputHatch,1)
                        )),
                listOf(reinforcedEnergyCore,reinforcedCore),reinforcedEnergyConnector,
                -128,
                64);
        QuestTemplate reinforcedCasing = simpleQuest("reinforcedCasing", SIBlocks.reinforcedCasing,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.reinforcedCasing,1)
                        )),
                listOf(reinforcedAlloyPlates),reinforcedAlloyPlates,
                -128,
                0);
        QuestTemplate reinforcedCasing2 = simpleQuest("reinforcedCasing2", SIBlocks.reinforcedCasing2,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.reinforcedCasing2,1)
                        )),
                listOf(reinforcedAlloyPlates),reinforcedCasing,
                0,
                -64);
        QuestTemplate reinforcedGrating = simpleQuest("reinforcedGrating", reinforcedGrate,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIBlocks.reinforcedGrate,1)
                        )),
                listOf(reinforcedAlloyPlates,getQuest("signalumAlloyMesh")),reinforcedCasing,
                -64,
                0);
        QuestTemplate booster = simpleQuest("booster", dilithiumBooster,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(dilithiumBooster,1)
                        )),
                listOf(dilithiumPlate),dilithiumPlate,
                80,
                0);
        QuestTemplate stabilizer = simpleQuest("stabilizer", dilithiumStabilizer,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(dilithiumStabilizer,1)
                        )),
                listOf(dilithiumPlate),booster,
                0,
                64);
        QuestTemplate reinforcedWrathBeacon = simpleQuest("reinforcedWrathBeacon", SIBlocks.reinforcedWrathBeacon,
                zip(listOf("retrieval","retrieval2"),
                        listOf(
                                new ItemStack(SIBlocks.reinforcedWrathBeacon,1),
                                new ItemStack(eternalTreeLog,259)
                        )),
                listOf(reinforcedCore),reinforcedCutter,
                0,
                64);
        QuestTemplate saturatedKey = simpleQuest("saturatedKey", SIItems.saturatedKey,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.saturatedKey,1)
                        )),
                listOf(reinforcedWrathBeacon),reinforcedWrathBeacon,
                0,
                64);
        QuestTemplate eclipse = simpleQuest("eclipse", infernalFragment,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(infernalFragment,1)
                        )),
                listOf(reinforcedWrathBeacon),reinforcedWrathBeacon,
                -64,
                0);
        QuestTemplate dilithiumChip = simpleQuest("dilithiumChip", SIItems.dilithiumChip,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.dilithiumChip,1)
                        )),
                listOf(reinforcedCutter),pureChip,
                0,
                -128);
        QuestTemplate dimensionalChip = simpleQuest("dimensionalChip", SIItems.dimensionalChip,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.dimensionalChip,1)
                        )),
                listOf(reinforcedCutter),pureChip,
                0,
                -128-64);
        QuestTemplate anchor = simpleQuest("anchor", dimensionalAnchor,
                zip(listOf("retrieval","retrieval2","retrieval3","retrieval4"),
                        listOf(
                                new ItemStack(dimensionalAnchor,1),
                                new ItemStack(dilithiumStabilizer,4),
                                new ItemStack(glowingObsidian,24),
                                new ItemStack(SIBlocks.reinforcedCasing,40)
                        )),
                listOf(warpOrb,stabilizer),warpOrb,
                64,
                -16).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate pulsar = simpleQuest("pulsar", SIItems.pulsar,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.pulsar,1)
                        )),
                listOf(reinforcedEnergyCore),reinforcedDrill,
                0,
                -128-64);
        QuestTemplate pulsarAttachment = simpleQuest("pulsarAttachment", SIItems.pulsarAttachment,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(SIItems.pulsarAttachment,1)
                        )),
                listOf(pulsar),pulsar,
                64,
                0);
        QuestTemplate reactor = simpleQuest("reactor", signalumReactorCore,
                zip(listOf("retrieval"),
                        listOf(
                                new ItemStack(signalumReactorCore,1)
                        )),
                listOf(reinforcedEnergyCore,saturatedKey,reinforcedItemInput,reinforcedItemOutput,reinforcedEnergyConnector,reinforcedCasing),saturatedKey,
                -16,
                64).setWidth(64).setHeight(64).setIconSize(2);
        QuestTemplate eternity = new QuestTemplate("signalindustries:eternity","quest.signalindustries.eternity", realityFabric, Logic.AND, Logic.AND)
                .setTasks(listOf(new VisitDimensionTaskTemplate("signalindustries:eternity/visit", SIDimensions.dimEternity)))
                .setPreRequisites(listOf(pulsar,warpOrb))
                .setY(pulsar,-80).setWidth(64).setHeight(64).setIconSize(2)
                .setX(pulsar,-16);
        return listOf(
                reinforcedAlloy,reinforcedAlloyPlates,reinforcedCore,signaliteGear,reinforcedCutter,
                pureChip,reinforcedEnergyCore,reinforcedDrill,dilithiumOre,reinforcedTracker,dilithiumPlate,
                builder,blueprint,dimensionalOre,warpOrb,reinforcedEnergyConnector,reinforcedCasing,
                reinforcedFluidOutput,reinforcedFluidInput,reinforcedItemOutput,reinforcedItemInput,
                reinforcedGrating,reinforcedCasing2,booster,stabilizer,reinforcedWrathBeacon,eclipse,
                dilithiumChip,dimensionalChip,anchor,pulsar,pulsarAttachment,eternity,saturatedKey,reactor
        );
    }

    public List<QuestTemplate> addAwakenedQuests(){
        return listOf();
    }

    public QuestTemplate simpleQuest(
            String id,
            IItemConvertible icon,
            List<Pair<String,ItemStack>> tasks,
            List<QuestTemplate> preRequisites,
            QuestTemplate offsetQuest,
            int xOffset,
            int yOffset
    ) {
        List<TaskTemplate> retrievalTasks = new ArrayList<>();
        for (Pair<String, ItemStack> task : tasks) {
            retrievalTasks.add(new RetrievalTaskTemplate("signalindustries:"+id+"/"+task.getLeft(),task.getRight()));
        }
        return new QuestTemplate("signalindustries:"+id,"quest.signalindustries."+id,icon,Logic.AND,Logic.AND)
                .setPreRequisites(preRequisites)
                .setTasks(retrievalTasks)
                .setX(offsetQuest,xOffset)
                .setY(offsetQuest,yOffset);
    }

    public QuestTemplate simpleQuest(
            String id,
            IItemConvertible icon,
            List<Pair<String,ItemStack>> tasks,
            List<Pair<String,ItemStack>> rewards,
            List<QuestTemplate> preRequisites,
            QuestTemplate offsetQuest,
            int xOffset,
            int yOffset
    ) {
        List<TaskTemplate> retrievalTasks = new ArrayList<>();
        for (Pair<String, ItemStack> task : tasks) {
            retrievalTasks.add(new RetrievalTaskTemplate("signalindustries:"+id+"/"+task.getLeft(),task.getRight()));
        }
        List<RewardTemplate> itemRewards = new ArrayList<>();
        for (Pair<String, ItemStack> task : tasks) {
            itemRewards.add(new ItemRewardTemplate("signalindustries:"+id+"/"+task.getLeft(),task.getRight()));
        }
        return new QuestTemplate("signalindustries:"+id,"quest.signalindustries."+id,icon,Logic.AND,Logic.AND)
                .setPreRequisites(preRequisites)
                .setTasks(retrievalTasks)
                .setRewards(itemRewards)
                .setX(offsetQuest,xOffset)
                .setY(offsetQuest,yOffset);
    }

    public QuestTemplate simpleClickQuest(
            String id,
            IItemConvertible icon,
            List<QuestTemplate> preRequisites,
            QuestTemplate offsetQuest,
            int xOffset,
            int yOffset
    ) {
        return new QuestTemplate("signalindustries:"+id,"quest.signalindustries."+id,icon,Logic.AND,Logic.AND)
                .setPreRequisites(preRequisites)
                .setTasks(listOf(new ClickTaskTemplate("signalindustries:"+id+"/click")))
                .setX(offsetQuest,xOffset)
                .setY(offsetQuest,yOffset);
    }

    public QuestTemplate getQuest(String id){
        return VintageQuesting.QUESTS.getItem("signalindustries:"+id);
    }

    public void reset() {
        Minecraft mc = Minecraft.getMinecraft(this);
        IHasQuests player = (IHasQuests) mc.thePlayer;
        World world = mc.theWorld;
        player.setCurrentChapter(null);
        player.getQuestGroup().chapters.clear();
        VintageQuesting.QUESTS = new QuestRegistry();
        VintageQuesting.TASKS = new TaskRegistry();
        VintageQuesting.REWARDS = new RewardRegistry();
        VintageQuesting.CHAPTERS = new ChapterRegistry();
        initializePlugin();
        for (ChapterTemplate chapter : VintageQuesting.CHAPTERS) {
            player.getQuestGroup().chapters.add(chapter.getInstance());
        }
        player.loadData(VintageQuesting.playerData);
    }
}
