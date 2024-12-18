package it.fulminazzo.jbukkit.persistence;

import lombok.Getter;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
public class DataContainer<T, Z> {
    private final @NotNull PersistentDataType<T, Z> type;
    private final @Nullable Z value;

    public DataContainer(final @NotNull PersistentDataType<T, Z> type,
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
        return o instanceof DataContainer &&
                ((DataContainer<?, ?>) o).type.equals(this.type) &&
                Objects.equals(((DataContainer<?, ?>) o).value, this.value);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.type, this.value);
    }

}
