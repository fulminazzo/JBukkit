package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.enchantments.MockEnchantment;
import it.fulminazzo.jbukkit.inventory.MockItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.Recipe;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BukkitUtils {
    private static final List<Recipe> RECIPES = new LinkedList<>();

    public static void setupServer() {
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
        when(server.addRecipe(any())).thenAnswer(r -> RECIPES.add(r.getArgument(0)));
        when(server.recipeIterator()).thenAnswer(r -> RECIPES.iterator());
        when(server.getItemFactory()).thenReturn(new MockItemFactory());
        new Refl<>(Bukkit.class).setFieldObject("server", server);
    }

    public static void setupEnchantments() {
        MockEnchantment.setupEnchantments();
    }
}
