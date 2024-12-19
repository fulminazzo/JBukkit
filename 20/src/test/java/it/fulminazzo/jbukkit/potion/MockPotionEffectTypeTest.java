package it.fulminazzo.jbukkit.potion;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class MockPotionEffectTypeTest {

    @BeforeEach
    void setUp() {
        BukkitUtils.setupServer();
    }

    private static PotionEffectType[] getTypes() {
        BukkitUtils.setupServer();
        Refl<?> potionEffectType = new Refl<>(PotionEffectType.class);
        return potionEffectType.getStaticFields().stream()
                .filter(f -> PotionEffectType.class.isAssignableFrom(f.getType()))
                .map(potionEffectType::getFieldObject)
                .map(o -> (PotionEffectType) o)
                .toArray(PotionEffectType[]::new);
    }

    @ParameterizedTest
    @MethodSource("getTypes")
    void testAllTypes(PotionEffectType type) {
        assertInstanceOf(MockPotionEffectType.class, type);
    }

    @Test
    void testNameAndKey() {
        PotionEffectType effectType = new MockPotionEffectType("Minin Fatigue",
                PotionEffectTypeCategory.NEUTRAL, Color.RED, false);
        assertEquals("Minin Fatigue", effectType.getName());
        assertEquals(NamespacedKey.minecraft("minin_fatigue"), effectType.getKey());
    }

    @Test
    void testUnluckName() {
        assertEquals("Bad Luck", PotionEffectType.UNLUCK.getName());
    }

    @ParameterizedTest
    @MethodSource("getTypes")
    void shouldNotBeAbleToCreateVanillaEffect(PotionEffectType type) {
        assertThrowsExactly(IllegalArgumentException.class, () -> new MockPotionEffectType(type.getName(),
                PotionEffectTypeCategory.NEUTRAL, Color.RED, false));
    }

}