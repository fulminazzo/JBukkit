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
    private static final Supplier<MockEnchantment> PROTECTION = () -> new MockEnchantment("protection", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> FIRE_PROTECTION = () -> new MockEnchantment("fire_protection", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> BLAST_PROTECTION = () -> new MockEnchantment("blast_protection", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> PROJECTILE_PROTECTION = () -> new MockEnchantment("projectile_protection", 1, 4, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> FEATHER_FALLING = () -> new MockEnchantment("feather_falling", 1, 4, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> RESPIRATION = () -> new MockEnchantment("respiration", 1, 3, EnchantmentTarget.ARMOR_HEAD);
    private static final Supplier<MockEnchantment> AQUA_AFFINITY = () -> new MockEnchantment("aqua_affinity", 1, 1, EnchantmentTarget.ARMOR_HEAD);
    private static final Supplier<MockEnchantment> THORNS = () -> new MockEnchantment("thorns", 1, 3, EnchantmentTarget.ARMOR);
    private static final Supplier<MockEnchantment> DEPTH_STRIDER = () -> new MockEnchantment("depth_strider", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> FROST_WALKER = () -> new MockEnchantment("frost_walker", 1, 2, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> BINDING_CURSE = () -> new MockEnchantment("binding_curse", 1, 1, EnchantmentTarget.WEARABLE);
    private static final Supplier<MockEnchantment> SHARPNESS = () -> new MockEnchantment("sharpness", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> SMITE = () -> new MockEnchantment("smite", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> BANE_OF_ARTHROPODS = () -> new MockEnchantment("bane_of_arthropods", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> KNOCKBACK = () -> new MockEnchantment("knockback", 1, 2, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> FIRE_ASPECT = () -> new MockEnchantment("fire_aspect", 1, 2, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> LOOTING = () -> new MockEnchantment("looting", 1, 3, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> SWEEPING_EDGE = () -> new MockEnchantment("sweeping_edge", 1, 3, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> EFFICIENCY = () -> new MockEnchantment("efficiency", 1, 5, EnchantmentTarget.TOOL);
    private static final Supplier<MockEnchantment> SILK_TOUCH = () -> new MockEnchantment("silk_touch", 1, 1, EnchantmentTarget.TOOL);
    private static final Supplier<MockEnchantment> UNBREAKING = () -> new MockEnchantment("unbreaking", 1, 3, EnchantmentTarget.BREAKABLE);
    private static final Supplier<MockEnchantment> FORTUNE = () -> new MockEnchantment("fortune", 1, 3, EnchantmentTarget.TOOL);
    private static final Supplier<MockEnchantment> POWER = () -> new MockEnchantment("power", 1, 5, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> PUNCH = () -> new MockEnchantment("punch", 1, 2, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> FLAME = () -> new MockEnchantment("flame", 1, 1, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> INFINITY = () -> new MockEnchantment("infinity", 1, 1, EnchantmentTarget.BOW);
    private static final Supplier<MockEnchantment> LUCK_OF_THE_SEA = () -> new MockEnchantment("luck_of_the_sea", 1, 3, EnchantmentTarget.FISHING_ROD);
    private static final Supplier<MockEnchantment> LURE = () -> new MockEnchantment("lure", 1, 3, EnchantmentTarget.FISHING_ROD);
    private static final Supplier<MockEnchantment> LOYALTY = () -> new MockEnchantment("loyalty", 1, 3, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> IMPALING = () -> new MockEnchantment("impaling", 1, 5, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> RIPTIDE = () -> new MockEnchantment("riptide", 1, 3, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> CHANNELING = () -> new MockEnchantment("channeling", 1, 1, EnchantmentTarget.TRIDENT);
    private static final Supplier<MockEnchantment> MULTISHOT = () -> new MockEnchantment("multishot", 1, 1, EnchantmentTarget.CROSSBOW);
    private static final Supplier<MockEnchantment> QUICK_CHARGE = () -> new MockEnchantment("quick_charge", 1, 3, EnchantmentTarget.CROSSBOW);
    private static final Supplier<MockEnchantment> PIERCING = () -> new MockEnchantment("piercing", 1, 4, EnchantmentTarget.CROSSBOW);
    private static final Supplier<MockEnchantment> MENDING = () -> new MockEnchantment("mending", 1, 1, EnchantmentTarget.BREAKABLE);
    private static final Supplier<MockEnchantment> VANISHING_CURSE = () -> new MockEnchantment("vanishing_curse", 1, 1, EnchantmentTarget.VANISHABLE);
    private static final Supplier<MockEnchantment> SOUL_SPEED = () -> new MockEnchantment("soul_speed", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> SWIFT_SNEAK = () -> new MockEnchantment("swift_sneak", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final Supplier<MockEnchantment> DENSITY = () -> new MockEnchantment("density", 1, 5, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> WIND_BURST = () -> new MockEnchantment("wind_burst", 1, 3, EnchantmentTarget.WEAPON);
    private static final Supplier<MockEnchantment> BREACH = () -> new MockEnchantment("breach", 1, 4, EnchantmentTarget.WEAPON);

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
     * It will copy each value from the given enchantment.
     *
     * @param enchantment the enchantment
     */
    public MockEnchantment(final @NotNull Enchantment enchantment) {
        this(enchantment.getKey(), enchantment.getName(), enchantment.getStartLevel(),
                enchantment.getMaxLevel(), enchantment.getItemTarget());
        setTreasure(enchantment.isTreasure()).setCursed(enchantment.isCursed());
    }

    /**
     * Instantiates a new Mock enchantment.
     *
     * @param name       the name
     * @param startLevel the start level
     * @param maxLevel   the max level
     * @param itemTarget the item target
     */
    protected MockEnchantment(final @NotNull String name, final int startLevel,
                              final int maxLevel, final @NotNull EnchantmentTarget itemTarget) {
        this(NamespacedKey.minecraft(name), name, startLevel, maxLevel, itemTarget);
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
        Refl<?> mock = new Refl<>(MockEnchantment.class);
        try {
            return mock.getFieldObject(StringUtils.capitalize(key.getKey()));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static @Nullable Enchantment valueOfEnchantment(final @NotNull NamespacedKey key) {
        Refl<?> mock = new Refl<>(Enchantment.class);
        try {
            return mock.getFieldObject(StringUtils.capitalize(key.getKey()));
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
