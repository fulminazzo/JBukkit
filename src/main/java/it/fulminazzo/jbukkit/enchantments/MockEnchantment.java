package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MockEnchantment extends Enchantment {
    private final @NotNull NamespacedKey key;
    private int startLevel;
    private int maxLevel;
    private EnchantmentTarget itemTarget;

    public MockEnchantment(final @NotNull NamespacedKey key) {
        this.key = key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return getName();
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

    public static void setupEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        for (Field field : Enchantment.class.getDeclaredFields())
            if (field.getType().equals(Enchantment.class))
                try {
                    Enchantment enchant = (Enchantment) field.get(Enchantment.class);
                    enchantments.add(new MockEnchantment(enchant.getKey()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
        // Register enchantments
        Map<NamespacedKey, Enchantment> byKey = new Refl<>(Enchantment.class).getFieldObject("byKey");
        if (byKey != null) enchantments.forEach(e -> byKey.put(e.getKey(), e));
    }
}
