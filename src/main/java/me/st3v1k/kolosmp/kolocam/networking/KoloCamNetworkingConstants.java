package me.st3v1k.kolosmp.kolocam.networking;

import me.st3v1k.kolosmp.kolocam.KoloCam;
import net.minecraft.util.Identifier;

public class KoloCamNetworkingConstants {

    public static Identifier CREATE_MAP_STATE = Identifier.of(KoloCam.MOD_ID, "create_map_state");

    public static Identifier CREATE_PICTURE = Identifier.of(KoloCam.MOD_ID, "create_picture");

    public static Identifier SPAWN_PICTURE = Identifier.of(KoloCam.MOD_ID, "spawn_picture");

    public static Identifier SEND_IMAGE_BYTES = Identifier.of(KoloCam.MOD_ID, "send_bytes");

    public static Identifier SEND_SCREENSHOT_IMAGE = Identifier.of(KoloCam.MOD_ID, "send_screenshot_image");

}
