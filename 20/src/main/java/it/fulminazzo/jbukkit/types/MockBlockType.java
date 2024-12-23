package it.fulminazzo.jbukkit.types;

import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Represents a mock for {@link BlockType}.
 */
@SuppressWarnings("UnstableApiUsage")
@Getter
@Setter
public class MockBlockType implements BlockType {
    private final NamespacedKey key;
    private Class<? extends BlockData> blockDataClass;
    private boolean solid;
    private boolean flammable;
    private boolean burnable;
    private boolean occluding;
    private boolean interactable;
    @Getter(AccessLevel.NONE)
    private boolean gravity;
    private float hardness;
    private float blastResistance;
    private float slipperiness;

    /**
     * Instantiates a new Mock block type.
     *
     * @param key the key
     */
    public MockBlockType(final @NotNull NamespacedKey key) {
        this.key = key;
    }

    @Override
    public @NotNull Typed<BlockData> typed() {
        return typed(BlockData.class);
    }

    @Override
    public @NotNull <B extends BlockData> Typed<B> typed(@NotNull Class<B> blockDataType) {
        MockTyped<B> mockTyped = new MockTyped<>(this.key);
        mockTyped.setBlockDataClass(blockDataType);
        return mockTyped;
    }

    @Override
    public boolean hasItemType() {
        return true;
    }

    @Override
    public @NotNull ItemType getItemType() {
        return new MockItemType(this.key);
    }

    @Override
    public @NotNull BlockData createBlockData() {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull BlockData createBlockData(@Nullable String data) {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasGravity() {
        return this.gravity;
    }

    @Override
    public boolean isAir() {
        return this.key.getKey().equalsIgnoreCase("air");
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
     * Represents a mock for {@link org.bukkit.block.BlockType.Typed}.
     *
     * @param <B> the type parameter
     */
    public static class MockTyped<B extends BlockData> extends MockBlockType implements Typed<B> {

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
        public @NotNull Class<B> getBlockDataClass() {
            return (Class<B>) super.getBlockDataClass();
        }

        @Override
        public @NotNull B createBlockData() {
            throw new NotImplementedException();
        }

        @Override
        public @NotNull B createBlockData(@Nullable String data) {
            throw new NotImplementedException();
        }

        @Override
        public @NotNull B createBlockData(@Nullable Consumer<? super B> consumer) {
            throw new NotImplementedException();
        }

    }

}
