package it.fulminazzo.jbukkit.enchantments;

import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
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

}
