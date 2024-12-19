package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mock implementation for {@link CrossbowMeta}.
 */
@Getter
public class MockCrossbowMeta extends MockItemMeta implements CrossbowMeta {
    private final List<ItemStack> chargedProjectiles;

    /**
     * Instantiates a new Mock crossbow meta.
     */
    public MockCrossbowMeta() {
        this.chargedProjectiles = new ArrayList<>();
    }

    @Override
    public boolean hasChargedProjectiles() {
        return !this.chargedProjectiles.isEmpty();
    }

    @Override
    public void setChargedProjectiles(@Nullable List<ItemStack> projectiles) {
        this.chargedProjectiles.clear();
        if (projectiles != null) this.chargedProjectiles.addAll(projectiles);
    }

    @Override
    public void addChargedProjectile(@NotNull ItemStack item) {
        this.chargedProjectiles.add(item);
    }

}
