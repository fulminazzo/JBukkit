package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock implementation for {@link AxolotlBucketMeta}.
 */
@Getter
@Setter
public class MockAxolotlBucketMeta extends MockItemMeta implements AxolotlBucketMeta {
    private Axolotl.Variant variant;

    @Override
    public boolean hasVariant() {
        return this.variant != null;
    }

    @Override
    public @NotNull MockAxolotlBucketMeta clone() {
        return (MockAxolotlBucketMeta) super.clone();
    }

}
