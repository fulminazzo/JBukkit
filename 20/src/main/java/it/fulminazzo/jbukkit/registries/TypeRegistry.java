package it.fulminazzo.jbukkit.registries;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a registry where elements can be manually added.
 * Used mainly for {@link org.bukkit.inventory.ItemType} and {@link org.bukkit.block.BlockType}.
 *
 * @param <T> the type parameter
 */
public class TypeRegistry<T extends Keyed> implements Registry<T> {
    private final Set<T> contents;

    /**
     * Instantiates a new Type registry.
     */
    public TypeRegistry() {
        this.contents = new HashSet<>();
    }

    /**
     * Adds to the internal contents list.
     *
     * @param t the element
     */
    public void add(final @NotNull T t) {
        this.contents.add(t);
    }

    /**
     * Removes from the internal contents list.
     *
     * @param t the element
     */
    public void remove(final @NotNull T t) {
        this.contents.remove(Objects.requireNonNull(t));
    }

    @Override
    public @Nullable T get(final @NotNull NamespacedKey key) {
        return this.contents.stream()
                .filter(t -> t.getKey().equals(key))
                .findFirst().orElse(null);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return this.contents.stream();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return stream().iterator();
    }

}
