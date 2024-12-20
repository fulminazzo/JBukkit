package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock implementation for {@link ArmorMeta}.
 */
@Getter
@Setter
public class MockArmorMeta extends MockItemMeta implements ArmorMeta {
    private ArmorTrim trim;

    @Override
    public boolean hasTrim() {
        return this.trim != null;
    }

    @Override
    public @NotNull MockArmorMeta clone() {
        return (MockArmorMeta) super.clone();
    }

}
