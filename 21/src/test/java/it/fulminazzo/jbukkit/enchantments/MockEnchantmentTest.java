package it.fulminazzo.jbukkit.enchantments;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class MockEnchantmentTest {

    @BeforeEach
    void setUp() {
        BukkitUtils.setupServer();
    }

    private static Enchantment[] getEnchantments() {
        BukkitUtils.setupServer();
        Refl<?> enchantment = new Refl<>(Enchantment.class);
        return enchantment.getStaticFields().stream()
                .filter(f -> Enchantment.class.isAssignableFrom(f.getType()))
                .map(enchantment::getFieldObject)
                .map(o -> (Enchantment) o)
                .toArray(Enchantment[]::new);
    }

    @ParameterizedTest
    @MethodSource("getEnchantments")
    void testAllTypes(Enchantment enchantment) {
        assertInstanceOf(MockEnchantment.class, enchantment);
    }

    @ParameterizedTest
    @MethodSource("getEnchantments")
    void shouldNotBeAbleToCreateVanillaEffect(Enchantment enchantment) {
        assertThrowsExactly(IllegalArgumentException.class, () -> new MockEnchantment(enchantment.getName(),
                0, 1, EnchantmentTarget.ARMOR));
    }

}