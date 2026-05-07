package ivetouchedgrass.hold_for_perspective.client;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.CameraType;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class HoldForPerspectiveClient implements ClientModInitializer {
    public static boolean isModActive = true;
    public static boolean wasModToggleKeyPressed = false;

    public static final OptionInstance<CameraType> SET_PERSPECTIVE = new OptionInstance<>(
            "options.hold_for_perspective.perspective_when_held",
            value -> Tooltip.create(
                    Component.translatable("tooltip.hold_for_perspective.base_tooltip")
                            .append("\n\n")
                            .append(
                                    Component.translatable(
                                                    "tooltip.hold_for_perspective." + value.name().toLowerCase()
                                            )
                                            .append("\n\n").append(
                                                    ModKeybinds.TOGGLE_MOD.isUnbound()
                                                            ? Component.translatable("tooltip.hold_for_perspective.no_keybind")
                                                            : Component.translatable(
                                                            "tooltip.hold_for_perspective.only_when_enabled",
                                                            ModKeybinds.TOGGLE_MOD.getTranslatedKeyMessage()
                                                    )
                                            )
                            )
            ),
            (component, value) -> Component.translatable(
                    "tooltip.hold_for_perspective." + value.name().toLowerCase() + ".name"
            ),
            new OptionInstance.Enum<>(Arrays.asList(CameraType.values()), Codec.INT.xmap(
                    index -> CameraType.values()[index],
                    value -> Arrays.asList(CameraType.values()).indexOf(value)
            )),
            CameraType.THIRD_PERSON_FRONT,
            _ -> {
            }
    );

    @Override
    public void onInitializeClient() {
        ModKeybinds.registerKeybinds();
    }
}
