package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.BukkitUtils;
import it.fulminazzo.jbukkit.registries.FieldsRegistry;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.banner.PatternType;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.loot.LootTables;
import org.bukkit.map.MapCursor;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class RegistryUtilsTest {

    private static Object[][] getRegistries() {
        BukkitUtils.setupServer();
        return new Object[][] {
                // Fields Registry
                new Object[]{GameEvent.class, FieldsRegistry.class},
                new Object[]{Wolf.Variant.class, FieldsRegistry.class},
                new Object[]{DamageType.class, FieldsRegistry.class},
                new Object[]{TrimPattern.class, FieldsRegistry.class},
                new Object[]{TrimMaterial.class, FieldsRegistry.class},
                new Object[]{StructureType.class, FieldsRegistry.class},
                new Object[]{Structure.class, FieldsRegistry.class},
                new Object[]{PotionEffectType.class, FieldsRegistry.class},
                new Object[]{MusicInstrument.class, FieldsRegistry.class},
                new Object[]{Enchantment.class, FieldsRegistry.class},
                // Abstract Registry
                new Object[]{Advancement.class, Registry.class},
                new Object[]{KeyedBossBar.class, Registry.class},
                new Object[]{MemoryKey.class, Registry.class},
                // SimpleRegistry
                new Object[]{Art.class, FieldsRegistry.class},
                new Object[]{Attribute.class, FieldsRegistry.class},
                new Object[]{PatternType.class, FieldsRegistry.class},
                new Object[]{Biome.class, FieldsRegistry.class},
                new Object[]{Cat.Type.class, FieldsRegistry.class},
                new Object[]{EntityType.class, FieldsRegistry.class},
                new Object[]{LootTables.class, FieldsRegistry.class},
                new Object[]{Material.class, FieldsRegistry.class},
                new Object[]{Particle.class, FieldsRegistry.class},
                new Object[]{PotionType.class, FieldsRegistry.class},
                new Object[]{Statistic.class, FieldsRegistry.class},
                new Object[]{Sound.class, FieldsRegistry.class},
                new Object[]{Villager.Profession.class, FieldsRegistry.class},
                new Object[]{Villager.Type.class, FieldsRegistry.class},
                new Object[]{Fluid.class, FieldsRegistry.class},
                new Object[]{Frog.Variant.class, FieldsRegistry.class},
                new Object[]{MapCursor.Type.class, FieldsRegistry.class},
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