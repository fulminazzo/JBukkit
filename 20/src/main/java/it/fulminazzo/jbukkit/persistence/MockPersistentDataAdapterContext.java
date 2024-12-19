package it.fulminazzo.jbukkit.persistence;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock for {@link PersistentDataAdapterContext}.
 */
public class MockPersistentDataAdapterContext implements PersistentDataAdapterContext {

    @Override
    public @NotNull PersistentDataContainer newPersistentDataContainer() {
        return new MockPersistentDataContainer();
    }

}
