package it.fulminazzo.jbukkit.inventory.meta;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.fulminazzo.jbukkit.Equable;
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
public class MockItemMeta extends AbstractMockItemMeta implements ItemMeta {
    private Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
    private final PersistentDataContainer persistentDataContainer = new MockPersistentDataContainer();
    private final CustomItemTagContainer customTagContainer = new MockCustomItemTagContainer();

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
}
