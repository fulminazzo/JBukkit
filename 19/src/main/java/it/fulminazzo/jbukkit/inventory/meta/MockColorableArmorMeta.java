package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull MockColorableArmorMeta clone() {
        return (MockColorableArmorMeta) super.clone();
    }

}
