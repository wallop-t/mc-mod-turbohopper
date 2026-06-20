package net.turbohopper.mixin;

import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityCooldownMixin {
    @ModifyArg(
        method = "tryMoveItems",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;setCooldown(I)V"
        ),
        index = 0
    )
    private static int reduceCooldown(int cooldown) {
        return 1;
    }
}
