package sunsetsatellite.signalindustries.interfaces.mixins;

import sunsetsatellite.signalindustries.mp.packets.PacketOpenMachineGUI;
import sunsetsatellite.signalindustries.mp.packets.PacketPipeItemSpawn;

public interface INetClientHandler {
    void handleOpenMachineGUI(PacketOpenMachineGUI p);

    void handlePipeItemSpawn(PacketPipeItemSpawn packetPipeItemSpawn);

}
