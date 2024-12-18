package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a mock implementation for {@link EnchantmentStorageMeta}.
 */
@Getter
@Setter
public class MockEnchantmentStorageMeta extends MockItemMeta implements EnchantmentStorageMeta {
    private final Map<Enchantment, Integer> storedEnchants;

    /**
     * Instantiates a new Mock enchantment storage meta.
     */
    public MockEnchantmentStorageMeta() {
        this.storedEnchants = new HashMap<>();
    }

    @Override
    public boolean hasStoredEnchants() {
        return !storedEnchants.isEmpty();
    }

    @Override
    public boolean hasStoredEnchant(Enchantment ench) {
        return this.storedEnchants.containsKey(ench);
    }

    @Override
    public int getStoredEnchantLevel(Enchantment ench) {
        return this.storedEnchants.get(ench);
    }

    @Override
    public boolean addStoredEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        if (!ignoreLevelRestriction && ench.getMaxLevel() < level) return false;
        this.storedEnchants.put(ench, level);
        return true;
    }

    @Override
    public boolean removeStoredEnchant(Enchantment ench) throws IllegalArgumentException {
        return this.storedEnchants.remove(ench) != null;
    }

    @Override
    public boolean hasConflictingStoredEnchant(Enchantment ench) {
        return this.storedEnchants.keySet().stream().anyMatch(e -> e.conflictsWith(ench));
    }

    @Override
    public MockEnchantmentStorageMeta clone() {
        return (MockEnchantmentStorageMeta) super.clone();
    }

}
