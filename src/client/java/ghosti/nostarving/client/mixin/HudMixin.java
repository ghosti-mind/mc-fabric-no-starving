package ghosti.nostarving.client.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Hud.class)
public class HudMixin {
   @Inject(method = "extractFood", at = @At("HEAD"), cancellable = true)
    private void onExtractFood(final GuiGraphicsExtractor graphics, final Player player, final int yLineBase, final int xRight, CallbackInfo ci) {
        // Cancel the extraction of food values to prevent the hunger bar from being rendered
        ci.cancel();
    }
}
