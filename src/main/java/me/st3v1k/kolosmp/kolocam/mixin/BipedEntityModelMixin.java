package me.st3v1k.kolosmp.kolocam.mixin;

import me.st3v1k.kolosmp.kolocam.CustomState;
import me.st3v1k.kolosmp.kolocam.KoloCam;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends BipedEntityRenderState> {

    @Final
    @Shadow
    public ModelPart head, rightArm, leftArm;

    @Inject(
            method = "positionLeftArm",
            at = @At("TAIL")
    )
    private void cameraLeftArmPoses(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (((CustomState) state).getMainHandStack().isOf(KoloCam.CAMERA_ITEM)) {
            this.leftArm.roll = 0.0F;
            this.leftArm.yaw = 0.16F + this.head.yaw + 0.4F;
            this.leftArm.pitch = -1.5707964F + this.head.pitch;
        }

    }

    @Inject(
            method = "positionRightArm",
            at = @At("TAIL")
    )
    private void cameraRightArmPoses(T state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (((CustomState) state).getMainHandStack().isOf(KoloCam.CAMERA_ITEM)) {
            this.rightArm.roll = 0.0F;
            this.rightArm.yaw = -0.1F + this.head.yaw;
            this.rightArm.pitch = -1.5707964F + this.head.pitch;
        }
    }
}
