package it.fulminazzo.jbukkit.utils;

import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.advancement.Advancement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistryUtilsTest {

    private static Object[][] getRegistries() {
        return new Object[][] {
                new Object[]{Advancement.class, Registry.ADVANCEMENT}
        };
    }

    @ParameterizedTest
    @MethodSource("getRegistries")
    <T extends Keyed> void testEveryRegistry(Class<T> clazz, Registry<T> expected) {
        Registry<T> actual = RegistryUtils.getRegistry(clazz);
        assertEquals(expected, actual);
    }

}