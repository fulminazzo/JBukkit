package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Printable;
import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an implementation of {@link Enchantment}.
 */
@Getter
public class MockEnchantment extends Enchantment {
    public static final MockEnchantment PROTECTION = new MockEnchantment("protection", 1, 4, EnchantmentTarget.ARMOR);
    public static final MockEnchantment FIRE_PROTECTION = new MockEnchantment("fire_protection", 1, 4, EnchantmentTarget.ARMOR);
    public static final MockEnchantment BLAST_PROTECTION = new MockEnchantment("blast_protection", 1, 4, EnchantmentTarget.ARMOR);
    public static final MockEnchantment PROJECTILE_PROTECTION = new MockEnchantment("projectile_protection", 1, 4, EnchantmentTarget.ARMOR);
    public static final MockEnchantment FEATHER_FALLING = new MockEnchantment("feather_falling", 1, 4, EnchantmentTarget.ARMOR_FEET);
    public static final MockEnchantment RESPIRATION = new MockEnchantment("respiration", 1, 3, EnchantmentTarget.ARMOR_HEAD);
    public static final MockEnchantment AQUA_AFFINITY = new MockEnchantment("aqua_affinity", 1, 1, EnchantmentTarget.ARMOR_HEAD);
    public static final MockEnchantment THORNS = new MockEnchantment("thorns", 1, 3, EnchantmentTarget.ARMOR);
    public static final MockEnchantment DEPTH_STRIDER = new MockEnchantment("depth_strider", 1, 3, EnchantmentTarget.ARMOR_FEET);
    public static final MockEnchantment FROST_WALKER = new MockEnchantment("frost_walker", 1, 2, EnchantmentTarget.ARMOR_FEET);
    public static final MockEnchantment BINDING_CURSE = new MockEnchantment("binding_curse", 1, 1, EnchantmentTarget.WEARABLE);
    public static final MockEnchantment SHARPNESS = new MockEnchantment("sharpness", 1, 5, EnchantmentTarget.WEAPON);
    public static final MockEnchantment SMITE = new MockEnchantment("smite", 1, 5, EnchantmentTarget.WEAPON);
    public static final MockEnchantment BANE_OF_ARTHROPODS = new MockEnchantment("bane_of_arthropods", 1, 5, EnchantmentTarget.WEAPON);
    public static final MockEnchantment KNOCKBACK = new MockEnchantment("knockback", 1, 2, EnchantmentTarget.WEAPON);
    public static final MockEnchantment FIRE_ASPECT = new MockEnchantment("fire_aspect", 1, 2, EnchantmentTarget.WEAPON);
    public static final MockEnchantment LOOTING = new MockEnchantment("looting", 1, 3, EnchantmentTarget.WEAPON);
    public static final MockEnchantment SWEEPING_EDGE = new MockEnchantment("sweeping_edge", 1, 3, EnchantmentTarget.WEAPON);
    public static final MockEnchantment EFFICIENCY = new MockEnchantment("efficiency", 1, 5, EnchantmentTarget.TOOL);
    public static final MockEnchantment SILK_TOUCH = new MockEnchantment("silk_touch", 1, 1, EnchantmentTarget.TOOL);
    public static final MockEnchantment UNBREAKING = new MockEnchantment("unbreaking", 1, 3, EnchantmentTarget.BREAKABLE);
    public static final MockEnchantment FORTUNE = new MockEnchantment("fortune", 1, 3, EnchantmentTarget.TOOL);
    public static final MockEnchantment POWER = new MockEnchantment("power", 1, 5, EnchantmentTarget.BOW);
    public static final MockEnchantment FLAME = new MockEnchantment("flame", 1, 1, EnchantmentTarget.BOW);
    public static final MockEnchantment INFINITY = new MockEnchantment("infinity", 1, 1, EnchantmentTarget.BOW);
    public static final MockEnchantment LUCK_OF_THE_SEA = new MockEnchantment("luck_of_the_sea", 1, 3, EnchantmentTarget.FISHING_ROD);
    public static final MockEnchantment LURE = new MockEnchantment("lure", 1, 3, EnchantmentTarget.FISHING_ROD);
    public static final MockEnchantment LOYALTY = new MockEnchantment("loyalty", 1, 3, EnchantmentTarget.TRIDENT);
    public static final MockEnchantment IMPALING = new MockEnchantment("impaling", 1, 5, EnchantmentTarget.TRIDENT);
    public static final MockEnchantment RIPTIDE = new MockEnchantment("riptide", 1, 3, EnchantmentTarget.TRIDENT);
    public static final MockEnchantment CHANNELING = new MockEnchantment("channeling", 1, 1, EnchantmentTarget.TRIDENT);
    public static final MockEnchantment MULTISHOT = new MockEnchantment("multishot", 1, 1, EnchantmentTarget.CROSSBOW);
    public static final MockEnchantment QUICK_CHARGE = new MockEnchantment("quick_charge", 1, 3, EnchantmentTarget.CROSSBOW);
    public static final MockEnchantment PIERCING = new MockEnchantment("piercing", 1, 4, EnchantmentTarget.CROSSBOW);
    public static final MockEnchantment MENDING = new MockEnchantment("mending", 1, 1, EnchantmentTarget.BREAKABLE);
    public static final MockEnchantment VANISHING_CURSE = new MockEnchantment("vanishing_curse", 1, 1, EnchantmentTarget.VANISHABLE);
    public static final MockEnchantment SOUL_SPEED = new MockEnchantment("soul_speed", 1, 3, EnchantmentTarget.ARMOR_FEET);
    public static final MockEnchantment SWIFT_SNEAK = new MockEnchantment("swift_sneak", 1, 3, EnchantmentTarget.ARMOR_FEET);
    public static final MockEnchantment DENSITY = new MockEnchantment("density", 1, 5, EnchantmentTarget.WEAPON);
    public static final MockEnchantment WIND_BURST = new MockEnchantment("wind_burst", 1, 3, EnchantmentTarget.WEAPON);
    public static final MockEnchantment BREACH = new MockEnchantment("breach", 1, 4, EnchantmentTarget.WEAPON);

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

    /**
     * Gets the {@link MockEnchantment} from the given key.
     * Throws {@link IllegalArgumentException} if it fails.
     *
     * @param key the key
     * @return the mock enchantment
     */
    public static @NotNull MockEnchantment valueOf(final @NotNull NamespacedKey key) {
        return Arrays.stream(values())
                .filter(e -> e.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown enchantment: " + key));
    }

    /**
     * Gets the {@link MockEnchantment} from the given name.
     * Throws {@link IllegalArgumentException} if it fails.
     *
     * @param name the name
     * @return the mock enchantment
     */
    public static @NotNull MockEnchantment valueOf(final @NotNull String name) {
        return Arrays.stream(values())
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown enchantment: " + name));
    }

    /**
     * Gets all the vanilla {@link MockEnchantment}s.
     *
     * @return the mock enchantments
     */
    public static MockEnchantment @NotNull [] values() {
        Refl<?> clazz = new Refl<>(MockEnchantment.class);
        return clazz.getStaticFields().stream()
                .filter(f -> f.getType().equals(MockEnchantment.class))
                .map(clazz::getFieldObject)
                .map(o -> (MockEnchantment) o)
                .toArray(MockEnchantment[]::new);
    }

}
