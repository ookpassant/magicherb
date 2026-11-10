package net.magicherb.mixin;

import net.magicherb.item.Aging;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Barrels have no item hooks of their own, so watch the shared container methods and act only for barrels.
@Mixin(RandomizableContainerBlockEntity.class)
abstract class RandomizableContainerBlockEntityMixin {

    @Inject(method = "setItem", at = @At("TAIL"))
    private void magicherb$startCuring(int slot, ItemStack stack, CallbackInfo ci) {
        if ((Object) this instanceof BarrelBlockEntity barrel && barrel.getLevel() instanceof ServerLevel level) {
            Aging.startCuring(stack, level.getGameTime());
        }
    }

    @Inject(method = {"removeItem", "removeItemNoUpdate"}, at = @At("RETURN"))
    private void magicherb$finishCuring(CallbackInfoReturnable<ItemStack> cir) {
        if ((Object) this instanceof BarrelBlockEntity barrel && barrel.getLevel() instanceof ServerLevel level) {
            Aging.finishCuring(cir.getReturnValue(), level.getGameTime());
        }
    }
}
