package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Villager;
import org.bukkit.loot.LootTables;
import org.bukkit.map.MapCursor;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class RegistryUtilsTest {

    private static Object[][] getRegistries() {
        BukkitUtils.setupServer(false);
        return new Object[][] {
                // SimpleRegistry
                new Object[]{Art.class, Registry.SimpleRegistry.class},
                new Object[]{Attribute.class, Registry.SimpleRegistry.class},
                new Object[]{PatternType.class, Registry.SimpleRegistry.class},
                new Object[]{Biome.class, Registry.SimpleRegistry.class},
                new Object[]{Cat.Type.class, Registry.SimpleRegistry.class},
                new Object[]{EntityType.class, Registry.SimpleRegistry.class},
                new Object[]{LootTables.class, Registry.SimpleRegistry.class},
                new Object[]{Material.class, Registry.SimpleRegistry.class},
                new Object[]{Particle.class, Registry.SimpleRegistry.class},
                new Object[]{PotionType.class, Registry.SimpleRegistry.class},
                new Object[]{Statistic.class, Registry.SimpleRegistry.class},
                new Object[]{Sound.class, Registry.SimpleRegistry.class},
                new Object[]{Villager.Profession.class, Registry.SimpleRegistry.class},
                new Object[]{Villager.Type.class, Registry.SimpleRegistry.class},
                new Object[]{Fluid.class, Registry.SimpleRegistry.class},
                new Object[]{Frog.Variant.class, Registry.SimpleRegistry.class},
                new Object[]{MapCursor.Type.class, Registry.SimpleRegistry.class},
        };
    }

    @ParameterizedTest
    @MethodSource("getRegistries")
    <T extends Keyed> void testEveryRegistry(Class<T> clazz, Class<Registry<T>> expected) {
        RegistryUtils.setupRegistries();
        Registry<T> actual = Bukkit.getRegistry(clazz);
        assertInstanceOf(expected, actual);
    }

}