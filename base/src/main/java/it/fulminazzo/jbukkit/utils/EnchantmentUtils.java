package it.fulminazzo.jbukkit.utils;

import lombok.NoArgsConstructor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for {@link Enchantment}.
 */
@NoArgsConstructor
public final class EnchantmentUtils {

    /**
     * Returns the enchantment corresponding to key "minecraft:{key}".
     * If none is found, uses {@link Registry} to search for it.
     *
     * @param key the key
     * @return the enchantment
     */
    public static Enchantment getEnchantment(final @NotNull String key) {
        NamespacedKey actualKey = NamespacedKey.minecraft(key.toLowerCase());
        Enchantment enchantment = Enchantment.getByKey(actualKey);
        if (enchantment == null) enchantment = Registry.ENCHANTMENT.get(actualKey);
        return enchantment;
    }

}
