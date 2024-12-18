package it.fulminazzo.jbukkit.inventory.meta;

import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock implementation for {@link ItemMeta}.
 */
@Getter
@Setter
public class MockTropicalFishBucketMeta extends MockItemMeta implements TropicalFishBucketMeta {
    private DyeColor patternColor;
    private DyeColor bodyColor;
    private TropicalFish.Pattern pattern;

    @Override
    public boolean hasVariant() {
        throw new NotImplementedException();
    }

    @Override
    public @NotNull MockTropicalFishBucketMeta clone() {
        return (MockTropicalFishBucketMeta) super.clone();
    }

}
