package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class MockEnchantment extends Enchantment {
    private int startLevel;
    private int maxLevel;
    private EnchantmentTarget itemTarget;

    public MockEnchantment(@NotNull NamespacedKey key) {
        super(key);
    }

    @NotNull
    public String getName() {
        return getKey().getKey();
    }

    public boolean isTreasure() {
        return false;
    }

    public boolean isCursed() {
        return false;
    }

    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return this.itemTarget != null && this.itemTarget.includes(itemStack);
    }

    @Override
    public boolean equals(Object obj) {
        return Equable.equals(this, obj) || super.equals(obj);
    }
}
