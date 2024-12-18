package it.fulminazzo.jbukkit.inventory.meta;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.fulminazzo.jbukkit.NotImplementedException;
import it.fulminazzo.jbukkit.inventory.meta.tags.MockCustomItemTagContainer;
import it.fulminazzo.jbukkit.persistence.MockPersistentDataContainer;
import it.fulminazzo.yagl.utils.ObjectUtils;
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

/**
 * Represents a mock implementation for {@link ItemMeta}.
 */
@Getter
@Setter
public class MockItemMeta implements ItemMeta {
    private String displayName;
    private String localizedName;
    private final List<String> lore;
    private final Map<Enchantment, Integer> enchants;
    private final Set<ItemFlag> itemFlags;
    private boolean unbreakable;
    private Integer customModelData;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final MockCustomItemTagContainer customTagContainer;
    private final PersistentDataContainer persistentDataContainer;

    /**
     * Instantiates a new Mock item meta.
     */
    public MockItemMeta() {
        this.lore = new LinkedList<>();
        this.enchants = new HashMap<>();
        this.itemFlags = new HashSet<>();
        this.attributeModifiers = HashMultimap.create();
        this.customTagContainer = new MockCustomItemTagContainer();
        this.persistentDataContainer = new MockPersistentDataContainer();
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
        this.lore.addAll(lore);
    }

    @Override
    public boolean hasCustomModelData() {
        return this.customModelData != null;
    }

    @Override
    public int getCustomModelData() {
        return hasCustomModelData() ? this.customModelData : 0;
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
        if (!ignoreLevelRestriction && ench.getMaxLevel() < level) return false;
        this.enchants.put(ench, level);
        return true;
    }

    @Override
    public boolean removeEnchant(@NotNull Enchantment ench) {
        return this.enchants.remove(ench) != null;
    }

    @Override
    public boolean hasConflictingEnchant(@NotNull Enchantment ench) {
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
    public boolean hasItemFlag(@NotNull ItemFlag flag) {
        return this.itemFlags.contains(flag);
    }

    @Override
    public boolean hasAttributeModifiers() {
        return !this.attributeModifiers.isEmpty();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> result = HashMultimap.create();
        for (Attribute attribute : this.attributeModifiers.keySet())
            for (AttributeModifier modifier : this.attributeModifiers.get(attribute))
                if (modifier.getSlot() == slot)
                    result.put(attribute, modifier);
        return result;
    }

    @Override
    public @Nullable Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        return this.attributeModifiers.get(attribute);
    }

    @Override
    public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return this.attributeModifiers.put(attribute, modifier);
    }

    @Override
    public void setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers) {
        this.attributeModifiers.clear();
        this.attributeModifiers.putAll(attributeModifiers);
    }

    @Override
    public boolean removeAttributeModifier(@NotNull Attribute attribute) {
        return this.attributeModifiers.removeAll(attribute) != null;
    }

    @Override
    public boolean removeAttributeModifier(@NotNull EquipmentSlot slot) {
        boolean removed = false;
        for (Attribute attribute : new ArrayList<>(this.attributeModifiers.keySet()))
            for (AttributeModifier modifier : new ArrayList<>(this.attributeModifiers.get(attribute)))
                if (modifier.getSlot() == slot) {
                    removeAttributeModifier(attribute, modifier);
                    removed = true;
                }
        return removed;
    }

    @Override
    public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return this.attributeModifiers.remove(attribute, modifier);
    }

    @Override
    public @NotNull CustomItemTagContainer getCustomTagContainer() {
        return this.customTagContainer;
    }

    @Override
    public void setVersion(int version) {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull ItemMeta clone() {
        return ObjectUtils.copy(this, getClass());
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        throw new NotImplementedException();
    }

}
