package ivetouchedgrass.hold_for_perspective.client.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import ivetouchedgrass.hold_for_perspective.client.HoldForPerspectiveClient;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class MixinOptions {
    @ModifyReturnValue(method = "getCameraType", at = @At("RETURN"))
    private CameraType modifyPerspective(CameraType original) {
        return HoldForPerspectiveClient.isModActive && Minecraft.getInstance().options.keyTogglePerspective.isDown() ? HoldForPerspectiveClient.SET_PERSPECTIVE.get() : original;
    }

    @Inject(method = "processOptions", at = @At("TAIL"))
    private void saveAndLoadOptions(Options.FieldAccess visitor, CallbackInfo ci) {
        visitor.process("hold_for_perspective.perspective_when_holding", HoldForPerspectiveClient.SET_PERSPECTIVE);
    }
}