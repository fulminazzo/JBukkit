package it.fulminazzo.jbukkit.utils;

import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

public class EnchantmentUtils {

    /**
     * Returns the enchantment corresponding to the given name.
     *
     * @param name the name
     * @return the enchantment
     */
    public static Enchantment getEnchantment(final @NotNull String name) {
        return Enchantment.getByName(name);
    }

}
