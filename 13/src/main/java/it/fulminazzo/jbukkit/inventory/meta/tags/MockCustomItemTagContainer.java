package it.fulminazzo.jbukkit.inventory.meta.tags;

import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a mock for {@link CustomItemTagContainer}.
 */
@Getter
public class MockCustomItemTagContainer extends Equable implements CustomItemTagContainer {
    private final Map<NamespacedKey, TagContainer<?, ?>> customDataTags;
    private final ItemTagAdapterContext adapterContext;

    /**
     * Instantiates a new Mock custom item tag container.
     */
    public MockCustomItemTagContainer() {
        this.customDataTags = new HashMap<>();
        this.adapterContext = new MockItemTagAdapterContext();
    }

    @Override
    public <T, Z> void setCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<T, Z> type, @NotNull Z value) {
        this.customDataTags.put(key, new TagContainer<>(type, value));
    }

    @Override
    public <T, Z> boolean hasCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<T, Z> type) {
        return getCustomTag(key, type) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T, Z> Z getCustomTag(@NotNull NamespacedKey key, @NotNull ItemTagType<T, Z> type) {
        TagContainer<?, ?> tagContainer = this.customDataTags.get(key);
        if (tagContainer == null) return null;
        Object value = tagContainer.getValue();
        if (value == null) return null;
        return type.fromPrimitive((T) value, this.adapterContext);
    }

    @Override
    public void removeCustomTag(@NotNull NamespacedKey key) {
        this.customDataTags.remove(key);
    }

    @Override
    public boolean isEmpty() {
        return this.customDataTags.isEmpty();
    }

}
