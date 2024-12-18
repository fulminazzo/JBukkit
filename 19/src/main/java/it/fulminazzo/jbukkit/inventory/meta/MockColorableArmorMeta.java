package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

/**
 * Represents a mock implementation for {@link ColorableArmorMeta}.
 */
@Getter
@Setter
public class MockColorableArmorMeta extends MockLeatherArmorMeta implements ColorableArmorMeta {
    private ArmorTrim trim;

    @Override
    public boolean hasTrim() {
        return this.trim != null;
    }

    @Override
    public MockColorableArmorMeta clone() {
        return (MockColorableArmorMeta) super.clone();
    }

}
