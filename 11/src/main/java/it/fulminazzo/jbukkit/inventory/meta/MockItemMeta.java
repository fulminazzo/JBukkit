package it.fulminazzo.jbukkit.inventory.meta;

import it.fulminazzo.jbukkit.Equable;
import it.fulminazzo.jbukkit.NotImplementedException;
import it.fulminazzo.yagl.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Represents a mock implementation for {@link ItemMeta}.
 */
@Getter
@Setter
public class MockItemMeta extends Equable implements ItemMeta {
    private String displayName;
    private String localizedName;
    private final List<String> lore;
    private final Map<Enchantment, Integer> enchants;
    private final Set<ItemFlag> itemFlags;
    private boolean unbreakable;
    @Getter(AccessLevel.NONE)
    private final Spigot spigot;

    /**
     * Instantiates a new Mock item meta.
     */
    public MockItemMeta() {
        this.lore = new LinkedList<>();
        this.enchants = new HashMap<>();
        this.itemFlags = new HashSet<>();
        this.spigot = new MockSpigot();
    }

    @Override
    public boolean hasDisplayName() {
        return this.displayName != null && !this.displayName.isEmpty();
    }

    @Override
    public boolean hasLocalizedName() {
        return this.localizedName != null && !this.localizedName.isEmpty();
    }

    @Override
    public boolean hasLore() {
        return !this.lore.isEmpty();
    }

    @Override
    public void setLore(List<String> lore) {
        this.lore.clear();
        if (lore != null)
            this.lore.addAll(lore);
    }

    @Override
    public boolean hasEnchants() {
        return !this.enchants.isEmpty();
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        return this.enchants.containsKey(ench);
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        return this.enchants.getOrDefault(ench, 0);
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        if (!ignoreLevelRestriction && ench.getMaxLevel() < level) return false;
        this.enchants.put(ench, level);
        return true;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        return this.enchants.remove(ench) != null;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        return this.enchants.keySet().stream().anyMatch(e -> e.conflictsWith(ench));
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags.addAll(Arrays.asList(itemFlags));
    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {
        Arrays.asList(itemFlags).forEach(this.itemFlags::remove);
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        return this.itemFlags.contains(flag);
    }

    @Override
    public ItemMeta clone() {
        return ObjectUtils.copy(this, getClass());
    }

    @Override
    public Spigot spigot() {
        return this.spigot;
    }

    @Override
    public Map<String, Object> serialize() {
        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Object object) {
        return super.equalsNull(object);
    }

    @NoArgsConstructor
    private class MockSpigot extends Spigot {

        @Override
        public void setUnbreakable(boolean unbreakable) {
            MockItemMeta.this.unbreakable = unbreakable;
        }

        @Override
        public boolean isUnbreakable() {
            return MockItemMeta.this.unbreakable;
        }

        @Override
        public boolean equals(Object o) {
            return Equable.equals(this, o);
        }

    }

}
