package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Utility class for {@link Registry}.
 */
@NoArgsConstructor
public final class RegistryUtils {

    /**
     * Sets up the registries.
     */
    public static void setupRegistries() {
        when(Bukkit.getServer().getRegistry(any())).thenAnswer(a ->
                getRegistry(a.getArgument(0)));
    }

    /**
     * Gets the most appropriate registry from the given class.
     *
     * @param <T>   the type of the class
     * @param clazz the class
     * @return the registry
     */
    public static <T extends Keyed> Registry<T> getRegistry(final @NotNull Class<T> clazz) {
        String clazzName = clazz.getSimpleName();
        Class<?> enclosingClass = clazz.getEnclosingClass();
        if (enclosingClass != null) clazzName = enclosingClass.getSimpleName() + clazzName;
        switch (clazzName) {
            case "KeyedBossBar":
                clazzName = "BossBars";
                break;
            case "Sound":
                clazzName = "Sounds";
                break;
            case "MemoryKey":
                clazzName = "MemoryModuleType";
                break;
        }
        clazzName = StringUtils.decapitalize(clazzName);
        return new Refl<>(Registry.class).getFieldObject(clazzName);
    }

}
