package it.fulminazzo.jbukkit.inventory.meta.tags;

import lombok.Getter;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.jetbrains.annotations.NotNull;

@Getter
public class MockCustomItemTagContainer implements CustomItemTagContainer {
    private final ItemTagAdapterContext adapterContext;

    public MockCustomItemTagContainer() {
        this.adapterContext = new MockItemTagAdapterContext();
    }

    public static class MockItemTagAdapterContext implements ItemTagAdapterContext {

        @Override
        public @NotNull CustomItemTagContainer newTagContainer() {
            return new MockCustomItemTagContainer();
        }

    }

}
