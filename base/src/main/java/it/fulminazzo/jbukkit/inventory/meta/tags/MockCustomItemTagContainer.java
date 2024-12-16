package it.fulminazzo.jbukkit.inventory.meta.tags;

import it.fulminazzo.jbukkit.Equable;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class MockCustomItemTagContainer extends Equable implements CustomItemTagContainer {
    private final Map<NamespacedKey, DataContainer<?, ?>> data = new HashMap<>();

    public <P, C> void setCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<P, C> type, @NotNull C value) {
        this.data.put(key, new DataContainer<>(type, value));
    }

    public <P, C> boolean hasCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<P, C> type) {
        DataContainer<?, ?> container = this.data.get(key);
        return container != null && container.type.equals(type);
    }

    @Nullable
    public <P, C> C getCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<P, C> type) {
        return hasCustomTag(key, type) ? null : (C) this.data.get(key).value;
    }

    public void removeCustomTag(@NotNull NamespacedKey key) {
        this.data.remove(key);
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @NotNull
    public ItemTagAdapterContext getAdapterContext() {
        return new ItemTagAdapterContext() {
            @NotNull
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
