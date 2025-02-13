package me.st3v1k.kolosmp.kolocam.items;

import me.st3v1k.kolosmp.image2map.Image2Map;
import me.st3v1k.kolosmp.image2map.renderer.MapRenderer;
import me.st3v1k.kolosmp.kolocam.networking.CreateMapStatePayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class CameraItem extends Item {

    public CameraItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            takePicture(world);
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS_SERVER;
    }

    @Environment(EnvType.CLIENT)
    private void takePicture(World world) {
        MinecraftClient client = MinecraftClient.getInstance();

        try (NativeImage nativeImage = ScreenshotRecorder.takeScreenshot(client.getFramebuffer())) {
            BufferedImage bufferedImage = new BufferedImage(nativeImage.getWidth(), nativeImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            try {
                for (int x = 0; x < nativeImage.getWidth(); x++) {
                    for (int y = 0; y < nativeImage.getHeight(); y++) {
                        bufferedImage.setRGB(x, y, nativeImage.getColorArgb(x, y));
                    }
                }

                bufferedImage = crop(bufferedImage, bufferedImage.getHeight(), bufferedImage.getHeight());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            byte scale = 0;
            MapState mapState = MapRenderer.render(bufferedImage, Image2Map.DitherMode.FLOYD, MapState.of(scale, true, world.getRegistryKey()));

            NbtCompound nbtCompound = new NbtCompound();
            DynamicRegistryManager manager = world.getRegistryManager();
            mapState.writeNbt(nbtCompound, manager);
            CreateMapStatePayload createMapStatePayload = new CreateMapStatePayload(nbtCompound);
            ClientPlayNetworking.send(createMapStatePayload);
        }
    }

    private BufferedImage crop(BufferedImage bufferedImage, int targetWidth, int targetHeight) throws IOException {
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();

        // Coordinates of the image's middle
        int xc = (width - targetWidth) / 2;
        int yc = (height - targetHeight) / 2;

        return bufferedImage.getSubimage(
                xc,
                yc,
                targetWidth,
                targetHeight
        );
    }
}
