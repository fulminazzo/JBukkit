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
        switch (StringUtils.capitalize(key.getKey()).toUpperCase()) {
            case "PROTECTION":
                return () -> new MockEnchantment("PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
            case "FIRE_PROTECTION":
                return () -> new MockEnchantment("FIRE_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
            case "BLAST_PROTECTION":
                return () -> new MockEnchantment("BLAST_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
            case "PROJECTILE_PROTECTION":
                return () -> new MockEnchantment("PROJECTILE_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
            case "FEATHER_FALLING":
                return () -> new MockEnchantment("FEATHER_FALLING", 1, 4, EnchantmentTarget.ARMOR_FEET);
            case "RESPIRATION":
                return () -> new MockEnchantment("RESPIRATION", 1, 3, EnchantmentTarget.ARMOR_HEAD);
            case "AQUA_AFFINITY":
                return () -> new MockEnchantment("AQUA_AFFINITY", 1, 1, EnchantmentTarget.ARMOR_HEAD);
            case "THORNS":
                return () -> new MockEnchantment("THORNS", 1, 3, EnchantmentTarget.ARMOR);
            case "DEPTH_STRIDER":
                return () -> new MockEnchantment("DEPTH_STRIDER", 1, 3, EnchantmentTarget.ARMOR_FEET);
            case "FROST_WALKER":
                return () -> new MockEnchantment("FROST_WALKER", 1, 2, EnchantmentTarget.ARMOR_FEET);
            case "BINDING_CURSE":
                return () -> new MockEnchantment("BINDING_CURSE", 1, 1, EnchantmentTarget.WEARABLE);
            case "SHARPNESS":
                return () -> new MockEnchantment("SHARPNESS", 1, 5, EnchantmentTarget.WEAPON);
            case "SMITE":
                return () -> new MockEnchantment("SMITE", 1, 5, EnchantmentTarget.WEAPON);
            case "BANE_OF_ARTHROPODS":
                return () -> new MockEnchantment("BANE_OF_ARTHROPODS", 1, 5, EnchantmentTarget.WEAPON);
            case "KNOCKBACK":
                return () -> new MockEnchantment("KNOCKBACK", 1, 2, EnchantmentTarget.WEAPON);
            case "FIRE_ASPECT":
                return () -> new MockEnchantment("FIRE_ASPECT", 1, 2, EnchantmentTarget.WEAPON);
            case "LOOTING":
                return () -> new MockEnchantment("LOOTING", 1, 3, EnchantmentTarget.WEAPON);
            case "SWEEPING_EDGE":
                return () -> new MockEnchantment("SWEEPING_EDGE", 1, 3, EnchantmentTarget.WEAPON);
            case "EFFICIENCY":
                return () -> new MockEnchantment("EFFICIENCY", 1, 5, EnchantmentTarget.TOOL);
            case "SILK_TOUCH":
                return () -> new MockEnchantment("SILK_TOUCH", 1, 1, EnchantmentTarget.TOOL);
            case "UNBREAKING":
                return () -> new MockEnchantment("UNBREAKING", 1, 3, EnchantmentTarget.BREAKABLE);
            case "FORTUNE":
                return () -> new MockEnchantment("FORTUNE", 1, 3, EnchantmentTarget.TOOL);
            case "POWER":
                return () -> new MockEnchantment("POWER", 1, 5, EnchantmentTarget.BOW);
            case "PUNCH":
                return () -> new MockEnchantment("PUNCH", 1, 2, EnchantmentTarget.BOW);
            case "FLAME":
                return () -> new MockEnchantment("FLAME", 1, 1, EnchantmentTarget.BOW);
            case "INFINITY":
                return () -> new MockEnchantment("INFINITY", 1, 1, EnchantmentTarget.BOW);
            case "LUCK_OF_THE_SEA":
                return () -> new MockEnchantment("LUCK_OF_THE_SEA", 1, 3, EnchantmentTarget.FISHING_ROD);
            case "LURE":
                return () -> new MockEnchantment("LURE", 1, 3, EnchantmentTarget.FISHING_ROD);
            case "LOYALTY":
                return () -> new MockEnchantment("LOYALTY", 1, 3, EnchantmentTarget.TRIDENT);
            case "IMPALING":
                return () -> new MockEnchantment("IMPALING", 1, 5, EnchantmentTarget.TRIDENT);
            case "RIPTIDE":
                return () -> new MockEnchantment("RIPTIDE", 1, 3, EnchantmentTarget.TRIDENT);
            case "CHANNELING":
                return () -> new MockEnchantment("CHANNELING", 1, 1, EnchantmentTarget.TRIDENT);
            case "MULTISHOT":
                return () -> new MockEnchantment("MULTISHOT", 1, 1, EnchantmentTarget.CROSSBOW);
            case "QUICK_CHARGE":
                return () -> new MockEnchantment("QUICK_CHARGE", 1, 3, EnchantmentTarget.CROSSBOW);
            case "PIERCING":
                return () -> new MockEnchantment("PIERCING", 1, 4, EnchantmentTarget.CROSSBOW);
            case "MENDING":
                return () -> new MockEnchantment("MENDING", 1, 1, EnchantmentTarget.BREAKABLE);
            case "VANISHING_CURSE":
                return () -> new MockEnchantment("VANISHING_CURSE", 1, 1, EnchantmentTarget.VANISHABLE);
            case "SOUL_SPEED":
                return () -> new MockEnchantment("SOUL_SPEED", 1, 3, EnchantmentTarget.ARMOR_FEET);
            case "SWIFT_SNEAK":
                return () -> new MockEnchantment("SWIFT_SNEAK", 1, 3, EnchantmentTarget.ARMOR_FEET);
            case "DENSITY":
                return () -> new MockEnchantment("DENSITY", 1, 5, EnchantmentTarget.WEAPON);
            case "WIND_BURST":
                return () -> new MockEnchantment("WIND_BURST", 1, 3, EnchantmentTarget.WEAPON);
            case "BREACH":
                return () -> new MockEnchantment("BREACH", 1, 4, EnchantmentTarget.WEAPON);
            default:
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
