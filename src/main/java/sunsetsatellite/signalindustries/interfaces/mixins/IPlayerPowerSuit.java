package sunsetsatellite.signalindustries.interfaces.mixins;

import com.mojang.nbt.CompoundTag;
import sunsetsatellite.signalindustries.powersuit.SignalumPowerSuit;

public interface IPlayerPowerSuit {
    SignalumPowerSuit getPowerSuit();

    CompoundTag getPowerSuitData();
}
