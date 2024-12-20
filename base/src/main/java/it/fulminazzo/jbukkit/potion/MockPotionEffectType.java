package it.fulminazzo.jbukkit.potion;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an implementation of {@link PotionEffectType}.
 */
@Getter
public class MockPotionEffectType extends PotionEffectType {
    private final @NotNull String name;

    /**
     * Instantiates a new Mock potion effect type.
     *
     * @param id   the id
     * @param name the name
     */
    public MockPotionEffectType(final int id, final @NotNull String name) {
        super(id);
        this.name = name;
    }

    @Override
    public double getDurationModifier() {
        return 1.0;
    }

    @Override
    public boolean isInstant() {
        return getName().contains("Instant");
    }

    @Override
    public @NotNull Color getColor() {
        return color(getColorString());
    }

    private @NotNull String getColorString() {
        switch (getName()) {
            case "Speed":
                return "33EBFF";
            case "Slowness":
                return "5A6C81";
            case "Haste":
                return "D9C043";
            case "Mining Fatigue":
                return "4A4217";
            case "Strength":
                return "FCC500";
            case "Instant Health":
                return "F82423";
            case "Instant Damage":
                return "430A09";
            case "Jump Boost":
                return "23FC4D";
            case "Nausea":
                return "551D4A";
            case "Regeneration":
                return "CD5CAB";
            case "Resistance":
                return "8F45ED";
            case "Fire Resistance":
                return "E49A3A";
            case "Water Breathing":
                return "96D7BE";
            case "Invisibility":
                return "7F8392";
            case "Blindness":
                return "1F1F23";
            case "Night Vision":
                return "1F1FA1";
            case "Hunger":
                return "587653";
            case "Weakness":
                return "484D48";
            case "Poison":
                return "4E9331";
            case "Wither":
                return "352A27";
            case "Health Boost":
                return "F87D23";
            case "Absorption":
                return "2552A5";
            case "Saturation":
                return "F82421";
            case "Glowing":
                return "94A061";
            case "Levitation":
                return "CEFFFF";
            case "Luck":
                return "339900";
            case "Bad Luck":
                return "C0A44D";
            case "Slow Falling":
                return "FFEFD1";
            case "Conduit Power":
                return "1DC2D1";
            case "Dolphins Grace":
                return "88A3BE";
            case "Bad Omen":
                return "0B6138";
            case "Hero of the Village":
                return "44FF44";
            case "Darkness":
                return "292721";
            default:
                return "000000";
        }
    }

    private static @NotNull Color color(final @NotNull String color) {
        if (color.length() != 6) throw new IllegalArgumentException("color must have 6 characters");
        String red = color.substring(0, 2);
        String green = color.substring(2, 4);
        String blue = color.substring(4, 6);
        return Color.fromRGB(Integer.parseInt(red, 16), Integer.parseInt(green, 16), Integer.parseInt(blue, 16));
    }

}
