package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Getter
@Setter
public class MockEnchantment extends Enchantment {
    private static final List<Enchantment> ENCHANTMENTS = new ArrayList<>();
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
        for (Field field : Enchantment.class.getDeclaredFields())
            if (field.getType().equals(Enchantment.class))
                ENCHANTMENTS.add(new MockEnchantment(NamespacedKey.minecraft(field.getName().toLowerCase())));
        // Register enchantments
        Registry<Enchantment> registry = Bukkit.getRegistry(Enchantment.class);
        when(registry.get(any())).thenAnswer(a -> {
            NamespacedKey key = a.getArgument(0);
            return ENCHANTMENTS.stream()
                    .filter(e -> e.getKey().equals(key))
                    .findFirst().orElseThrow(() ->
                            new IllegalArgumentException("Could not find enchantment: " + key));
        });
    }
}
