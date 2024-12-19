package it.fulminazzo.jbukkit.potion;

import com.google.common.collect.BiMap;
import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import static org.bukkit.potion.PotionEffectTypeCategory.*;

/**
 * Represents an implementation of {@link PotionEffectType}.
 */
@Getter
public class MockPotionEffectType extends PotionEffectType {
    public static final MockPotionEffectType SPEED = new MockPotionEffectType("Speed", BENEFICIAL, "33EBFF", false);
    public static final MockPotionEffectType SLOWNESS = new MockPotionEffectType("Slowness", HARMFUL, "5A6C81", false);
    public static final MockPotionEffectType HASTE = new MockPotionEffectType("Haste", BENEFICIAL, "D9C043", false);
    public static final MockPotionEffectType MINING_FATIGUE = new MockPotionEffectType("Mining Fatigue", HARMFUL, "4A4217", false);
    public static final MockPotionEffectType STRENGTH = new MockPotionEffectType("Strength", BENEFICIAL, "FCC500", false);
    public static final MockPotionEffectType INSTANT_HEALTH = new MockPotionEffectType("Instant Health", BENEFICIAL, "F82423", true);
    public static final MockPotionEffectType INSTANT_DAMAGE = new MockPotionEffectType("Instant Damage", HARMFUL, "430A09", true);
    public static final MockPotionEffectType JUMP_BOOST = new MockPotionEffectType("Jump Boost", BENEFICIAL, "23FC4D", false);
    public static final MockPotionEffectType NAUSEA = new MockPotionEffectType("Nausea", HARMFUL, "551D4A", false);
    public static final MockPotionEffectType REGENERATION = new MockPotionEffectType("Regeneration", BENEFICIAL, "CD5CAB", false);
    public static final MockPotionEffectType RESISTANCE = new MockPotionEffectType("Resistance", BENEFICIAL, "8F45ED", false);
    public static final MockPotionEffectType FIRE_RESISTANCE = new MockPotionEffectType("Fire Resistance", BENEFICIAL, "E49A3A", false);
    public static final MockPotionEffectType WATER_BREATHING = new MockPotionEffectType("Water Breathing", BENEFICIAL, "96D7BE", false);
    public static final MockPotionEffectType INVISIBILITY = new MockPotionEffectType("Invisibility", BENEFICIAL, "7F8392", false);
    public static final MockPotionEffectType BLINDNESS = new MockPotionEffectType("Blindness", HARMFUL, "1F1F23", false);
    public static final MockPotionEffectType NIGHT_VISION = new MockPotionEffectType("Night Vision", BENEFICIAL, "1F1FA1", false);
    public static final MockPotionEffectType HUNGER = new MockPotionEffectType("Hunger", HARMFUL, "587653", false);
    public static final MockPotionEffectType WEAKNESS = new MockPotionEffectType("Weakness", BENEFICIAL, "484D48", false);
    public static final MockPotionEffectType POISON = new MockPotionEffectType("Poison", HARMFUL, "4E9331", false);
    public static final MockPotionEffectType WITHER = new MockPotionEffectType("Wither", HARMFUL, "352A27", false);
    public static final MockPotionEffectType HEALTH_BOOST = new MockPotionEffectType("Health Boost", BENEFICIAL, "F87D23", false);
    public static final MockPotionEffectType ABSORPTION = new MockPotionEffectType("Absorption", BENEFICIAL, "2552A5", false);
    public static final MockPotionEffectType SATURATION = new MockPotionEffectType("Saturation", BENEFICIAL, "F82421", false);
    public static final MockPotionEffectType GLOWING = new MockPotionEffectType("Glowing", NEUTRAL, "94A061", false);
    public static final MockPotionEffectType LEVITATION = new MockPotionEffectType("Levitation", HARMFUL, "CEFFFF", false);
    public static final MockPotionEffectType LUCK = new MockPotionEffectType("Luck", BENEFICIAL, "339900", false);
    public static final MockPotionEffectType UNLUCK = new MockPotionEffectType("Bad Luck", HARMFUL, "C0A44D", false);
    public static final MockPotionEffectType SLOW_FALLING = new MockPotionEffectType("Slow Falling", BENEFICIAL, "FFEFD1", false);
    public static final MockPotionEffectType CONDUIT_POWER = new MockPotionEffectType("Conduit Power", BENEFICIAL, "1DC2D1", false);
    public static final MockPotionEffectType DOLPHINS_GRACE = new MockPotionEffectType("Dolphins Grace", BENEFICIAL, "88A3BE", false);
    public static final MockPotionEffectType BAD_OMEN = new MockPotionEffectType("Bad Omen", NEUTRAL, "0B6138", false);
    public static final MockPotionEffectType HERO_OF_THE_VILLAGE = new MockPotionEffectType("Hero of the Village", BENEFICIAL, "44FF44", false);
    public static final MockPotionEffectType DARKNESS = new MockPotionEffectType("Darkness", HARMFUL, "292721", false);
    public static final MockPotionEffectType TRIAL_OMEN = new MockPotionEffectType("Trial Omen", HARMFUL, "000000", false);
    public static final MockPotionEffectType RAID_OMEN = new MockPotionEffectType("Raid Omen", HARMFUL, "000000", false);
    public static final MockPotionEffectType WIND_CHARGED = new MockPotionEffectType("Wind Charged", HARMFUL, "000000", false);
    public static final MockPotionEffectType WEAVING = new MockPotionEffectType("Weaving", HARMFUL, "000000", false);
    public static final MockPotionEffectType OOZING = new MockPotionEffectType("Oozing", HARMFUL, "000000", false);
    public static final MockPotionEffectType INFESTED = new MockPotionEffectType("Infested", HARMFUL, "000000", false);

