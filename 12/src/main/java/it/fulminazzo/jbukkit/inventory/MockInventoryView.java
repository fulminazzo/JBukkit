package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class MockInventoryView extends InventoryView {
    private final Inventory topInventory;
    private final Player player;

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
