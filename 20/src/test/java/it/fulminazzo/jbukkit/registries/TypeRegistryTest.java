package it.fulminazzo.jbukkit.registries;

import it.fulminazzo.jbukkit.BukkitUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeRegistryTest {

    @SuppressWarnings("UnstableApiUsage")
    @Test
    void testGet() {
        BukkitUtils.setupServer();
        TypeRegistry<BlockType> registry = new TypeRegistry<>(BlockType.class);
        NamespacedKey key = NamespacedKey.minecraft("stone");
        BlockType type = registry.get(key);
        assertNotNull(type);
        assertEquals(key, type.getKey());
    }

}