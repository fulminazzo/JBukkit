package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private final Set<ItemStack> canEnchantItems;
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
        this.canEnchantItems = new HashSet<>();
    }

    public @NotNull MockEnchantment setTreasure(boolean treasure) {
        this.treasure = treasure;
        return this;
    }

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

    /**
     * Explicitly declare the items which this enchantment can be applied to.
     *
     * @param canEnchantItems the items
     * @return this enchantment
     */
    public @NotNull MockEnchantment canEnchantItems(final @NotNull ItemStack... canEnchantItems) {
        this.canEnchantItems.addAll(Arrays.asList(canEnchantItems));
        return this;
    }

    @Override
    public boolean conflictsWith(final @NotNull Enchantment other) {
        return this.conflicts.contains(other);
    }

    @Override
    public boolean canEnchantItem(final @NotNull ItemStack item) {
        return this.canEnchantItems.contains(item);
    }

    @Override
    public @NotNull String getTranslationKey() {
        throw new NotImplementedException();
    }

    /**
     * Sets up the default vanilla enchantments by checking the {@link Enchantment} static fields.
     */
    @SneakyThrows
    public static void setupEnchantments() {
        Refl<Class<Enchantment>> enchantmentClass = new Refl<>(Enchantment.class);
        Map<NamespacedKey, Enchantment> byKey = enchantmentClass.getFieldObject("byKey");
        Map<String, Enchantment> byName = enchantmentClass.getFieldObject("byName");
        byKey.clear();
        byName.clear();
        for (Field field : enchantmentClass.getStaticFields())
            if (field.getType().equals(Enchantment.class)) {
                Enchantment enchantment = (Enchantment) ReflectionUtils.setAccessibleOrThrow(field)
                        .get(Enchantment.class);
                MockEnchantment mock = new MockEnchantment(enchantment);
                field.set(Enchantment.class, mock);
                byKey.put(mock.getKey(), enchantment);
                byName.put(mock.getName(), enchantment);
            }
    }

    /**
     * Converts the given key to a vanilla {@link Enchantment}.
     * Throws {@link IllegalArgumentException} if it could not be converted.
     *
     * @param key the key
     * @return the enchantment
     */
    public static Enchantment getVanillaEnchantment(final @NotNull NamespacedKey key) {
        switch (key.getKey()) {
            case "protection":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.ARMOR);
            case "fire_protection":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.ARMOR);
            case "feather_falling":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.ARMOR_FEET);
            case "blast_protection":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.ARMOR);
            case "projectile_protection":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.ARMOR);
            case "respiration":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.ARMOR_HEAD);
            case "aqua_affinity":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.ARMOR_HEAD);
            case "thorns":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.ARMOR);
            case "depth_strider":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.ARMOR_FEET);
            case "frost_walker":
                return new MockEnchantment(key, key.getKey(), 1, 2, EnchantmentTarget.ARMOR_FEET);
            case "binding_curse":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.WEARABLE);
            case "sharpness":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.WEAPON);
            case "smite":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.WEAPON);
            case "bane_of_arthropods":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.WEAPON);
            case "knockback":
                return new MockEnchantment(key, key.getKey(), 1, 2, EnchantmentTarget.WEAPON);
            case "fire_aspect":
                return new MockEnchantment(key, key.getKey(), 1, 2, EnchantmentTarget.WEAPON);
            case "looting":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.WEAPON);
            case "sweeping_edge":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.WEAPON);
            case "efficiency":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.TOOL);
            case "silk_touch":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.TOOL);
            case "unbreaking":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.BREAKABLE);
            case "fortune":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.TOOL);
            case "power":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.BOW);
            case "flame":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.BOW);
            case "infinity":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.BOW);
            case "luck_of_the_sea":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.FISHING_ROD);
            case "lure":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.FISHING_ROD);
            case "loyalty":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.TRIDENT);
            case "impaling":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.TRIDENT);
            case "riptide":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.TRIDENT);
            case "channeling":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.TRIDENT);
            case "multishot":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.CROSSBOW);
            case "quick_charge":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.CROSSBOW);
            case "piercing":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.CROSSBOW);
            case "mending":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.BREAKABLE);
            case "vanishing_curse":
                return new MockEnchantment(key, key.getKey(), 1, 1, EnchantmentTarget.VANISHABLE);
            case "soul_speed":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.ARMOR_FEET);
            case "swift_sneak":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.ARMOR_FEET);
            case "density":
                return new MockEnchantment(key, key.getKey(), 1, 5, EnchantmentTarget.WEAPON);
            case "wind_burst":
                return new MockEnchantment(key, key.getKey(), 1, 3, EnchantmentTarget.WEAPON);
            case "breach":
                return new MockEnchantment(key, key.getKey(), 1, 4, EnchantmentTarget.WEAPON);
            default:
                throw new IllegalArgumentException("Could not create enchantment from key " + key.getKey());
        }
    }

}