    private static int LAST_USED_ID = 1;
    private final int id;
    private final @NotNull NamespacedKey key;
    private final @NotNull String name;
    private final @NotNull PotionEffectTypeCategory category;
    private final @NotNull Color color;
    private final boolean instant;

    /**
     * Instantiates a new Mock potion effect type.
     *
     * @param name      the name
     * @param category the category
     * @param color    the color
     * @param instant  true if its effects are applied instantly
     */
    public MockPotionEffectType(final @NotNull String name,
                                final @NotNull PotionEffectTypeCategory category,
                                final @NotNull String color, final boolean instant) {
        this(name, category, color(color), instant);
    }

    /**
     * Instantiates a new Mock potion effect type.
     *
     * @param name      the name
     * @param category the category
     * @param color    the color
     * @param instant  true if its effects are applied instantly
     */
    public MockPotionEffectType(final @NotNull String name,
                                final @NotNull PotionEffectTypeCategory category,
                                final @NotNull Color color, final boolean instant) {
        this.id = LAST_USED_ID++;
        this.name = Objects.requireNonNull(name);
        this.key = NamespacedKey.minecraft(StringUtils.decapitalize(name).toLowerCase());
        this.category = category;
        this.color = color;
        this.instant = instant;
        // Set to internal map
        BiMap<Integer, PotionEffectType> idMap = new Refl<>(PotionEffectType.class).getFieldObject("ID_MAP");
        Objects.requireNonNull(idMap).put(this.id, this);
    }

    @Override
    public @NotNull PotionEffect createEffect(int duration, int amplifier) {
        return new PotionEffect(this, duration, amplifier);
    }

    @Override
    public double getDurationModifier() {
        return 1.0;
    }

    @Override
    public @NotNull String getTranslationKey() {
        throw new NotImplementedException();
    }

    /**
     * Gets the {@link MockPotionEffectType} from the given key.
     * Throws {@link IllegalArgumentException} if it fails.
     *
     * @param key the key
     * @return the mock potion effect type
     */
    public static @NotNull MockPotionEffectType valueOf(final @NotNull NamespacedKey key) {
        return valueOf(key.getKey());
    }

    /**
     * Gets the {@link MockPotionEffectType} from the given name.
     * Throws {@link IllegalArgumentException} if it fails.
     *
     * @param name the name
     * @return the mock potion effect type
     */
    public static @NotNull MockPotionEffectType valueOf(final @NotNull String name) {
        return Arrays.stream(values())
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown potion effect type: " + name));
    }

    /**
     * Gets all the vanilla {@link MockPotionEffectType}s.
     *
     * @return the mock potion effect types
     */
    public static MockPotionEffectType @NotNull [] values() {
        Refl<?> clazz = new Refl<>(MockPotionEffectType.class);
        return clazz.getStaticFields().stream()
                .filter(f -> f.getType().equals(MockPotionEffectType.class))
                .map(clazz::getFieldObject)
                .map(o -> (MockPotionEffectType) o)
                .toArray(MockPotionEffectType[]::new);
    }

    private static @NotNull Color color(final @NotNull String color) {
        if (color.length() != 6) throw new IllegalArgumentException("color must have 6 characters");
        String red = color.substring(0, 2);
        String green = color.substring(2, 4);
        String blue = color.substring(4, 6);
        return Color.fromRGB(Integer.parseInt(red, 16), Integer.parseInt(green, 16), Integer.parseInt(blue, 16));
    }

}
