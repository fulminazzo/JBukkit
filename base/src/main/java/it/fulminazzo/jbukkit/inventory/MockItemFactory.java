package it.fulminazzo.jbukkit.inventory;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a mock implementation for {@link ItemFactory}.
 */
public class MockItemFactory implements ItemFactory {

    @Override
    public @NotNull ItemMeta getItemMeta(@NotNull Material material) {
        return null;
    }

    @Override
    public boolean isApplicable(@Nullable ItemMeta itemMeta, @Nullable ItemStack itemStack) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean isApplicable(@Nullable ItemMeta itemMeta, @Nullable Material material) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(@Nullable ItemMeta meta1, @Nullable ItemMeta meta2) throws IllegalArgumentException {
        return false;
    }

    @Override
    public ItemMeta asMetaFor(@NotNull ItemMeta itemMeta, @NotNull ItemStack itemStack) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ItemMeta asMetaFor(@NotNull ItemMeta itemMeta, @NotNull Material material) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Color getDefaultLeatherColor() {
        return null;
    }

    public @NotNull ItemStack createItemStack(@NotNull String input) throws IllegalArgumentException {
        return null;
    }

    public @Nullable Material getSpawnEgg(@NotNull EntityType type) {
        return null;
    }

    public @NotNull ItemStack enchantItem(@NotNull Entity entity, @NotNull ItemStack item, int level, boolean allowTreasures) {
        return null;
    }

    public @NotNull ItemStack enchantItem(@NotNull World world, @NotNull ItemStack item, int level, boolean allowTreasures) {
        return null;
    }

    public @NotNull ItemStack enchantItem(@NotNull ItemStack item, int level, boolean allowTreasures) {
        return null;
    }
}
