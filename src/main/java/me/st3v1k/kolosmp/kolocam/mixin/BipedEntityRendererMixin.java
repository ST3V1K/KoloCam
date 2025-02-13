package me.st3v1k.kolosmp.kolocam.mixin;

import me.st3v1k.kolosmp.kolocam.CustomState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin {

    @Inject(
            method = "updateBipedRenderState",
            at = @At("TAIL")
    )
    private static void onUpdateBipedRenderState(LivingEntity entity, BipedEntityRenderState state, float tickDelta, ItemModelManager itemModelResolver, CallbackInfo ci) {
        ((CustomState) state).setMainHandStack(entity.getMainHandStack());
    }
}
