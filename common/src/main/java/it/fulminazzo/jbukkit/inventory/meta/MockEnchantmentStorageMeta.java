package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MockEnchantmentStorageMeta extends MockItemMeta implements EnchantmentStorageMeta {
    private final Map<Enchantment, Integer> storedEnchants = new HashMap<>();

    public boolean hasStoredEnchants() {
        return !this.storedEnchants.isEmpty();
    }

    public boolean hasStoredEnchant(@NotNull Enchantment ench) {
        return this.storedEnchants.containsKey(ench);
    }

    public int getStoredEnchantLevel(@NotNull Enchantment ench) {
        return this.storedEnchants.getOrDefault(ench, 0);
    }

    public boolean addStoredEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction) {
        int max = ench.getMaxLevel();
        if (level <= max || ignoreLevelRestriction) {
            this.storedEnchants.put(ench, level);
            return true;
        }
        return false;
    }

    public boolean removeStoredEnchant(@NotNull Enchantment ench) throws IllegalArgumentException {
        return this.storedEnchants.remove(ench) != null;
    }

    public boolean hasConflictingStoredEnchant(@NotNull Enchantment ench) {
        return this.storedEnchants.keySet().stream().anyMatch(e -> e.conflictsWith(ench));
    }

    public @NotNull MockEnchantmentStorageMeta clone() {
        return (MockEnchantmentStorageMeta) super.clone();
    }
}
