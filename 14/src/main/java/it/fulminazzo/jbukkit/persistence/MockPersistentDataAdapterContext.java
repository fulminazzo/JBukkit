package it.fulminazzo.jbukkit.persistence;

import it.fulminazzo.jbukkit.Equable;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock for {@link PersistentDataAdapterContext}.
 */
public class MockPersistentDataAdapterContext extends Equable implements PersistentDataAdapterContext {

    @Override
    public @NotNull PersistentDataContainer newPersistentDataContainer() {
        return new MockPersistentDataContainer();
    }

}
