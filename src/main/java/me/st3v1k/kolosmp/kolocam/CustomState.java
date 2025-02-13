package me.st3v1k.kolosmp.kolocam;

import net.minecraft.item.ItemStack;

public interface CustomState {

    ItemStack getMainHandStack();

    void setMainHandStack(ItemStack stack);
}
