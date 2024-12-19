package it.fulminazzo.jbukkit.registries;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import static org.junit.jupiter.api.Assertions.*;

class FieldsRegistryTest {


    @Getter
    static class MockClass implements Keyed {
        public static final MockClass FIRST = new MockClass("first");
        public static final MockClass SECOND = new MockClass("second");
        public static final MockClass THIRD = new MockClass("third");
        private final @NotNull NamespacedKey key;

        public MockClass(final @NotNull String name) {
            this.key = NamespacedKey.minecraft(name);
        }

    }
}