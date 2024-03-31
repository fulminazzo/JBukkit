package it.fulminazzo.jbukkit.inventory.meta;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.fulminazzo.jbukkit.inventory.meta.tags.MockCustomItemTagContainer;
import it.fulminazzo.jbukkit.persistence.MockPersistentDataContainer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class MockItemMeta implements ItemMeta {
    private String displayName;
    private String localizedName;
    private List<String> lore;
    private Integer customModelData;
    private final Map<Enchantment, Integer> enchants = new HashMap<>();
    private final Set<ItemFlag> itemFlags = new HashSet<>();
    private Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
    private boolean unbreakable;
    private final PersistentDataContainer persistentDataContainer = new MockPersistentDataContainer();
    private final CustomItemTagContainer customTagContainer = new MockCustomItemTagContainer();
    private int version;

    public int getCustomModelData() {
        return this.customModelData;
    }

    @Override
    public boolean hasDisplayName() {
        return this.displayName != null;
    }

    @Override
    public boolean hasLocalizedName() {
        return this.localizedName != null;
    }

    @Override
    public boolean hasLore() {
        return this.localizedName != null;
    }

    @Override
    public boolean hasCustomModelData() {
        return this.customModelData != null;
    }

    @Override
    public boolean hasEnchants() {
        return !this.enchants.isEmpty();
    }

    @Override
    public boolean hasEnchant(@NotNull Enchantment ench) {
        return this.enchants.containsKey(ench);
    }

    @Override
    public int getEnchantLevel(@NotNull Enchantment ench) {
        return this.enchants.getOrDefault(ench, 0);
    }

    @Override
    public boolean addEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction) {
        int max = ench.getMaxLevel();
        if (level <= max || ignoreLevelRestriction) {
            this.enchants.put(ench, level);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEnchant(@NotNull Enchantment ench) {
        if (hasEnchant(ench)) {
            this.enchants.remove(ench);
            return true;
        }
        return false;
    }

    @Override
    public void removeEnchantments() {
        this.enchants.clear();
    }

    @Override
    public boolean hasConflictingEnchant(@NotNull Enchantment ench) {
        return this.enchants.keySet().stream().anyMatch(e -> e.conflictsWith(ench));
    }

    @Override
    public void addItemFlags(@NotNull ItemFlag... itemFlags) {
        this.itemFlags.addAll(Arrays.asList(itemFlags));
    }

    @Override
    public void removeItemFlags(@NotNull ItemFlag... itemFlags) {
        for (ItemFlag i : itemFlags) this.itemFlags.remove(i);
    }

    @Override
    public boolean hasItemFlag(@NotNull ItemFlag flag) {
        return this.itemFlags.contains(flag);
    }

    @Override
    public boolean hasAttributeModifiers() {
        return this.attributeModifiers.isEmpty();
    }

    @NotNull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        this.attributeModifiers.asMap().forEach((k, v) -> modifiers.putAll(k, v.stream().filter(m -> m.getSlot() == slot).collect(Collectors.toList())));
        return modifiers;
    }

    @Nullable
    @Override
    public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        return this.attributeModifiers.asMap().get(attribute);
    }

    @Override
    public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return this.attributeModifiers.put(attribute, modifier);
    }

    @Override
    public boolean removeAttributeModifier(@NotNull Attribute attribute) {
        return !this.attributeModifiers.removeAll(attribute).isEmpty();
    }

    @Override
    public boolean removeAttributeModifier(@NotNull EquipmentSlot slot) {
        this.attributeModifiers.asMap().forEach((a, c) -> c.removeIf(m -> m.getSlot() == slot));
        return true;
    }

    @Override
    public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return this.attributeModifiers.remove(attribute, modifier);
    }

    @NotNull
    @Override
    public String getAsString() {
        return "Unimplemented";
    }

    @NotNull
    @Override
    public ItemMeta clone() {
        try {
            return (ItemMeta) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }
}
