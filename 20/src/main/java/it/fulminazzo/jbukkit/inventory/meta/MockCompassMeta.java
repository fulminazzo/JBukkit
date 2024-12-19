package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock implementation for {@link MockCompassMeta}.
 */
@Getter
@Setter
public class MockCompassMeta extends MockItemMeta implements CompassMeta {
    private Location lodestone;
    private boolean lodestoneTracked;

    @Override
    public boolean hasLodestone() {
        return this.lodestone != null;
    }

    @Override
    public @NotNull MockCompassMeta clone() {
        return (MockCompassMeta) super.clone();
    }

}
