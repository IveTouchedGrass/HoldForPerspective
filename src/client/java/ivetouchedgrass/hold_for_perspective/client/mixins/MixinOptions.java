package ivetouchedgrass.hold_for_perspective.client.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import ivetouchedgrass.hold_for_perspective.client.HoldForPerspectiveClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class MixinOptions {
    @Shadow
    private Perspective perspective;

    @ModifyReturnValue(method = "getPerspective", at = @At("RETURN"))
    private Perspective modifyPerspective(Perspective original) {
        return HoldForPerspectiveClient.isModActive && MinecraftClient.getInstance().options.togglePerspectiveKey.isPressed() ? HoldForPerspectiveClient.SET_PERSPECTIVE.getValue() : original;
    }

    @Inject(method = "accept", at = @At("TAIL"))
    private void saveAndLoadOptions(GameOptions.Visitor visitor, CallbackInfo ci) {
        visitor.accept("hold_for_perspective.perspective_when_holding", HoldForPerspectiveClient.SET_PERSPECTIVE);
    }
}