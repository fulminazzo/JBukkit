package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.mockito.Mockito.when;

/**
 * Represents a mock implementation of {@link InventoryView}.
 */
@Getter
public class MockInventoryView extends InventoryView {
    private final @Nullable Inventory topInventory;
    private final @NotNull Player player;

    /**
     * Instantiates a new Mock inventory view.
     *
     * @param topInventory the inventory displayed
     * @param player       the player viewing it
     * @param title        the title
     */
    public MockInventoryView(final @Nullable Inventory topInventory, final @NotNull Player player,
                             final @NotNull String title) {
        this.topInventory = topInventory;
        if (this.topInventory instanceof MockInventory) ((MockInventory) this.topInventory).setTitle(title);
        this.player = player;
        if (player.getClass().getCanonicalName().contains("MockitoMock"))
            when(player.getOpenInventory()).thenReturn(this);
    }

    @Override
    public @NotNull Inventory getBottomInventory() {
        return this.player.getInventory();
    }

    @Override
    public @Nullable InventoryType getType() {
        return this.topInventory == null ? null : this.topInventory.getType();
    }

}
