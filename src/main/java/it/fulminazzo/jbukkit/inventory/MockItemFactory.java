package it.fulminazzo.jbukkit.inventory;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.jbukkit.inventory.meta.MockDamageable;
import it.fulminazzo.jbukkit.inventory.meta.MockEnchantmentStorageMeta;
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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MockItemFactory implements ItemFactory {
    private final Map<Material, Class<? extends ItemMeta>> materialAssociations = new HashMap<Material, Class<? extends ItemMeta>>(){{
        put(Material.ENCHANTED_BOOK, MockEnchantmentStorageMeta.class);
    }};

    private Class<? extends ItemMeta> getMaterialClass(Material material) {
        return this.materialAssociations.getOrDefault(material, MockDamageable.class);
    }

    @Nullable
    public ItemMeta getItemMeta(@NotNull Material material) {
        return new Refl<>(getMaterialClass(material), new Object[0]).getObject();
    }

    public boolean isApplicable(@Nullable ItemMeta meta, @Nullable ItemStack stack) throws IllegalArgumentException {
        return stack != null && isApplicable(meta, stack.getType());
    }

    public boolean isApplicable(@Nullable ItemMeta meta, @Nullable Material material) throws IllegalArgumentException {
        return meta != null && material != null && getMaterialClass(material).isAssignableFrom(meta.getClass());
    }

    public boolean equals(@Nullable ItemMeta meta1, @Nullable ItemMeta meta2) throws IllegalArgumentException {
        return meta1 != null && ReflectionUtils.equalsFields(meta1, meta2);
    }

    @Nullable
    public ItemMeta asMetaFor(@NotNull ItemMeta meta, @NotNull ItemStack stack) throws IllegalArgumentException {
        ItemMeta itemMeta = stack.getItemMeta();
        Refl<?> m1 = new Refl<>(meta);
        Refl<?> m2 = new Refl<>(itemMeta);
        for (Field field : m1.getNonStaticFields())
            try {
                field.setAccessible(true);
                m2.setFieldObject(field, m1.getFieldObject(field));
            } catch (Exception ignored) {

            }
        return itemMeta;
    }

    @Nullable
    public ItemMeta asMetaFor(@NotNull ItemMeta meta, @NotNull Material material) throws IllegalArgumentException {
        return getItemMeta(material);
    }

    @NotNull
    public Color getDefaultLeatherColor() {
        return Color.MAROON;
    }

    @NotNull
    public ItemStack createItemStack(@NotNull String input) throws IllegalArgumentException {
        return new ItemStack(Material.valueOf(input.toUpperCase()));
    }

    @NotNull
    public Material updateMaterial(@NotNull ItemMeta meta, @NotNull Material material) throws IllegalArgumentException {
        return material;
    }

    @Nullable
    public Material getSpawnEgg(@NotNull EntityType type) {
        return Material.valueOf(type.name().toUpperCase() + "_SPAWN_EGG");
    }

    @NotNull
    public ItemStack enchantItem(@NotNull Entity entity, @NotNull ItemStack item, int level, boolean allowTreasures) {
        return item;
    }

    @NotNull
    public ItemStack enchantItem(@NotNull World world, @NotNull ItemStack item, int level, boolean allowTreasures) {
        return item;
    }

    @NotNull
    public ItemStack enchantItem(@NotNull ItemStack item, int level, boolean allowTreasures) {
        return item;
    }
}
