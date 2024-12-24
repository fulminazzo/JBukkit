package it.fulminazzo.jbukkit;

import com.google.common.collect.Multimap;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a mock for {@link UnsafeValues}.
 */
public class MockUnsafeValues implements UnsafeValues {
    private static final String LEGACY_PREFIX = "LEGACY_";

    @Override
    public Material toLegacy(Material material) {
        return Material.valueOf(LEGACY_PREFIX + material.name());
    }

    @Override
    public Material fromLegacy(Material material) {
        return Material.valueOf(material.name().substring(LEGACY_PREFIX.length()));
    }

    @Override
    public Material fromLegacy(MaterialData material) {
        throw new NotImplementedException();
    }

    @Override
    public Material fromLegacy(MaterialData material, boolean itemPriority) {
        throw new NotImplementedException();
    }

    @Override
    public BlockData fromLegacy(Material material, byte data) {
        throw new NotImplementedException();
    }

    @Override
    public Material getMaterial(String material, int version) {
        return Material.valueOf(material.toUpperCase());
    }

    @Override
    public int getDataVersion() {
        return 1;
    }

    @Override
    public ItemStack modifyItemStack(ItemStack stack, String arguments) {
        throw new NotImplementedException();
    }

    @Override
    public void checkSupported(PluginDescriptionFile pdf) throws InvalidPluginException {
        throw new NotImplementedException();
    }

    @Override
    public byte[] processClass(PluginDescriptionFile pdf, String path, byte[] clazz) {
        throw new NotImplementedException();
    }

    @Override
    public Advancement loadAdvancement(NamespacedKey key, String advancement) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAdvancement(NamespacedKey key) {
        throw new NotImplementedException();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(Material material, EquipmentSlot slot) {
        throw new NotImplementedException();
    }

    @Override
    public CreativeCategory getCreativeCategory(Material material) {
        throw new NotImplementedException();
    }

    @Override
    public String getBlockTranslationKey(Material material) {
        throw new NotImplementedException();
    }

    @Override
    public String getItemTranslationKey(Material material) {
        throw new NotImplementedException();
    }

    @Override
    public String getTranslationKey(EntityType entityType) {
        throw new NotImplementedException();
    }

    @Override
    public String getTranslationKey(ItemStack itemStack) {
        throw new NotImplementedException();
    }

    @Override
    public String getTranslationKey(Attribute attribute) {
        throw new NotImplementedException();
    }

    @Override
    public @Nullable FeatureFlag getFeatureFlag(@NotNull NamespacedKey key) {
        throw new NotImplementedException();
    }

    @Override
    public PotionType.InternalPotionData getInternalPotionData(NamespacedKey key) {
        throw new NotImplementedException();
    }

    @Override
    public @Nullable DamageEffect getDamageEffect(@NotNull String key) {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull DamageSource.Builder createDamageSourceBuilder(@NotNull DamageType damageType) {
        throw new NotImplementedException();
    }

    @Override
    public String get(Class<?> aClass, String value) {
        throw new NotImplementedException();
    }

    @Override
    public <B extends Keyed> B get(Registry<B> registry, NamespacedKey key) {
        throw new NotImplementedException();
    }

    @Override
    public Biome getCustomBiome() {
        throw new NotImplementedException();
    }

}
