package it.fulminazzo.jbukkit.registries;

import com.sun.istack.internal.NotNull;
import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FieldsRegistryTest {
    private Registry<MockClass> registry;

    @BeforeEach
    void setUp() {
        this.registry = new FieldsRegistry<>(MockClass.class, MockClass::new);
    }

    @Getter
    static class MockClass extends Equable implements Keyed {
        public static final MockClass FIRST = new MockClass("first");
        public static final MockClass SECOND = new MockClass("second");
        public static final MockClass THIRD = new MockClass("third");
        private final @NotNull NamespacedKey key;

        public MockClass(final @NotNull String name) {
            this(NamespacedKey.minecraft(name));
        }

        public MockClass(final @NotNull NamespacedKey key) {
            this.key = Objects.requireNonNull(key);
        }

    }
}