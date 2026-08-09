package ghosti.nostarving.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
   private int foodLevel;

   @Shadow
   private float saturationLevel;

   @Shadow
   private float exhaustionLevel;

   @Inject(method = "add", at = @At("HEAD"), cancellable = true)
   private void add(final int food, final float saturation, CallbackInfo ci) {
        // just record the nutrition component
        this.foodLevel += food;

        // throw out saturation, we're always "full"

        ci.cancel();
   }
   
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(final ServerPlayer player, CallbackInfo ci) {
        // just clear this crap out
        this.saturationLevel = 5.0F;
        this.exhaustionLevel = 0.0F;
        
        // apply food level directly as healing this turn
        player.heal(this.foodLevel);
        this.foodLevel = 0;

        ci.cancel();
    }

    @Inject(method = "hasEnoughFood", at = @At("HEAD"), cancellable = true)
    public void hasEnoughFood(CallbackInfoReturnable<Boolean> ci) {
        // necessary to preserve sprint functions
        ci.setReturnValue(true);
    }

    @Inject(method = "needsFood", at = @At("HEAD"), cancellable = true)
    public void needsFood(CallbackInfoReturnable<Boolean> ci) {
        // always return true here, so that the player can eat larger nutritional content than 20
        ci.setReturnValue(true);
    }
}
