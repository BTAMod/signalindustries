package sunsetsatellite.signalindustries.abilities.trigger;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.core.util.Vec3i;
import sunsetsatellite.signalindustries.SIBlocks;

import java.util.*;

public class ScanAbility extends TriggerBaseEffectAbility {

    public static HashMap<Block,OreInfo> oreMap = new HashMap<>();

    public ScanAbility(String name, int cost, int cooldown, int effectTime, int costPerTick) {
        super(name, cost, cooldown, effectTime, costPerTick);
    }

    @Override
    public void deactivate(int x, int y, int z, EntityPlayer player, World world, ItemStack trigger, ItemStack harness) {
        deactivate(player, world, trigger, harness);
    }

    @Override
    public void deactivate(EntityPlayer player, World world, ItemStack trigger, ItemStack harness) {
        oreMap.clear();
    }

    @Override
    public void tick(EntityPlayer player, World world, ItemStack trigger, ItemStack harness) {

    }

    @Override
    public void activate(int x, int y, int z, EntityPlayer player, World world, ItemStack trigger, ItemStack harness) {
        activate(player, world, trigger, harness);
    }

    @Override
    public void activate(EntityPlayer player, World world, ItemStack trigger, ItemStack harness) {
        int range = 16;

        oreMap.clear();

        oreMap.put(Block.oreCoalStone,new OreInfo());
        oreMap.put(Block.oreIronStone,new OreInfo());
        oreMap.put(Block.oreGoldStone,new OreInfo());
        oreMap.put(Block.oreLapisStone,new OreInfo());
        oreMap.put(Block.oreRedstoneStone,new OreInfo());

        oreMap.put(Block.oreCoalBasalt,new OreInfo());
        oreMap.put(Block.oreIronBasalt,new OreInfo());
        oreMap.put(Block.oreGoldBasalt,new OreInfo());
        oreMap.put(Block.oreLapisBasalt,new OreInfo());
        oreMap.put(Block.oreRedstoneBasalt,new OreInfo());

        oreMap.put(Block.oreCoalLimestone,new OreInfo());
        oreMap.put(Block.oreIronLimestone,new OreInfo());
        oreMap.put(Block.oreGoldLimestone,new OreInfo());
        oreMap.put(Block.oreLapisLimestone,new OreInfo());
        oreMap.put(Block.oreRedstoneLimestone,new OreInfo());

        oreMap.put(Block.oreCoalGranite,new OreInfo());
        oreMap.put(Block.oreIronGranite,new OreInfo());
        oreMap.put(Block.oreGoldGranite,new OreInfo());
        oreMap.put(Block.oreLapisGranite,new OreInfo());
        oreMap.put(Block.oreRedstoneGranite,new OreInfo());

        oreMap.put(SIBlocks.signalumOre,new OreInfo());
        oreMap.put(SIBlocks.dilithiumOre,new OreInfo());

        for (int i = -range; i < range; i++) {
            for (int j = -range; j < range; j++) {
                for (int k = 0; k < world.getHeightValue((int) (player.x + i), (int) (player.z + j)); k++) {
                    int blockId = world.getBlockId((int) (player.x + i), k, (int) (player.z + j));
                    int finalK = k;
                    int finalI = (int) (player.x + i);
                    int finalJ = (int) (player.z + j);
                    oreMap.forEach((block, oreInfo) -> {
                        if(blockId == block.id){
                            oreInfo.count++;
                            oreInfo.positions.add(new Vec3i(finalI,finalK, finalJ));
                        }
                    });
                }
            }
        }

        player.sendMessage("--- SCAN RESULTS ---");
        oreMap.forEach((block, oreInfo) -> {
            if(oreInfo.count > 0){
                player.sendMessage(String.format("%s%s | Count: %s%d",block.asItem().getTranslatedName(block.getDefaultStack()), TextFormatting.LIGHT_GRAY, TextFormatting.WHITE, oreInfo.count));
            }
        });
        player.sendMessage("--------------------");
    }

    public static class OreInfo {
        public int count = 0;
        public ArrayList<Vec3i> positions = new ArrayList<>();

        public OreInfo() {}
    }
}
