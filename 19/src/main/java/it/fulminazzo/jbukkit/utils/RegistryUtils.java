package it.fulminazzo.jbukkit.utils;

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

}
