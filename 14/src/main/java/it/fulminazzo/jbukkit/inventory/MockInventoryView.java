package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a mock implementation of {@link InventoryView}.
 */
@Getter
public class MockInventoryView extends InventoryView {
    private final Inventory topInventory;
    private final Player player;

    /**
     * Instantiates a new Mock inventory view.
     *
     * @param topInventory the inventory displayed
     * @param player       the player viewing it
     */
    public MockInventoryView(final @Nullable Inventory topInventory, final @NotNull Player player) {
        this.topInventory = topInventory;
        this.player = player;
    }

    @Override
    public Inventory getBottomInventory() {
        return this.player.getInventory();
    }

    @Override
    public InventoryType getType() {
        return this.topInventory == null ? null : this.topInventory.getType();
    }

}
