package ivetouchedgrass.hold_for_perspective.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ivetouchedgrass.hold_for_perspective.client.HoldForPerspectiveClient;
import ivetouchedgrass.hold_for_perspective.client.ModKeybinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraft {
    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z", ordinal = 0))
    private boolean wrapPerspectiveSet(KeyBinding instance, Operation<Boolean> original) {
        if (HoldForPerspectiveClient.isModActive && instance == MinecraftClient.getInstance().options.togglePerspectiveKey) {
            while (original.call(instance)) {}
            return false;
        }
        return original.call(instance);
    }

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void allowModToggling(CallbackInfo ci) {
        if (HoldForPerspectiveClient.wasModToggleKeyPressed || MinecraftClient.getInstance().player == null) {
            HoldForPerspectiveClient.wasModToggleKeyPressed = ModKeybinds.TOGGLE_MOD.isPressed();
            return;
        }
        if (ModKeybinds.TOGGLE_MOD.isPressed()) {
            HoldForPerspectiveClient.isModActive = !HoldForPerspectiveClient.isModActive;
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(HoldForPerspectiveClient.isModActive ? "hold_for_perspective.mod_enabled" : "hold_for_perspective.mod_disabled").formatted(HoldForPerspectiveClient.isModActive ? Formatting.GREEN : Formatting.RED), true);
        }
        HoldForPerspectiveClient.wasModToggleKeyPressed = ModKeybinds.TOGGLE_MOD.isPressed();
    }
}