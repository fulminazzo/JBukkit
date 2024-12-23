package it.fulminazzo.jbukkit.registries;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A registry helper for {@link org.bukkit.block.BlockType} and {@link org.bukkit.inventory.ItemType}.
 *
 * @param <T> the type of the objects
 */
public class TypeRegistry<T extends Keyed> implements Registry<T> {
    private final Class<T> type;

    /**
     * Instantiates a new Type registry.
     *
     * @param type the type of the objects
     */
    public TypeRegistry(final @NotNull Class<T> type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public @Nullable T get(@NotNull NamespacedKey key) {
        if (Arrays.stream(this.type.getFields()).anyMatch(f -> f.getName().equalsIgnoreCase(key.getKey()))) {
            T blockType = mock(this.type);
            when(blockType.getKey()).thenReturn(key);
            return blockType;
        } else return null;
    }

    @Override
    public @NotNull Stream<T> stream() {
        return Arrays.stream(Material.values())
                .map(m -> get(NamespacedKey.minecraft(m.name().toLowerCase())))
                .filter(Objects::nonNull);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return stream().iterator();
    }

}
