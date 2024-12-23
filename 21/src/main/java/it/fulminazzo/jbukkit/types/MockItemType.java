package it.fulminazzo.jbukkit.types;

import com.google.common.collect.Multimap;
import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Represents a mock for {@link ItemType}.
 */
@SuppressWarnings("UnstableApiUsage")
@Getter
@Setter
public class MockItemType implements ItemType {
    private final NamespacedKey key;
    private Class<? extends ItemMeta> itemMetaClass;
    private int maxStackSize;
    private short maxDurability;
    private boolean edible;
    private boolean record;
    private boolean fuel;
    private boolean compostable;
    private float compostChance;
    private CreativeCategory creativeCategory;

    /**
     * Instantiates a new Mock item type.
     *
     * @param key the key
     */
    public MockItemType(final @NotNull NamespacedKey key) {
        this.key = key;
    }

    @Override
    public @NotNull Typed<ItemMeta> typed() {
        return typed(ItemMeta.class);
    }

    @Override
    public @NotNull <M extends ItemMeta> Typed<M> typed(@NotNull Class<M> itemMetaType) {
        MockTyped<M> mockTyped = new MockTyped<>(this.key);
        mockTyped.setItemMetaClass(itemMetaClass);
        return mockTyped;
    }

    @Override
    public @NotNull ItemStack createItemStack() {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull ItemStack createItemStack(int amount) {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasBlockType() {
        return true;
    }

    @Override
    public @NotNull BlockType getBlockType() {
        return new MockBlockType(this.key);
    }

    @Override
    public @Nullable ItemType getCraftingRemainingItem() {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isEnabledByFeature(@NotNull World world) {
        throw new NotImplementedException();
    }

    @Override
    public @Nullable Material asMaterial() {
        try {
            return Material.valueOf(this.key.getKey().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public @NotNull String getTranslationKey() {
        throw new NotImplementedException();
    }

    /**
     * Represents a mock for {@link org.bukkit.inventory.ItemType.Typed}.
     *
     * @param <M> the type parameter
     */
    public static class MockTyped<M extends ItemMeta> extends MockItemType implements Typed<M> {

        /**
         * Instantiates a new Mock typed.
         *
         * @param key the key
         */
        public MockTyped(final @NotNull NamespacedKey key) {
            super(key);
        }

        @SuppressWarnings("unchecked")
        @Override
        public @NotNull Class<M> getItemMetaClass() {
            return (Class<M>) super.getItemMetaClass();
        }

        @Override
        public @NotNull ItemStack createItemStack(@Nullable Consumer<? super M> metaConfigurator) {
            throw new NotImplementedException();
        }

        @Override
        public @NotNull ItemStack createItemStack(int amount, @Nullable Consumer<? super M> metaConfigurator) {
            throw new NotImplementedException();
        }

    }

}
