package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.interfaces.functions.FunctionException;
import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.jbukkit.registries.FieldsRegistry;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Utility class for {@link Registry}.
 */
@NoArgsConstructor
public final class RegistryUtils {
    private static final Map<String, String> FIELD_BY_CLASS_NAME = new HashMap<>();
    // Represents classes with static fields in them.
    private static final Map<String, Function<NamespacedKey, Object>> FIELDS_CLASSES = new HashMap<>();

    static {
        FIELD_BY_CLASS_NAME.put("PatternType", "BannerPattern");
        FIELD_BY_CLASS_NAME.put("CatType", "CatVariant");
        FIELD_BY_CLASS_NAME.put("Particle", "ParticleType");
        FIELD_BY_CLASS_NAME.put("PotionType", "Potion");
        FIELD_BY_CLASS_NAME.put("Sound", "Sounds");
        FIELD_BY_CLASS_NAME.put("MapCursorType", "MapDecorationType");
        FIELD_BY_CLASS_NAME.put("KeyedBossBar", "BossBars");
        FIELD_BY_CLASS_NAME.put("MemoryKey", "MemoryModuleType");

        FIELDS_CLASSES.put("GameEvent", null);
        FIELDS_CLASSES.put("WolfVariant", null);
        FIELDS_CLASSES.put("DamageType", null);
        FIELDS_CLASSES.put("TrimPattern", null);
        FIELDS_CLASSES.put("TrimMaterial", null);
        FIELDS_CLASSES.put("StructureType", null);
        FIELDS_CLASSES.put("Structure", null);
        FIELDS_CLASSES.put("PotionEffectType", null);
        FIELDS_CLASSES.put("MusicInstrument", null);
        FIELDS_CLASSES.put("Enchantment", null);
    }

    /**
     * Sets up the registries.
     */
    public static void setupRegistries() {
        when(Bukkit.getServer().getRegistry(any())).thenAnswer(a -> {
            Class<Keyed> clazz = a.getArgument(0);
            if (clazz == null) return null;
            else return getRegistry(clazz);
        });
    }

    /**
     * Gets the most appropriate registry from the given class.
     *
     * @param <T>   the type of the class
     * @param clazz the class
     * @return the registry
     */
    @SuppressWarnings("unchecked")
    public static <T extends Keyed> Registry<T> getRegistry(final @NotNull Class<T> clazz) {
        String clazzName = clazz.getSimpleName();
        Class<?> enclosingClass = clazz.getEnclosingClass();
        if (enclosingClass != null) clazzName = enclosingClass.getSimpleName() + clazzName;
        clazzName = FIELD_BY_CLASS_NAME.getOrDefault(clazzName, clazzName);
        clazzName = StringUtils.decapitalize(clazzName);
        // Field registries
        Function<NamespacedKey, Object> converterFunction = FIELDS_CLASSES.get(clazzName);
        if (converterFunction != null)
            return new FieldsRegistry<>(clazz, (FunctionException<NamespacedKey, T>) converterFunction);
        // Default case, registry already initialized.
        try {
            Registry<T> registry = new Refl<>(Registry.class).getFieldObject(clazzName);
            if (registry != null) return registry;
            else throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            //TODO: temporary for testing purposes
            e.printStackTrace();
            return mock(Registry.class);
        }
    }

}
