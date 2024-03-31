package it.fulminazzo.jbukkit.persistence;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
@Getter
public class MockPersistentDataContainer implements PersistentDataContainer {
    private final Map<NamespacedKey, DataContainer<?, ?>> data = new HashMap<>();

    public <P, C> void set(@NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type, @NotNull C value) {
        this.data.put(key, new DataContainer<>(type, value));
    }

    public <P, C> boolean has(@NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type) {
        DataContainer<?, ?> container = this.data.get(key);
        return container != null && container.type.equals(type);
    }

    public boolean has(@NotNull NamespacedKey key) {
        return this.data.containsKey(key);
    }

    @Nullable
    public <P, C> C get(@NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type) {
        return has(key, type) ? null : (C) this.data.get(key).value;
    }

    @NotNull
    public <P, C> C getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type, @NotNull C defaultValue) {
        C c = get(key, type);
        if (c == null) c = defaultValue;
        return c;
    }

    @NotNull
    public Set<NamespacedKey> getKeys() {
        return this.data.keySet();
    }

    public void remove(@NotNull NamespacedKey key) {
        this.data.remove(key);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public void copyTo(@NotNull PersistentDataContainer other, boolean replace) {
        for (NamespacedKey key : getKeys())
            if (!other.has(key, this.data.get(key).type) || replace) {
                DataContainer<?, ?> container = this.data.get(key);
                set(other, key, container.type, container.value);
            }
    }

    private <P, C> void set(PersistentDataContainer container, NamespacedKey key, PersistentDataType<?, ?> type, C value) {
        container.set(key, (PersistentDataType<P, C>) type, value);
    }

    @NotNull
    public PersistentDataAdapterContext getAdapterContext() {
        return new PersistentDataAdapterContext() {
            @NotNull
            public PersistentDataContainer newPersistentDataContainer() {
                return new MockPersistentDataContainer();
            }
        };
    }

    private static class DataContainer<P, C> {
        private final PersistentDataType<P, C> type;
        private final C value;

        private DataContainer(PersistentDataType<P, C> type, C value) {
            this.type = type;
            this.value = value;
        }
    }
}
