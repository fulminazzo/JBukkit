package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.interfaces.functions.FunctionException;
import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.jbukkit.enchantments.MockEnchantment;
import it.fulminazzo.jbukkit.potion.MockPotionEffectType;
import it.fulminazzo.jbukkit.registries.FieldsRegistry;
import it.fulminazzo.jbukkit.registries.TypeRegistry;
import lombok.NoArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.BlockType;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Wolf;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Utility class for {@link Registry}.
 */
@SuppressWarnings("UnstableApiUsage")
@NoArgsConstructor
public final class RegistryUtils {
    private static final Map<String, String> FIELD_BY_CLASS_NAME = new HashMap<>();
    // Represents classes with static fields in them.
    private static final Map<String, FunctionException<NamespacedKey, Object>> FIELDS_CLASSES = new HashMap<>();

    static {
        FIELD_BY_CLASS_NAME.put("PatternType", "BannerPattern");
        FIELD_BY_CLASS_NAME.put("CatType", "CatVariant");
        FIELD_BY_CLASS_NAME.put("Particle", "ParticleType");
        FIELD_BY_CLASS_NAME.put("PotionType", "Potion");
        FIELD_BY_CLASS_NAME.put("Sound", "Sounds");
        FIELD_BY_CLASS_NAME.put("MapCursorType", "MapDecorationType");
        FIELD_BY_CLASS_NAME.put("KeyedBossBar", "BossBars");
        FIELD_BY_CLASS_NAME.put("MemoryKey", "MemoryModuleType");

        FIELDS_CLASSES.put("GameEvent", k -> mockKeyed(GameEvent.class, k));
        FIELDS_CLASSES.put("WolfVariant", k -> mockKeyed(Wolf.Variant.class, k));
        FIELDS_CLASSES.put("DamageType", k -> mockKeyed(DamageType.class, k));
        FIELDS_CLASSES.put("TrimPattern", k -> mockKeyed(TrimPattern.class, k));
        FIELDS_CLASSES.put("TrimMaterial", k -> mockKeyed(TrimMaterial.class, k));
        FIELDS_CLASSES.put("StructureType", k -> mockKeyed(StructureType.class, k));
        FIELDS_CLASSES.put("Structure", k -> mockKeyed(Structure.class, k));
        FIELDS_CLASSES.put("PotionEffectType", MockPotionEffectType::getVanillaPotionEffectType);
        FIELDS_CLASSES.put("MusicInstrument", k -> mockKeyed(MusicInstrument.class, k));
        // Necessary to avoid compilation conflicts.
        FIELDS_CLASSES.put("Enchantment", k ->
                new Refl<>(MockEnchantment.class).invokeMethod("getVanillaEnchantment", k));
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
        setupPotionEffectTypes();
    }

    /**
     * Tries loading every {@link PotionEffectType} after setting up the registry.
     */
    private static void setupPotionEffectTypes() {
        Refl<?> potionEffectType = new Refl<>(PotionEffectType.class);
        for (final Field field : potionEffectType.getFields(f -> Modifier.isStatic(f.getModifiers()) &&
                PotionEffectType.class.isAssignableFrom(f.getType()))) {
            potionEffectType.getFieldObject(field);
        }
    }

    /**
     * Gets the most appropriate registry from the given class.
     *
     * @param <T>   the type of the class
     * @param clazz the class
     * @return the registry
     */
    @SuppressWarnings("unchecked")
    public static <T extends Keyed> @NotNull Registry<T> getRegistry(final @NotNull Class<T> clazz) {
        String clazzName = clazz.getSimpleName();
        Class<?> enclosingClass = clazz.getEnclosingClass();
        if (enclosingClass != null) clazzName = enclosingClass.getSimpleName() + clazzName;
        clazzName = FIELD_BY_CLASS_NAME.getOrDefault(clazzName, clazzName);
        if (clazz.equals(BlockType.class)) return new TypeRegistry<>();
        else if (clazz.equals(ItemType.class)) return new TypeRegistry<>();
        // Field registries
        Object converterFunction = FIELDS_CLASSES.get(clazzName);
        if (converterFunction != null)
            return new FieldsRegistry<>(clazz, (FunctionException<NamespacedKey, T>) converterFunction);
        // Default case, registry already initialized.
        clazzName = StringUtils.decapitalize(clazzName);
        Registry<T> registry = new Refl<>(Registry.class).getFieldObject(clazzName);
        if (registry != null) return registry;
        else throw new IllegalArgumentException("Could not create registry for " + clazzName);
    }

    /**
     * Provides a mock for the given class.
     * When invoking the <code>getKey</code> method, the given key will be returned.
     *
     * @param <T>   the type of the mock
     * @param clazz the class
     * @param key   the key
     * @return the mock
     */
    public static <T extends Keyed> T mockKeyed(final @NotNull Class<T> clazz,
                                                final @NotNull NamespacedKey key) {
        T t = mock(clazz);
        when(t.getKey()).thenReturn(key);
        return t;
    }

}
