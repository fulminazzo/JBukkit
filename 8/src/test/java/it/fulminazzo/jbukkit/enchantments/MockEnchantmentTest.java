package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class MockEnchantmentTest {

    @BeforeEach
    void setUp() {
        BukkitUtils.setupEnchantments();
    }

    private static Object[] getEnchantments() {
        BukkitUtils.setupEnchantments();
        Refl<?> enchantment = new Refl<>(Enchantment.class);
        return enchantment.getStaticFields().stream()
                .filter(f -> Enchantment.class.isAssignableFrom(f.getType()))
                .map(f -> new Object[]{f.getName(), enchantment.getFieldObject(f)})
                .toArray(Object[]::new);
    }

    @ParameterizedTest
    @MethodSource("getEnchantments")
    void testAllTypes(String name, Enchantment enchantment) {
        assertInstanceOf(EnchantmentWrapper.class, enchantment, "Expected " + name + " to be of type EnchantmentWrapper");
        assertInstanceOf(MockEnchantment.class, ((EnchantmentWrapper) enchantment).getEnchantment(),
                "Expected wrapped " + name + " to be of type MockEnchantment");
    }

    @ParameterizedTest
    @MethodSource("getEnchantments")
    void shouldNotBeAbleToCreateVanillaEffect(String name, Enchantment enchantment) {
        assertThrowsExactly(IllegalArgumentException.class, () -> new MockEnchantment(enchantment.getId(),
                enchantment.getName(), 0, 1, EnchantmentTarget.ARMOR));
    }

}