package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Represents a mock implementation for {@link LeatherArmorMeta}.
 */
@Getter
@Setter
public class MockLeatherArmorMeta extends MockItemMeta implements LeatherArmorMeta {
    private Color color;

    @Override
    public MockLeatherArmorMeta clone() {
        return (MockLeatherArmorMeta) super.clone();
    }

}
