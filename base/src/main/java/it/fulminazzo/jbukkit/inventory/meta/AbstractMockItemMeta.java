package it.fulminazzo.jbukkit.inventory.meta;

import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Getter
@Setter
abstract class AbstractMockItemMeta extends Equable {
    protected String displayName = "";
    protected String localizedName;
    protected List<String> lore = new LinkedList<>();
    protected Integer customModelData;
    protected final Map<Enchantment, Integer> enchants = new HashMap<>();
    protected final Set<ItemFlag> itemFlags = new HashSet<>();
    protected boolean unbreakable;
    protected int version;

    public int getCustomModelData() {
        return this.customModelData == null ? 0 : this.customModelData;
    }

    public boolean hasDisplayName() {
        return this.displayName != null;
    }

    public boolean hasLocalizedName() {
        return this.localizedName != null;
    }

    public boolean hasLore() {
        return this.localizedName != null;
    }

    public boolean hasCustomModelData() {
        return this.customModelData != null;
    }

    public boolean hasEnchants() {
        return !this.enchants.isEmpty();
    }

    public boolean hasEnchant(@NotNull Enchantment ench) {
        return this.enchants.containsKey(ench);
    }

    public int getEnchantLevel(@NotNull Enchantment ench) {
        return this.enchants.getOrDefault(ench, 0);
    }

    public boolean addEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction) {
        int max = ench.getMaxLevel();
        if (level <= max || ignoreLevelRestriction) {
            this.enchants.put(ench, level);
            return true;
        }
        return false;
    }

    public boolean removeEnchant(@NotNull Enchantment ench) {
        if (hasEnchant(ench)) {
            this.enchants.remove(ench);
            return true;
        }
        return false;
    }

    public void removeEnchantments() {
        this.enchants.clear();
    }

    public boolean hasConflictingEnchant(@NotNull Enchantment ench) {
        return this.enchants.keySet().stream().anyMatch(e -> e.conflictsWith(ench));
    }

    public void addItemFlags(@NotNull ItemFlag... itemFlags) {
        this.itemFlags.addAll(Arrays.asList(itemFlags));
    }

    public void removeItemFlags(@NotNull ItemFlag... itemFlags) {
        for (ItemFlag i : itemFlags) this.itemFlags.remove(i);
    }

    public boolean hasItemFlag(@NotNull ItemFlag flag) {
        return this.itemFlags.contains(flag);
    }

    @NotNull
    public String getAsString() {
        return "Unimplemented";
    }

    @NotNull
    public ItemMeta clone() {
        try {
            return (ItemMeta) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }
}
