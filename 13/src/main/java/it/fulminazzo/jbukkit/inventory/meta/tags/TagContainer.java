package it.fulminazzo.jbukkit.inventory.meta.tags;

import lombok.Getter;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public class TagContainer<T, Z> {
    private final ItemTagType<T, Z> type;
    private final Z value;

    public TagContainer(final @NotNull ItemTagType<T, Z> type,
                        final @NotNull Z value) {
        this.type = Objects.requireNonNull(type);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public int hashCode() {
        return this.type.hashCode() + this.value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TagContainer &&
                ((TagContainer<?, ?>) o).type.equals(this.type) &&
                ((TagContainer<?, ?>) o).value.equals(this.value);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.type, this.value);
    }

}
