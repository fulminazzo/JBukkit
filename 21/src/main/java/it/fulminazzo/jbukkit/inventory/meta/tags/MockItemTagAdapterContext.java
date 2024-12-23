package it.fulminazzo.jbukkit.inventory.meta.tags;

import it.fulminazzo.jbukkit.Equable;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock for {@link ItemTagAdapterContext}.
 */
public class MockItemTagAdapterContext extends Equable implements ItemTagAdapterContext {

    @Override
    public @NotNull CustomItemTagContainer newTagContainer() {
        return new MockCustomItemTagContainer();
    }

}
