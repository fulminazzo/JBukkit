package it.fulminazzo.jbukkit;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.UnsafeValues;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;

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

}
