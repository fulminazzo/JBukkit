package it.fulminazzo.jbukkit.inventory.meta;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.fulminazzo.jbukkit.inventory.meta.tags.MockCustomItemTagContainer;
import it.fulminazzo.jbukkit.persistence.MockPersistentDataContainer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.*;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class MockItemMeta extends AbstractMockItemMeta implements ItemMeta {
    private Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
    private final PersistentDataContainer persistentDataContainer = new MockPersistentDataContainer();
    private final CustomItemTagContainer customTagContainer = new MockCustomItemTagContainer();
    // From 1.21
    private String itemName;
    private Integer maxStackSize;
    private Integer enchantable;
    private boolean hideTooltip;
    private NamespacedKey tooltipStyle;
    private NamespacedKey itemModel;
    private Boolean enchantmentGlintOverride;
    private boolean glider;
    private boolean isFireResistant;
    private Tag<DamageType> damageResistant;
    private ItemRarity rarity;
    private ItemStack useRemainder;
    private CustomModelDataComponent customModelDataComponent;
    private UseCooldownComponent useCooldown;
    private FoodComponent food;
    private ToolComponent tool;
    private EquippableComponent equippable;
    private JukeboxPlayableComponent jukeboxPlayable;

    public int getMaxStackSize() {
        return this.maxStackSize;
    }

    @Override
    public boolean hasMaxStackSize() {
        return this.maxStackSize != null;
    }

    @Override
    public void setMaxStackSize(@Nullable Integer max) {
        this.maxStackSize = max;
    }

    @Override
    public boolean hasItemName() {
        return this.itemName != null;
    }

    @Override
    public boolean hasEnchantable() {
        return this.enchantable != null;
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
    public boolean hasDamageResistant() {
        return this.damageResistant != null;
    }

    @Override
    public boolean hasEnchantmentGlintOverride() {
        return this.enchantmentGlintOverride != null;
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

    public boolean hasAttributeModifiers() {
        return this.attributeModifiers.isEmpty();
    }

    @NotNull
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();
        this.attributeModifiers.asMap().forEach((k, v) -> modifiers.putAll(k, v.stream().filter(m -> m.getSlot() == slot).collect(Collectors.toList())));
        return modifiers;
    }

    @Nullable
    public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        return this.attributeModifiers.asMap().get(attribute);
    }

    public boolean addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return this.attributeModifiers.put(attribute, modifier);
    }

    public boolean removeAttributeModifier(@NotNull Attribute attribute) {
        return !this.attributeModifiers.removeAll(attribute).isEmpty();
    }

    public boolean removeAttributeModifier(@NotNull EquipmentSlot slot) {
        this.attributeModifiers.asMap().forEach((a, c) -> c.removeIf(m -> m.getSlot() == slot));
        return true;
    }

    public boolean removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return this.attributeModifiers.remove(attribute, modifier);
    }

    @Override
    public @NotNull String getAsComponentString() {
        return getAsString();
    }
}
