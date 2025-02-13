package me.st3v1k.kolosmp.kolocam.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class KoloCamClient implements ClientModInitializer {

    public static KeyBinding TAKE_PICTURE_KEY;

    @Override
    public void onInitializeClient() {

        TAKE_PICTURE_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.kolocam.take_picture", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C, "KoloCam"));
    }
}
