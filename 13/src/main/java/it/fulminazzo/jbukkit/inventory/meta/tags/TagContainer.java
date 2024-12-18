package it.fulminazzo.jbukkit.inventory.meta.tags;

import lombok.Getter;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
public class TagContainer<T, Z> {
    private final @NotNull ItemTagType<T, Z> type;
    private final @Nullable Z value;

    public TagContainer(final @NotNull ItemTagType<T, Z> type,
                        final @Nullable Z value) {
        this.type = Objects.requireNonNull(type);
        this.value = value;
    }

    @Override
    public int hashCode() {
        int code = this.type.hashCode();
        if (this.value != null) code += this.value.hashCode();
        return code;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TagContainer &&
                ((TagContainer<?, ?>) o).type.equals(this.type) &&
                Objects.equals(((TagContainer<?, ?>) o).value, this.value);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.type, this.value);
    }

}
