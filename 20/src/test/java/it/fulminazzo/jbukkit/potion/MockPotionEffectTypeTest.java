package it.fulminazzo.jbukkit.potion;

import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockPotionEffectTypeTest {

    @Test
    void testNameAndKey() {
        BukkitUtils.setupServer();
        PotionEffectType effectType = new MockPotionEffectType("Mining Fatigue",
                PotionEffectTypeCategory.NEUTRAL, Color.RED, false);
        assertEquals("Mining Fatigue", effectType.getName());
        assertEquals(NamespacedKey.minecraft("mining_fatigue"), effectType.getKey());
    }

}