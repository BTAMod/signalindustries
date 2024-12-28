package sunsetsatellite.signalindustries.mixin;


import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeatureLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunsetsatellite.signalindustries.SIItems;

import java.util.Random;

@Mixin(
        value = WorldFeatureLabyrinth.class,
        remap = false
)
public class WorldGenLabyrinthMixin {

    @Shadow public WeightedRandomBag<WeightedRandomLootObject> chestLoot;

    @Inject(method = "generate",at = @At(value = "INVOKE",target = "Lnet/minecraft/core/WeightedRandomBag;addEntry(Ljava/lang/Object;D)V",ordinal = 0,shift = At.Shift.AFTER))
    private void init(World world, Random random, int x, int y, int z, CallbackInfoReturnable<Boolean> cir){
        this.chestLoot.addEntry(new WeightedRandomLootObject(SIItems.romChipBoost.getDefaultStack()), 30);
        this.chestLoot.addEntry(new WeightedRandomLootObject(SIItems.romChipProjectile.getDefaultStack()), 30);
        this.chestLoot.addEntry(new WeightedRandomLootObject(SIItems.romChipShield.getDefaultStack()), 30);
        this.chestLoot.addEntry(new WeightedRandomLootObject(SIItems.romChipScan.getDefaultStack()), 30);
    }

}
