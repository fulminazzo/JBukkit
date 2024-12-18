package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class MockEnchantment extends Enchantment {
    private final int id;
    private final String name;
    private final int startLevel;
    private final int maxLevel;
    private final EnchantmentTarget itemTarget;
    private final Set<Enchantment> conflicts;
    private final Set<ItemStack> canEnchantItems;
    private boolean treasure;

    public MockEnchantment(Enchantment enchantment) {
        this(enchantment.getId(), enchantment.getName(), enchantment.getStartLevel(),
                enchantment.getMaxLevel(), enchantment.getItemTarget());
    }

    public MockEnchantment(final int id, final @NotNull String name, final int startLevel,
                           final int maxLevel, final @NotNull EnchantmentTarget itemTarget) {
        super(id);
        this.id = id;
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

    public @NotNull MockEnchantment conflictsWith(final Enchantment @NotNull ... conflicts) {
        this.conflicts.addAll(Arrays.asList(conflicts));
        return this;
    }

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

    @SneakyThrows
    public static void setupEnchantments() {
        Refl<Class<Enchantment>> enchantmentClass = new Refl<>(Enchantment.class);
        Map<Integer, Enchantment> byId = enchantmentClass.getFieldObject("byId");
        Map<String, Enchantment> byName = enchantmentClass.getFieldObject("byName");
        byId.clear();
        byName.clear();
        for (Field field : enchantmentClass.getStaticFields())
            if (field.getType().equals(Enchantment.class)) {
                Enchantment enchantment = (Enchantment) ReflectionUtils.setAccessibleOrThrow(field)
                        .get(Enchantment.class);
                MockEnchantment mock = new MockEnchantment(enchantment);
                field.set(Enchantment.class, mock);
                byId.put(mock.getId(), enchantment);
                byName.put(mock.getName(), enchantment);
            }
    }

}
