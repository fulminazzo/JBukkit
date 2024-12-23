package it.fulminazzo.jbukkit.persistence;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Mock persistent data container.
 */
@Getter
public class MockPersistentDataContainer extends Equable implements PersistentDataContainer {
    private final Map<NamespacedKey, DataContainer<?, ?>> data;
    private final PersistentDataAdapterContext adapterContext;

    /**
     * Instantiates a new Mock persistent data container.
     */
    public MockPersistentDataContainer() {
        this.data = new LinkedHashMap<>();
        this.adapterContext = new MockPersistentDataAdapterContext();
    }

    @Override
    public <T, Z> void set(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        this.data.put(key, new DataContainer<>(type, value));
    }

    @Override
    public <T, Z> boolean has(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return get(key, type) != null;
    }

    @Override
    public boolean has(@NotNull NamespacedKey key) {
        return this.data.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T, Z> Z get(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        DataContainer<?, ?> dataContainer = this.data.get(key);
        if (dataContainer == null) return null;
        Object value = dataContainer.getValue();
        if (value == null) return null;
        return type.fromPrimitive((T) value, this.adapterContext);
    }

    @Override
    public @NotNull <T, Z> Z getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z defaultValue) {
        Z value = get(key, type);
        if (value == null) return defaultValue;
        return value;
    }

    @Override
    public @NotNull Set<NamespacedKey> getKeys() {
        return this.data.keySet();
    }

    @Override
    public void remove(@NotNull NamespacedKey key) {
        this.data.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public void copyTo(@NotNull PersistentDataContainer other, boolean replace) {
        this.data.forEach((k, v) -> {
            if (!other.has(k) || replace)
                new Refl<>(other).invokeMethod("set", k, v.getType(), v.getValue());
        });
    }

}
