package it.fulminazzo.jbukkit.inventory.meta;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.fulminazzo.jbukkit.Equable;
import it.fulminazzo.jbukkit.NotImplementedException;
import it.fulminazzo.jbukkit.inventory.meta.tags.MockCustomItemTagContainer;
import it.fulminazzo.jbukkit.persistence.MockPersistentDataContainer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.*;
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent;
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
public class MockItemMeta extends Equable implements ItemMeta, Damageable {
    private String displayName;
    private String localizedName;
    private String itemName;
    private final List<String> lore;
    private final Map<Enchantment, Integer> enchants;
    private final Set<ItemFlag> itemFlags;
    private boolean unbreakable;
    private int damage;
    private boolean hideTooltip;
    private boolean fireResistant;
    private boolean glider;
    private Boolean enchantmentGlintOverride;
    private Integer enchantable;
    private Integer maxDamage;
    private Integer maxStackSize;
    private Integer customModelData;
    private ItemRarity rarity;
    private NamespacedKey itemModel;
    private NamespacedKey tooltipStyle;
    private ItemStack useRemainder;
    private Tag<DamageType> damageResistant;
    private FoodComponent food;
    private ToolComponent tool;
    private UseCooldownComponent useCooldown;
    private EquippableComponent equippable;
    private JukeboxPlayableComponent jukeboxPlayable;
    private CustomModelDataComponent customModelDataComponent;
    private ConsumableComponent consumable;
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
    public void setDisplayName(@Nullable String displayName) {
        this.displayName = displayName != null && displayName.isEmpty() ? null : displayName;
    }

    @Override
    public @NotNull String getDisplayName() {
        return this.displayName == null ? "" : this.displayName;
    }

    @Override
    public boolean hasDamage() {
        return this.damage > 0;
    }

    @Override
    public boolean hasMaxDamage() {
        return this.maxDamage != null && this.maxDamage > 0;
    }

    public int getMaxDamage() {
        return this.maxDamage == null ? 0 : this.maxDamage;
    }

    @Override
    public boolean hasDisplayName() {
        return this.displayName != null && !this.displayName.isEmpty();
    }

    @Override
    public boolean hasItemName() {
        return this.itemName != null && !this.itemName.isEmpty();
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
    public boolean hasCustomModelData() {
        return this.customModelData != null;
    }

    @Override
    public int getCustomModelData() {
        return hasCustomModelData() ? this.customModelData : 0;
    }

    @Override
    public boolean hasEnchantable() {
        return this.enchantable != null && this.enchantable != 0;
    }

    public int getEnchantable() {
        return this.enchantable == null ? 0 : this.enchantable;
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
    public void removeEnchantments() {
        this.enchants.clear();
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
    public boolean hasTooltipStyle() {
        return this.tooltipStyle != null;
    }

    @Override
    public boolean hasItemModel() {
        return this.itemModel != null;
    }

    @Override
    public boolean hasEnchantmentGlintOverride() {
        return this.enchantmentGlintOverride != null;
    }

    @Override
    public boolean hasDamageResistant() {
        return this.damageResistant != null;
    }

    @Override
    public boolean hasMaxStackSize() {
        return this.maxStackSize != null;
    }

    public int getMaxStackSize() {
        return this.maxStackSize == null ? 0 : this.maxStackSize;
    }

    @Override
    public boolean hasRarity() {
        return this.rarity != null;
    }

    @Override
    public boolean hasUseRemainder() {
        return this.useRemainder != null;
    }

    @Override
    public boolean hasUseCooldown() {
        return this.useCooldown != null;
    }

    @Override
    public boolean hasFood() {
        return this.food != null;
    }

    @Override
    public boolean hasConsumable() {
        return this.consumable != null;
    }

    @Override
    public boolean hasTool() {
        return this.tool != null;
    }

    @Override
    public boolean hasEquippable() {
        return this.equippable != null;
    }

    @Override
    public boolean hasJukeboxPlayable() {
        return this.jukeboxPlayable != null;
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
        if (attributeModifiers != null)
            this.attributeModifiers.putAll(attributeModifiers);
    }

    @Override
    public boolean removeAttributeModifier(@NotNull Attribute attribute) {
        return !this.attributeModifiers.removeAll(attribute).isEmpty();
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
    public @NotNull String getAsString() {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull String getAsComponentString() {
        throw new NotImplementedException();
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
    public @NotNull MockItemMeta clone() {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        throw new NotImplementedException();
    }

    @Override
    public boolean equals(Object object) {
        return super.equalsNull(object);
    }

}
