package it.fulminazzo.jbukkit.inventory.meta.tags;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class MockCustomItemTagContainer implements CustomItemTagContainer {
    private final Map<NamespacedKey, DataContainer<?, ?>> data = new HashMap<>();

    @Override
    public <P, C> void setCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<P, C> type, @NotNull C value) {
        this.data.put(key, new DataContainer<>(type, value));
    }

    @Override
    public <P, C> boolean hasCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<P, C> type) {
        DataContainer<?, ?> container = this.data.get(key);
        return container != null && container.type.equals(type);
    }

    @Nullable
    @Override
    public <P, C> C getCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<P, C> type) {
        return hasCustomTag(key, type) ? null : (C) this.data.get(key).value;
    }

    @Override
    public void removeCustomTag(@NotNull NamespacedKey key) {
        this.data.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @NotNull
    @Override
    public ItemTagAdapterContext getAdapterContext() {
        return new ItemTagAdapterContext() {
            @NotNull
            @Override
            public CustomItemTagContainer newTagContainer() {
                return new MockCustomItemTagContainer();
            }
        };
    }

    private static class DataContainer<P, C> {
        private final ItemTagType<P, C> type;
        private final C value;

        private DataContainer(ItemTagType<P, C> type, C value) {
            this.type = type;
            this.value = value;
        }
    }
}
