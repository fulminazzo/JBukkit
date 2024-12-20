package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Printable;
import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Represents an implementation of {@link Enchantment}.
 */
@Getter
public class MockEnchantment extends Enchantment {
    private static final Supplier<MockEnchantment> PROTECTION = () -> new MockEnchantment("PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> FIRE_PROTECTION = () -> new MockEnchantment("FIRE_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> BLAST_PROTECTION = () -> new MockEnchantment("BLAST_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> PROJECTILE_PROTECTION = () -> new MockEnchantment("PROJECTILE_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> FEATHER_FALLING = () -> new MockEnchantment("FEATHER_FALLING", 1, 4, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> RESPIRATION = () -> new MockEnchantment("RESPIRATION", 1, 3, EnchantmentTarget.ARMOR_HEAD);
    private static final Supplier<MockEnchantment> AQUA_AFFINITY = () -> new MockEnchantment("AQUA_AFFINITY", 1, 1, EnchantmentTarget.ARMOR_HEAD);
    private static final Supplier<MockEnchantment> THORNS = () -> new MockEnchantment("THORNS", 1, 3, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> DEPTH_STRIDER = () -> new MockEnchantment("DEPTH_STRIDER", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> FROST_WALKER = () -> new MockEnchantment("FROST_WALKER", 1, 2, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> BINDING_CURSE = () -> new MockEnchantment("BINDING_CURSE", 1, 1, EnchantmentTarget.WEARABLE);
    private static final Supplier<MockEnchantment> SHARPNESS = () -> new MockEnchantment("SHARPNESS", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> SMITE = () -> new MockEnchantment("SMITE", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> BANE_OF_ARTHROPODS = () -> new MockEnchantment("BANE_OF_ARTHROPODS", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> KNOCKBACK = () -> new MockEnchantment("KNOCKBACK", 1, 2, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> FIRE_ASPECT = () -> new MockEnchantment("FIRE_ASPECT", 1, 2, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> LOOTING = () -> new MockEnchantment("LOOTING", 1, 3, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> SWEEPING_EDGE = () -> new MockEnchantment("SWEEPING_EDGE", 1, 3, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> EFFICIENCY = () -> new MockEnchantment("EFFICIENCY", 1, 5, EnchantmentTarget.TOOL);
    private static final Supplier<MockEnchantment> SILK_TOUCH = () -> new MockEnchantment("SILK_TOUCH", 1, 1, EnchantmentTarget.TOOL);
    private static final Supplier<MockEnchantment> UNBREAKING = () -> new MockEnchantment("UNBREAKING", 1, 3, EnchantmentTarget.BREAKABLE);
    private static final Supplier<MockEnchantment> FORTUNE = () -> new MockEnchantment("FORTUNE", 1, 3, EnchantmentTarget.TOOL);
    private static final Supplier<MockEnchantment> POWER = () -> new MockEnchantment("POWER", 1, 5, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> PUNCH = () -> new MockEnchantment("PUNCH", 1, 2, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> FLAME = () -> new MockEnchantment("FLAME", 1, 1, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> INFINITY = () -> new MockEnchantment("INFINITY", 1, 1, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> LUCK_OF_THE_SEA = () -> new MockEnchantment("LUCK_OF_THE_SEA", 1, 3, EnchantmentTarget.FISHING_ROD);
    private static final Supplier<MockEnchantment> LURE = () -> new MockEnchantment("LURE", 1, 3, EnchantmentTarget.FISHING_ROD);
    private static final Supplier<MockEnchantment> LOYALTY = () -> new MockEnchantment("LOYALTY", 1, 3, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> IMPALING = () -> new MockEnchantment("IMPALING", 1, 5, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> RIPTIDE = () -> new MockEnchantment("RIPTIDE", 1, 3, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> CHANNELING = () -> new MockEnchantment("CHANNELING", 1, 1, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> MULTISHOT = () -> new MockEnchantment("MULTISHOT", 1, 1, EnchantmentTarget.CROSSBOW);
    private static final Supplier<MockEnchantment> QUICK_CHARGE = () -> new MockEnchantment("QUICK_CHARGE", 1, 3, EnchantmentTarget.CROSSBOW);
    private static final Supplier<MockEnchantment> PIERCING = () -> new MockEnchantment("PIERCING", 1, 4, EnchantmentTarget.CROSSBOW);
    private static final Supplier<MockEnchantment> MENDING = () -> new MockEnchantment("MENDING", 1, 1, EnchantmentTarget.BREAKABLE);
    private static final Supplier<MockEnchantment> VANISHING_CURSE = () -> new MockEnchantment("VANISHING_CURSE", 1, 1, EnchantmentTarget.VANISHABLE);
    private static final Supplier<MockEnchantment> SOUL_SPEED = () -> new MockEnchantment("SOUL_SPEED", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> SWIFT_SNEAK = () -> new MockEnchantment("SWIFT_SNEAK", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> DENSITY = () -> new MockEnchantment("DENSITY", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> WIND_BURST = () -> new MockEnchantment("WIND_BURST", 1, 3, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> BREACH = () -> new MockEnchantment("BREACH", 1, 4, EnchantmentTarget.WEAPON);

    private final NamespacedKey key;
    private final String name;
    private final int startLevel;
    private final int maxLevel;
    private final EnchantmentTarget itemTarget;
    private final Set<Enchantment> conflicts;
    private boolean treasure;
    private boolean cursed;

    /**
     * Instantiates a new Mock enchantment.
     *
     * @param name       the name
     * @param startLevel the start level
     * @param maxLevel   the max level
     * @param itemTarget the item target
     */
    public MockEnchantment(final @NotNull String name, final int startLevel,
                           final int maxLevel, final @NotNull EnchantmentTarget itemTarget) {
        this(NamespacedKey.minecraft(name.toLowerCase().replace(" ", "_")), name, startLevel, maxLevel, itemTarget);
    }

    /**
     * Instantiates a new Mock enchantment.
     *
     * @param key        the key
     * @param name       the name
     * @param startLevel the start level
     * @param maxLevel   the max level
     * @param itemTarget the item target
     */
    public MockEnchantment(final @NotNull NamespacedKey key, final @NotNull String name, final int startLevel,
                           final int maxLevel, final @NotNull EnchantmentTarget itemTarget) {
        this.key = key;
        // If the provided key belongs to a static supplier in this class
        // And the corresponding Enchantment is already initialized,
        // Throw exception.
        if (valueOf(this.key) != null && valueOfEnchantment(this.key) != null)
            throw new IllegalArgumentException(String.format("Cannot create enchantment with key \"%s\". ", this.key) +
                    "Use the vanilla enchantment from the Enchantment class instead.");
        this.name = name;
        this.startLevel = startLevel;
        this.maxLevel = maxLevel;
        this.itemTarget = itemTarget;
        this.conflicts = new HashSet<>();
    }

    /**
     * Flags this enchantment as a treasure.
     * <br>
     * Treasure enchantments can only be received via looting, trading, or
     * fishing.
     *
     * @param treasure the flag
     * @return this enchantment
     */
    public @NotNull MockEnchantment setTreasure(boolean treasure) {
        this.treasure = treasure;
        return this;
    }

    /**
     * Flags this enchantment as cursed.
     * <br>
     * Cursed enchantments are found the same way treasure enchantments are.
     *
     * @param cursed the flag
     * @return this enchantment
     */
    public @NotNull MockEnchantment setCursed(boolean cursed) {
        this.cursed = cursed;
        return this;
    }

    /**
     * Explicitly declare the enchantments with which this enchantment will conflict.
     *
     * @param conflicts the enchantments
     * @return this enchantment
     */
    public @NotNull MockEnchantment conflictsWith(final Enchantment @NotNull ... conflicts) {
        this.conflicts.addAll(Arrays.asList(conflicts));
        return this;
    }

    @Override
    public boolean conflictsWith(final @NotNull Enchantment other) {
        return this.conflicts.contains(other);
    }

    @Override
    public boolean canEnchantItem(final @NotNull ItemStack item) {
        return this.itemTarget.includes(item);
    }

    @Override
    public @NotNull String getTranslationKey() {
        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Enchantment) {
            Enchantment other = (Enchantment) obj;
            return other.getKey().equals(this.key);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public String toString() {
        return Printable.convertToJson(this);
    }

    /**
     * Gets the most appropriate {@link Enchantment} from the given {@link NamespacedKey}.
     * Uses the static {@link Supplier}s present in this class.
     * If none is found, an exception is thrown.
     *
     * @param key the key
     * @return the vanilla enchantment
     */
    public static @NotNull Enchantment getVanillaEnchantment(final @NotNull NamespacedKey key) {
        Supplier<MockEnchantment> supplier = valueOf(key);
        Objects.requireNonNull(supplier, "Could not find vanilla enchantment: " + key);
        return supplier.get();
    }

    private static @Nullable Supplier<MockEnchantment> valueOf(final @NotNull NamespacedKey key) {
        try {
            Refl<?> mock = new Refl<>(MockEnchantment.class);
            return mock.getFieldObject(StringUtils.capitalize(key.getKey()));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static @Nullable Enchantment valueOfEnchantment(final @NotNull NamespacedKey key) {
        try {
            Refl<?> mock = new Refl<>(Enchantment.class);
            return mock.getFieldObject(StringUtils.capitalize(key.getKey()).toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Sets up the default vanilla enchantments by checking the {@link Enchantment} static fields.
     */
    @SuppressWarnings("RedundantCast")
    public static void setupEnchantments() {
        // Protection
        ((MockEnchantment) Enchantment.PROTECTION).conflictsWith(Enchantment.FIRE_PROTECTION, Enchantment.BLAST_PROTECTION, Enchantment.PROJECTILE_PROTECTION);
        ((MockEnchantment) Enchantment.FIRE_PROTECTION).conflictsWith(Enchantment.PROTECTION, Enchantment.BLAST_PROTECTION, Enchantment.PROJECTILE_PROTECTION);
        ((MockEnchantment) Enchantment.BLAST_PROTECTION).conflictsWith(Enchantment.FIRE_PROTECTION, Enchantment.PROTECTION, Enchantment.PROJECTILE_PROTECTION);
        ((MockEnchantment) Enchantment.PROJECTILE_PROTECTION).conflictsWith(Enchantment.FIRE_PROTECTION, Enchantment.BLAST_PROTECTION, Enchantment.PROTECTION);
        // Depth strider
        ((MockEnchantment) Enchantment.DEPTH_STRIDER).conflictsWith(Enchantment.FROST_WALKER);
        ((MockEnchantment) Enchantment.FROST_WALKER).conflictsWith(Enchantment.DEPTH_STRIDER);
        // Sharpness
        ((MockEnchantment) Enchantment.SHARPNESS).conflictsWith(Enchantment.SMITE, Enchantment.BANE_OF_ARTHROPODS);
        ((MockEnchantment) Enchantment.SMITE).conflictsWith(Enchantment.SHARPNESS, Enchantment.BANE_OF_ARTHROPODS);
        ((MockEnchantment) Enchantment.BANE_OF_ARTHROPODS).conflictsWith(Enchantment.SMITE, Enchantment.SHARPNESS);
        // Fortune
        ((MockEnchantment) Enchantment.FORTUNE).conflictsWith(Enchantment.SILK_TOUCH);
        ((MockEnchantment) Enchantment.SILK_TOUCH).conflictsWith(Enchantment.FORTUNE);
        // Mending
        ((MockEnchantment) Enchantment.MENDING).conflictsWith(Enchantment.INFINITY);
        ((MockEnchantment) Enchantment.INFINITY).conflictsWith(Enchantment.MENDING);
        // Channeling
        ((MockEnchantment) Enchantment.RIPTIDE).conflictsWith(Enchantment.CHANNELING);
        ((MockEnchantment) Enchantment.CHANNELING).conflictsWith(Enchantment.RIPTIDE);
        // Piercing
        ((MockEnchantment) Enchantment.PIERCING).conflictsWith(Enchantment.MULTISHOT);
        ((MockEnchantment) Enchantment.MULTISHOT).conflictsWith(Enchantment.PIERCING);
        // Density
        ((MockEnchantment) Enchantment.DENSITY).conflictsWith(Enchantment.SMITE, Enchantment.BANE_OF_ARTHROPODS, Enchantment.BREACH);
        ((MockEnchantment) Enchantment.SMITE).conflictsWith(Enchantment.DENSITY, Enchantment.BANE_OF_ARTHROPODS, Enchantment.BREACH);
        ((MockEnchantment) Enchantment.BANE_OF_ARTHROPODS).conflictsWith(Enchantment.SMITE, Enchantment.DENSITY, Enchantment.BREACH);
        ((MockEnchantment) Enchantment.BREACH).conflictsWith(Enchantment.SMITE, Enchantment.BANE_OF_ARTHROPODS, Enchantment.DENSITY);
        // Cursed
        ((MockEnchantment) Enchantment.BINDING_CURSE).setCursed(true);
        ((MockEnchantment) Enchantment.VANISHING_CURSE).setCursed(true);
        // Treasure
        ((MockEnchantment) Enchantment.BINDING_CURSE).setTreasure(true);
        ((MockEnchantment) Enchantment.VANISHING_CURSE).setTreasure(true);
        ((MockEnchantment) Enchantment.MENDING).setTreasure(true);
        ((MockEnchantment) Enchantment.FROST_WALKER).setTreasure(true);
        ((MockEnchantment) Enchantment.SOUL_SPEED).setTreasure(true);
        ((MockEnchantment) Enchantment.SWIFT_SNEAK).setTreasure(true);
        ((MockEnchantment) Enchantment.WIND_BURST).setTreasure(true);
    }

}
