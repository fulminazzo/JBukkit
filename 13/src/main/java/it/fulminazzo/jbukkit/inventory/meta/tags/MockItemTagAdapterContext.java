package it.fulminazzo.jbukkit.inventory.meta.tags;

import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock for {@link ItemTagAdapterContext}.
 */
public class MockItemTagAdapterContext implements ItemTagAdapterContext {

    @Override
    public @NotNull CustomItemTagContainer newTagContainer() {
        return new MockCustomItemTagContainer();
    }

}
