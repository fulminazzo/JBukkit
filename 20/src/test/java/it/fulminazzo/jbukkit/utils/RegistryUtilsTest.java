package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.banner.PatternType;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.loot.LootTables;
import org.bukkit.map.MapCursor;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class RegistryUtilsTest {

    private static Object[][] getRegistries() {
        BukkitUtils.setupServer();
        return new Object[][] {
                // Abstract Registry
                new Object[]{Advancement.class, Registry.class},
                new Object[]{KeyedBossBar.class, Registry.class},
                new Object[]{MemoryKey.class, Registry.class},
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
        Registry<T> actual = Bukkit.getRegistry(clazz);
        assertInstanceOf(expected, actual);
    }

    private static Object[] getAllRegistries() {
        BukkitUtils.setupServer();
        Refl<Class<?>> registry = new Refl<>(Registry.class);
        return registry.getStaticFields().stream()
                .filter(f -> Registry.class.isAssignableFrom(f.getType()))
                .map(f -> new Object[]{f.getName(), registry.getFieldObject(f)})
                .toArray(Object[]::new);
    }

    @ParameterizedTest
    @MethodSource("getAllRegistries")
    <T extends Keyed> void testEveryRegistryNotNull(String registryName, Registry<T> registry) {
        assertNotNull(registry, "Registry " + registryName + " was not initialized");
        assertFalse(registry.getClass().getName().contains("MockitoMock"),
                "Expected registry " + registryName + " to not be mock");
    }

}