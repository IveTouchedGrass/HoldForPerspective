package ivetouchedgrass.hold_for_perspective.client;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.Arrays;

public class HoldForPerspectiveClient implements ClientModInitializer {
    public static boolean isModActive = true;
    public static boolean wasModToggleKeyPressed = false;

    public static final SimpleOption<Perspective> SET_PERSPECTIVE = new SimpleOption<>(
            "options.hold_for_perspective.perspective_when_held",
            value -> Tooltip.of(
                    Text.translatable("tooltip.hold_for_perspective.base_tooltip")
                            .append("\n\n")
                            .append(
                                    Text.translatable(
                                                    "tooltip.hold_for_perspective." + value.name().toLowerCase()
                                            )
                                            .append("\n\n").append(
                                                    ModKeybinds.TOGGLE_MOD.isUnbound()
                                                            ? Text.translatable("tooltip.hold_for_perspective.no_keybind")
                                                            : Text.translatable(
                                                            "tooltip.hold_for_perspective.only_when_enabled",
                                                            ModKeybinds.TOGGLE_MOD.getBoundKeyLocalizedText()
                                                    )
                                            )
                            )
            ),
            (component, value) -> Text.translatable(
                    "tooltip.hold_for_perspective." + value.name().toLowerCase() + ".name"
            ),
            new SimpleOption.PotentialValuesBasedCallbacks<>(
                    Arrays.asList(Perspective.values()),
                    Codec.INT.xmap(
                            index -> Perspective.values()[index],
                            value -> Arrays.asList(Perspective.values()).indexOf(value)
                    )
            ),
            Perspective.THIRD_PERSON_FRONT,
            value -> {
            }
    );

    @Override
    public void onInitializeClient() {
        ModKeybinds.registerKeybinds();
    }
}
