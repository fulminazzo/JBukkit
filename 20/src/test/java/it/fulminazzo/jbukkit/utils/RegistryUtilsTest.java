package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class RegistryUtilsTest {

    private static Object[][] getRegistries() {
        BukkitUtils.setupServer(false);
        return new Object[][] {

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