package me.st3v1k.kolosmp.kolocam;

import me.st3v1k.kolosmp.kolocam.items.CameraItem;
import me.st3v1k.kolosmp.kolocam.networking.CreateMapStatePayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class KoloCam implements ModInitializer {

    public static final String MOD_ID = "kolocam";

    private static final RegistryKey<Item> CAMERA_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "camera"));
    public static final CameraItem CAMERA_ITEM = new CameraItem(new Item.Settings().registryKey(CAMERA_KEY).maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, CAMERA_KEY.getValue(), CAMERA_ITEM);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) ->
                itemGroup.add(CAMERA_ITEM)
        );

        PayloadTypeRegistry.playC2S().register(CreateMapStatePayload.PACKET_ID, CreateMapStatePayload.PACKET_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(CreateMapStatePayload.PACKET_ID, (payload, context) -> {
            var player = context.player();
            var world = player.getServerWorld();
            NbtCompound nbtCompound = payload.imageNBT();
            MapState mapState = MapState.fromNbt(nbtCompound, world.getRegistryManager());

            ItemStack stack = new ItemStack(Items.FILLED_MAP);
            MapIdComponent mapIdComponent = world.increaseAndGetMapId();
            player.getEntityWorld().putMapState(mapIdComponent, mapState);
            stack.set(DataComponentTypes.MAP_ID, mapIdComponent);

            if (!player.isCreative()) {
                int slot = player.getInventory().getSlotWithStack((new ItemStack(Items.MAP)));
                if (slot != -1) {
                    player.getInventory().getStack(slot).decrement(1);
                    ItemEntity itemEntity = new ItemEntity(player.getServerWorld(), player.getPos().x, player.getPos().y, player.getPos().z, stack);
                    player.getServerWorld().spawnEntity(itemEntity);
                }
            } else {
                ItemEntity itemEntity = new ItemEntity(player.getServerWorld(), player.getPos().x, player.getPos().y, player.getPos().z, stack);
                player.getServerWorld().spawnEntity(itemEntity);
            }
        });
    }
}