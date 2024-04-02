package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.jbukkit.enchantments.MockEnchantment;
import it.fulminazzo.jbukkit.inventory.MockItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BukkitUtils {
    private static final List<Recipe> RECIPES = new LinkedList<>();

    public static void setupServer() {
        Server server = mock(Server.class);
        when(server.addRecipe(any())).thenAnswer(r -> RECIPES.add(r.getArgument(0)));
        when(server.recipeIterator()).thenAnswer(r -> RECIPES.iterator());
        when(server.getItemFactory()).thenReturn(new MockItemFactory());
        new Refl<>(Bukkit.class).setFieldObject("server", server);
    }

    public static void setupEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        for (Field field : Enchantment.class.getDeclaredFields())
            if (field.getType().equals(Enchantment.class))
                try {
                    Enchantment enchant = (Enchantment) field.get(Enchantment.class);
                    enchantments.add(new MockEnchantment(enchant.getKey()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
        // Register enchantments
        Map<NamespacedKey, Enchantment> byKey = new Refl<>(Enchantment.class).getFieldObject("byKey");
        if (byKey != null) enchantments.forEach(e -> byKey.put(e.getKey(), e));
    }
}
