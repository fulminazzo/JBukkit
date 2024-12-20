package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Refl;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Represents an implementation of {@link Enchantment}.
 */
@Getter
public class MockEnchantment extends Enchantment {
    private static int LAST_USED_ID = 0;

    private static final MockEnchantment PROTECTION_ENVIRONMENTAL = new MockEnchantment("PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final MockEnchantment PROTECTION_FIRE = new MockEnchantment("FIRE_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final MockEnchantment PROTECTION_EXPLOSIONS = new MockEnchantment("BLAST_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final MockEnchantment PROTECTION_PROJECTILE = new MockEnchantment("PROJECTILE_PROTECTION", 1, 4, EnchantmentTarget.ARMOR);
    private static final MockEnchantment PROTECTION_FALL = new MockEnchantment("FEATHER_FALLING", 1, 4, EnchantmentTarget.ARMOR_FEET);
    private static final MockEnchantment OXYGEN = new MockEnchantment("RESPIRATION", 1, 3, EnchantmentTarget.ARMOR_HEAD);
    private static final MockEnchantment WATER_WORKER = new MockEnchantment("AQUA_AFFINITY", 1, 1, EnchantmentTarget.ARMOR_HEAD);
    private static final MockEnchantment THORNS = new MockEnchantment("THORNS", 1, 3, EnchantmentTarget.ARMOR);
    private static final MockEnchantment DEPTH_STRIDER = new MockEnchantment("DEPTH_STRIDER", 1, 3, EnchantmentTarget.ARMOR_FEET);
    private static final MockEnchantment FROST_WALKER = new MockEnchantment(lastUsedId(16), "FROST_WALKER", 1, 2, EnchantmentTarget.ARMOR_FEET);
    private static final MockEnchantment DAMAGE_ALL = new MockEnchantment("SHARPNESS", 1, 5, EnchantmentTarget.WEAPON);
    private static final MockEnchantment DAMAGE_UNDEAD = new MockEnchantment("SMITE", 1, 5, EnchantmentTarget.WEAPON);
    private static final MockEnchantment DAMAGE_ARTHROPODS = new MockEnchantment("BANE_OF_ARTHROPODS", 1, 5, EnchantmentTarget.WEAPON);
    private static final MockEnchantment KNOCKBACK = new MockEnchantment("KNOCKBACK", 1, 2, EnchantmentTarget.WEAPON);
    private static final MockEnchantment FIRE_ASPECT = new MockEnchantment("FIRE_ASPECT", 1, 2, EnchantmentTarget.WEAPON);
    private static final MockEnchantment LOOT_BONUS_MOBS = new MockEnchantment("LOOTING", 1, 3, EnchantmentTarget.WEAPON);
    private static final MockEnchantment SWEEPING_EDGE = new MockEnchantment(lastUsedId(32), "SWEEPING", 1, 3, EnchantmentTarget.WEAPON);
    private static final MockEnchantment DIG_SPEED = new MockEnchantment("EFFICIENCY", 1, 5, EnchantmentTarget.TOOL);
    private static final MockEnchantment SILK_TOUCH = new MockEnchantment("SILK_TOUCH", 1, 1, EnchantmentTarget.TOOL);
    private static final MockEnchantment DURABILITY = new MockEnchantment("UNBREAKING", 1, 3, EnchantmentTarget.ALL);
    private static final MockEnchantment LOOT_BONUS_BLOCKS = new MockEnchantment(lastUsedId(48), "FORTUNE", 1, 3, EnchantmentTarget.TOOL);
    private static final MockEnchantment ARROW_DAMAGE = new MockEnchantment("POWER", 1, 5, EnchantmentTarget.BOW);
    private static final MockEnchantment ARROW_KNOCKBACK = new MockEnchantment("PUNCH", 1, 2, EnchantmentTarget.BOW);
    private static final MockEnchantment ARROW_FIRE = new MockEnchantment("FLAME", 1, 1, EnchantmentTarget.BOW);
    private static final MockEnchantment ARROW_INFINITE = new MockEnchantment(lastUsedId(61), "INFINITY", 1, 1, EnchantmentTarget.BOW);
    private static final MockEnchantment LUCK = new MockEnchantment("LUCK_OF_THE_SEA", 1, 3, EnchantmentTarget.FISHING_ROD);
    private static final MockEnchantment LURE = new MockEnchantment(lastUsedId(70), "LURE", 1, 3, EnchantmentTarget.FISHING_ROD);
    private static final MockEnchantment MENDING = new MockEnchantment("MENDING", 1, 1, EnchantmentTarget.ALL);

    private static int lastUsedId(final int id) {
        int prev = LAST_USED_ID;
        LAST_USED_ID = id;
        return prev;
    }

    private final int id;
    private final String name;
    private final int startLevel;
    private final int maxLevel;
    private final EnchantmentTarget itemTarget;
    private final Set<Enchantment> conflicts;
    private boolean treasure;

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
        this(LAST_USED_ID++, name, startLevel, maxLevel, itemTarget);
    }

    /**
     * Instantiates a new Mock enchantment.
     *
     * @param id         the id
     * @param name       the name
     * @param startLevel the start level
     * @param maxLevel   the max level
     * @param itemTarget the item target
     */
    public MockEnchantment(final int id, final @NotNull String name, final int startLevel,
                           final int maxLevel, final @NotNull EnchantmentTarget itemTarget) {
        super(id);
        // If the provided key belongs to a static supplier in this class
        // And the corresponding Enchantment is already initialized,
        // Throw exception.
        if (getByIdMap().containsKey(id) || getByNameMap().containsKey(name))
            throw new IllegalArgumentException(String.format("Cannot create enchantment with name \"%s\". ", name) +
                    "Use the vanilla enchantment from the Enchantment class instead.");
        this.id = id;
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

    private static @NotNull Map<String, Enchantment> getByNameMap() {
        return Objects.requireNonNull(new Refl<>(Enchantment.class).getFieldObject("byName"), "Could not get byName map");
    }

    private static @NotNull Map<Integer, Enchantment> getByIdMap() {
        return Objects.requireNonNull(new Refl<>(Enchantment.class).getFieldObject("byId"), "Could not get byId map");
    }

    /**
     * Sets up the default vanilla enchantments by checking the {@link Enchantment} static fields.
     */
    @SneakyThrows
    public static void setupEnchantments() {
        Map<Integer, Enchantment> byKey = getByIdMap();
        Map<String, Enchantment> byName = getByNameMap();
        byKey.clear();
        byName.clear();
        Refl<Class<MockEnchantment>> mockEnchantmentClass = new Refl<>(MockEnchantment.class);
        for (Field field : mockEnchantmentClass.getFields(f -> Modifier.isStatic(f.getModifiers()) &&
                f.getType().equals(MockEnchantment.class))) {
            MockEnchantment enchantment = mockEnchantmentClass.getFieldObject(field);
            byKey.put(enchantment.getId(), enchantment);
            byName.put(enchantment.getName(), enchantment);
        }
        // Protection
        MockEnchantment.PROTECTION_ENVIRONMENTAL.conflictsWith(MockEnchantment.PROTECTION_FIRE, MockEnchantment.PROTECTION_EXPLOSIONS, MockEnchantment.PROTECTION_PROJECTILE);
        MockEnchantment.PROTECTION_FIRE.conflictsWith(MockEnchantment.PROTECTION_ENVIRONMENTAL, MockEnchantment.PROTECTION_EXPLOSIONS, MockEnchantment.PROTECTION_PROJECTILE);
        MockEnchantment.PROTECTION_EXPLOSIONS.conflictsWith(MockEnchantment.PROTECTION_FIRE, MockEnchantment.PROTECTION_ENVIRONMENTAL, MockEnchantment.PROTECTION_PROJECTILE);
        MockEnchantment.PROTECTION_PROJECTILE.conflictsWith(MockEnchantment.PROTECTION_FIRE, MockEnchantment.PROTECTION_EXPLOSIONS, MockEnchantment.PROTECTION_ENVIRONMENTAL);
        // Depth strider
        MockEnchantment.DEPTH_STRIDER.conflictsWith(MockEnchantment.FROST_WALKER);
        MockEnchantment.FROST_WALKER.conflictsWith(MockEnchantment.DEPTH_STRIDER);
        // Sharpness
        MockEnchantment.DAMAGE_ALL.conflictsWith(MockEnchantment.DAMAGE_UNDEAD, MockEnchantment.DAMAGE_ARTHROPODS);
        MockEnchantment.DAMAGE_UNDEAD.conflictsWith(MockEnchantment.DAMAGE_ALL, MockEnchantment.DAMAGE_ARTHROPODS);
        MockEnchantment.DAMAGE_ARTHROPODS.conflictsWith(MockEnchantment.DAMAGE_UNDEAD, MockEnchantment.DAMAGE_ALL);
        // Fortune
        MockEnchantment.LOOT_BONUS_BLOCKS.conflictsWith(MockEnchantment.SILK_TOUCH);
        MockEnchantment.SILK_TOUCH.conflictsWith(MockEnchantment.LOOT_BONUS_BLOCKS);
        // Mending
        MockEnchantment.MENDING.conflictsWith(MockEnchantment.ARROW_INFINITE);
        MockEnchantment.ARROW_INFINITE.conflictsWith(MockEnchantment.MENDING);
        // Treasure
        MockEnchantment.MENDING.setTreasure(true);
        MockEnchantment.FROST_WALKER.setTreasure(true);
    }

}
