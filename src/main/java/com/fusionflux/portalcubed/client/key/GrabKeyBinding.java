package com.fusionflux.portalcubed.client.key;

import com.fusionflux.portalcubed.PortalCubed;
import com.fusionflux.portalcubed.packet.PortalCubedServerPackets;
import com.fusionflux.portalcubed.util.PortalCubedComponents;
import com.mojang.blaze3d.platform.InputUtil;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBind;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

@ClientOnly
public class GrabKeyBinding {
    public static void register() {
        KeyBind key = new KeyBind(
                "key." + PortalCubed.MOD_ID + ".grab",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "key." + PortalCubed.MOD_ID + ".category"
        );

        KeyBindingHelper.registerKeyBinding(key);
        ClientTickEvents.END.register(client -> {
            if (client.player != null && key.wasPressed()) {
                PortalCubedComponents.HOLDER_COMPONENT.get(client.player).stopHolding();
                ClientPlayNetworking.send(PortalCubedServerPackets.GRAB_KEY_PRESSED, PacketByteBufs.create());
            }
        });
    }
}
