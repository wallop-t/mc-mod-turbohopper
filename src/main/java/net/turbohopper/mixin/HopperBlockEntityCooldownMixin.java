package net.turbohopper.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityCooldownMixin {
    @ModifyArg(
        method = "insertAndExtract",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/entity/HopperBlockEntity;setTransferCooldown(I)V"
        ),
        index = 0
    )
    private static int forceOneTickCooldown(int original) {
        return 1; // Always set cooldown to 1 tick
    }
}
