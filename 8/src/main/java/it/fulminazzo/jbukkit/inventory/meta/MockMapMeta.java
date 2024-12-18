package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.MapMeta;

/**
 * Represents a mock implementation for {@link MapMeta}.
 */
@Getter
@Setter
public class MockMapMeta extends MockItemMeta implements MapMeta {
    private boolean scaling;

    @Override
    public MockMapMeta clone() {
        return (MockMapMeta) super.clone();
    }

}
