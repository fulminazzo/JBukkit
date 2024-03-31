package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.inventory.MockItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.Recipe;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BukkitUtils {
    private static final List<Recipe> RECIPES = new LinkedList<>();

    public static void setupServer() {
        Server server = mock(Server.class);
        when(server.getRecipe(any())).thenAnswer(r -> {
            NamespacedKey key = r.getArgument(0);
            for (Recipe recipe : RECIPES)
                if (key.equals(new Refl<>(recipe).getFieldObject("key")))
                    return recipe;
            return null;
        });
        when(server.addRecipe(any())).thenAnswer(r -> RECIPES.add(r.getArgument(0)));
        when(server.recipeIterator()).thenAnswer(r -> RECIPES.iterator());
        when(server.getItemFactory()).thenReturn(new MockItemFactory());
        new Refl<>(Bukkit.class).setFieldObject("server", server);
    }
}
