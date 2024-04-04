package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.enchantments.MockEnchantment;
import it.fulminazzo.jbukkit.inventory.MockItemFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.Recipe;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BukkitUtils {
    private static final List<Recipe> RECIPES = new LinkedList<>();
    private static final String VERSION_FORMAT = "1\\.(\\d+)\\.(\\d+)-R\\d+\\.\\d+-SNAPSHOT";
    private static final String VERSION_NAME = "minecraft-version";
    private static final String DEFAULT_VERSION = "1.20.4-R0.1-SNAPSHOT";
    @Getter
    private static double numericalVersion;

    public static void setupVersion() {
        String version = System.getenv(VERSION_NAME);
        if (version == null) {
            Logger.getGlobal().warning(String.format("Could not find set version '%s'. Defaulting to value '%s'", VERSION_NAME, DEFAULT_VERSION));
            version = DEFAULT_VERSION;
        }
        Matcher matcher = Pattern.compile(VERSION_FORMAT).matcher(version);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Version '%s' did not match format '%s'", version, VERSION_FORMAT));
        numericalVersion = Double.parseDouble(matcher.group(1) + "." + matcher.group(2));
        Logger.getGlobal().info(String.format("Using version '%s'", numericalVersion));
    }

    public static void setupServer() {
        setupVersion();
        Server server = mock(Server.class);
        if (Arrays.stream(server.getClass().getDeclaredMethods()).anyMatch(m -> m.getName().equals("getRecipe")))
            when(server.getRecipe(any())).thenAnswer(r -> {
                Object key = r.getArgument(0);
                Iterator<Recipe> iterator = Bukkit.recipeIterator();
                while (iterator.hasNext()) {
                    Recipe recipe = iterator.next();
                    if (Objects.equals(new Refl<>(recipe).invokeMethod("getKey"), key)) return recipe;
                }
                return null;
            });
        when(server.getBukkitVersion()).thenReturn(String.format("1.%s-R0.1-SNAPSHOT", numericalVersion));
        when(server.addRecipe(any())).thenAnswer(r -> RECIPES.add(r.getArgument(0)));
        when(server.recipeIterator()).thenAnswer(r -> RECIPES.iterator());
        when(server.getItemFactory()).thenReturn(new MockItemFactory());
        new Refl<>(Bukkit.class).setFieldObject("server", server);
    }

    public static void setupEnchantments() {
        MockEnchantment.setupEnchantments();
    }
}
