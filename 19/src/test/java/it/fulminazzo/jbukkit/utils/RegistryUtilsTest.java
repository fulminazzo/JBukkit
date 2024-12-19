package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.loot.LootTables;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistryUtilsTest {

    private static Object[][] getRegistries() {
        BukkitUtils.setupServer(false);
        return new Object[][] {
                new Object[]{Advancement.class, Registry.ADVANCEMENT},
                new Object[]{Art.class, Registry.ART},
                new Object[]{Attribute.class, Registry.ATTRIBUTE},
                new Object[]{Biome.class, Registry.BIOME},
                new Object[]{KeyedBossBar.class, Registry.BOSS_BARS},
                new Object[]{Enchantment.class, Registry.ENCHANTMENT},
                new Object[]{EntityType.class, Registry.ENTITY_TYPE},
                new Object[]{LootTables.class, Registry.LOOT_TABLES},
                new Object[]{Material.class, Registry.MATERIAL},
                new Object[]{Statistic.class, Registry.STATISTIC},
                new Object[]{Structure.class, Registry.STRUCTURE},
                new Object[]{StructureType.class, Registry.STRUCTURE_TYPE},
                new Object[]{Sound.class, Registry.SOUNDS},
                new Object[]{TrimMaterial.class, Registry.TRIM_MATERIAL},
                new Object[]{TrimPattern.class, Registry.TRIM_PATTERN},
                new Object[]{Villager.Profession.class, Registry.VILLAGER_PROFESSION},
                new Object[]{Villager.Type.class, Registry.VILLAGER_TYPE},
                new Object[]{MemoryKey.class, Registry.MEMORY_MODULE_TYPE},
                new Object[]{Fluid.class, Registry.FLUID},
                new Object[]{Frog.Variant.class, Registry.FROG_VARIANT},
                new Object[]{GameEvent.class, Registry.GAME_EVENT},
        };
    }

    @ParameterizedTest
    @MethodSource("getRegistries")
    <T extends Keyed> void testEveryRegistry(Class<T> clazz, Registry<T> expected) {
        RegistryUtils.setupRegistries();
        System.out.println(clazz);
        Registry<T> actual = Bukkit.getRegistry(clazz);
        assertEquals(expected, actual);
    }

}