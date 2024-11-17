package sunsetsatellite.signalindustries;

import sunsetsatellite.catalyst.core.util.DataInitializer;
import sunsetsatellite.catalyst.multiblocks.Multiblock;
import sunsetsatellite.catalyst.multiblocks.Structure;
import sunsetsatellite.signalindustries.util.SIMultiblock;
import sunsetsatellite.signalindustries.util.Tier;

import static sunsetsatellite.signalindustries.SignalIndustries.LOGGER;
import static sunsetsatellite.signalindustries.SignalIndustries.MOD_ID;

public class SIMultiblocks extends DataInitializer {
    public static SIMultiblock dimAnchorMultiblock;
    public static SIMultiblock wrathTree;
    public static SIMultiblock signalumReactor;
    public static SIMultiblock extractionManifold;
    public static SIMultiblock inductionSmelterBasic;
    public static SIMultiblock warpGate;
    public static SIMultiblock wakingCrusher;
    public static SIMultiblock wakingAlloySmelter;
    public static SIMultiblock wakingPlateFormer;
    public static SIMultiblock wakingInfuser;

    public void init(){
        if(initialized) return;
        LOGGER.info("Initializing multiblocks...");
        dimAnchorMultiblock = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "dimensionalAnchor", "dimensionalAnchor", false, Tier.REINFORCED);
        wrathTree = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "wrathTree", "reinforcedWrathBeacon", false, Tier.REINFORCED);
        signalumReactor = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "signalumReactor", "signalumReactor", false, Tier.REINFORCED);
        extractionManifold = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "extractionManifold", "reinforcedExtractor", false, Tier.REINFORCED);
        inductionSmelterBasic = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "basicInductionSmelter", "basicInductionSmelter", false, Tier.BASIC);
        warpGate = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "warpGate", "warpGate", false, Tier.AWAKENED);
        wakingPlateFormer = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "wakingPlateFormer", "wakingPlateFormer", false, Tier.REINFORCED);
        wakingAlloySmelter = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "wakingAlloySmelter", "wakingAlloySmelter", false, Tier.REINFORCED);
        wakingCrusher = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "wakingCrusher", "wakingCrusher", false, Tier.REINFORCED);
        wakingInfuser = new SIMultiblock(MOD_ID, new Class[]{SIBlocks.class}, "wakingInfuser", "wakingInfuser", false, Tier.REINFORCED);

        Multiblock.multiblocks.put("dimensionalAnchor", dimAnchorMultiblock);
        Multiblock.multiblocks.put("wrathTree", wrathTree);
        Multiblock.multiblocks.put("signalumReactor", signalumReactor);
        Multiblock.multiblocks.put("extractionManifold", extractionManifold);
        Multiblock.multiblocks.put("basicInductionSmelter", inductionSmelterBasic);
        Multiblock.multiblocks.put("warpGate", warpGate);
        Multiblock.multiblocks.put("wakingPlateFormer", wakingPlateFormer);
        Multiblock.multiblocks.put("wakingAlloySmelter", wakingAlloySmelter);
        Multiblock.multiblocks.put("wakingCrusher", wakingCrusher);
        Multiblock.multiblocks.put("wakingInfuser", wakingInfuser);

        LOGGER.info(String.format("Loaded %d multiblocks..",Multiblock.multiblocks.size()));
        LOGGER.info(String.format("Loaded %d internal structures.", Structure.internalStructures.size()));
        setInitialized(true);
    }

}