package ghosti.nostarving.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow
    public abstract boolean isHurt();

    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void canEat(final boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        // invokes super::cancel internally
        cir.setReturnValue(this.isHurt());
    }
}
