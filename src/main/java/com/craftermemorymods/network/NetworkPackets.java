package com.craftermemorymods.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import com.craftermemorymods.CrafterMemory;

public class NetworkPackets {
    public static final Identifier SAVE_RECIPE_PACKET = Identifier.of(CrafterMemory.MOD_ID, "save_recipe");
    public static final Identifier FORGET_RECIPE_PACKET = Identifier.of(CrafterMemory.MOD_ID, "forget_recipe");

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(SAVE_RECIPE_PACKET, (server, player, handler, buf, responseSender) -> {
            // Handle save recipe packet
        });

        ServerPlayNetworking.registerGlobalReceiver(FORGET_RECIPE_PACKET, (server, player, handler, buf, responseSender) -> {
            // Handle forget recipe packet
        });
    }
}
