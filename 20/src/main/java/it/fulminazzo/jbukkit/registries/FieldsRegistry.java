package it.fulminazzo.jbukkit.registries;

import it.fulminazzo.fulmicollection.interfaces.functions.FunctionException;
import it.fulminazzo.fulmicollection.objects.Refl;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Represents a general {@link Registry} that supports a class with static fields representing its expected objects.
 *
 * @param <T> the type of the class
 */
public class FieldsRegistry<T extends Keyed> implements Registry<T> {
    private final @NotNull Map<NamespacedKey, T> internalMap;
    private final @NotNull Class<T> clazz;
    private final @NotNull FunctionException<NamespacedKey, T> conversionFunction;

    /**
     * Instantiates a new Fields registry.
     *
     * @param clazz              the class of the registry
     * @param conversionFunction a function to convert a {@link NamespacedKey} to the given type
     */
    public FieldsRegistry(final @NotNull Class<T> clazz,
                          final @NotNull FunctionException<NamespacedKey, T> conversionFunction) {
        this.clazz = Objects.requireNonNull(clazz);
        this.conversionFunction = Objects.requireNonNull(conversionFunction);
        this.internalMap = new LinkedHashMap<>();
    }

    @Override
    public @Nullable T get(final @NotNull NamespacedKey key) {
        if (this.internalMap.isEmpty()) {
            Refl<?> refl = new Refl<>(Objects.requireNonNull(this.clazz));
            for (final Field field : refl.getFields(f -> Modifier.isStatic(f.getModifiers()) && f.getType().equals(this.clazz)))
                try {
                    NamespacedKey k = NamespacedKey.minecraft(field.getName().toLowerCase());
                    this.internalMap.put(k, this.conversionFunction.apply(k));
                } catch (Exception e) {
                    throw new RegistryException("Could not convert field " + field.getName(), e);
                }
        }
        return this.internalMap.get(key);
    }

    @Override
    public @NotNull Stream<T> stream() {
        return this.internalMap.values().stream();
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return stream().iterator();
    }

}
