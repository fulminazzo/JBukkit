package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a mock implementation for {@link MockItemMeta}.
 */
@Getter
public class MockKnowledgeBookMeta extends MockItemMeta implements KnowledgeBookMeta {
    private final List<NamespacedKey> recipes;

    /**
     * Instantiates a new Mock knowledge book meta.
     */
    public MockKnowledgeBookMeta() {
        this.recipes = new ArrayList<>();
    }

    @Override
    public boolean hasRecipes() {
        return !this.recipes.isEmpty();
    }

    @Override
    public void setRecipes(@NotNull List<NamespacedKey> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
    }

    @Override
    public void addRecipe(NamespacedKey @NotNull ... recipes) {
        this.recipes.addAll(Arrays.asList(recipes));
    }

    @Override
    public MockKnowledgeBookMeta clone() {
        return (MockKnowledgeBookMeta) super.clone();
    }

}
