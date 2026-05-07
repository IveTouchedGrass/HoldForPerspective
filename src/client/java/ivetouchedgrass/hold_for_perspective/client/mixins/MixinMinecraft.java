package ivetouchedgrass.hold_for_perspective.client.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ivetouchedgrass.hold_for_perspective.client.HoldForPerspectiveClient;
import ivetouchedgrass.hold_for_perspective.client.ModKeybinds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;consumeClick()Z", ordinal = 0))
    private boolean wrapPerspectiveSet(KeyMapping instance, Operation<Boolean> original) {
        if (HoldForPerspectiveClient.isModActive && instance == Minecraft.getInstance().options.keyTogglePerspective) {
            while (original.call(instance)) {}
            return false;
        }
        return original.call(instance);
    }

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void allowModToggling(CallbackInfo ci) {
        if (HoldForPerspectiveClient.wasModToggleKeyPressed || Minecraft.getInstance().player == null) {
            HoldForPerspectiveClient.wasModToggleKeyPressed = ModKeybinds.TOGGLE_MOD.isDown();
            return;
        }
        if (ModKeybinds.TOGGLE_MOD.isDown()) {
            HoldForPerspectiveClient.isModActive = !HoldForPerspectiveClient.isModActive;
            Minecraft.getInstance().player.sendOverlayMessage(Component.translatable(HoldForPerspectiveClient.isModActive ? "hold_for_perspective.mod_enabled" : "hold_for_perspective.mod_disabled").withStyle(HoldForPerspectiveClient.isModActive ? ChatFormatting.GREEN : ChatFormatting.RED));
        }
        HoldForPerspectiveClient.wasModToggleKeyPressed = ModKeybinds.TOGGLE_MOD.isDown();
    }
}