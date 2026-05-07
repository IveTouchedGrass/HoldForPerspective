package ivetouchedgrass.hold_for_perspective.client.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import ivetouchedgrass.hold_for_perspective.client.HoldForPerspectiveClient;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(AccessibilityOptionsScreen.class)
public class MixinAddPerspectiveSetting {
    @ModifyReturnValue(method = "options", at = @At("RETURN"))
    private static OptionInstance<?>[] addOption(OptionInstance<?>[] original) {
        return appendElementsToArray(original, HoldForPerspectiveClient.SET_PERSPECTIVE);
    }

    @SuppressWarnings("SameParameterValue")
    @Unique
    @SafeVarargs
    private static <T> T[] appendElementsToArray(T[] array, T... elements) {
        T[] newArr = Arrays.copyOf(array, array.length + elements.length);
        System.arraycopy(elements, 0, newArr, array.length, newArr.length - array.length);
        return newArr;
    }
}