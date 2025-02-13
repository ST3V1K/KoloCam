package me.st3v1k.kolosmp.kolocam.mixin;

import me.st3v1k.kolosmp.kolocam.CustomState;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BipedEntityRenderState.class)
public abstract class BipedEntityRenderStateMixin implements CustomState {

    @Unique
    private ItemStack mainHandStack = ItemStack.EMPTY;

    @Override
    public ItemStack getMainHandStack() {
        return this.mainHandStack;
    }

    @Override
    public void setMainHandStack(ItemStack stack) {
        this.mainHandStack = stack;
    }
}
