package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
@Setter
public class MockInventoryView extends InventoryView {
    private Inventory topInventory;
    private final PlayerInventory bottomInventory;
    private String title;
    private final String originalTitle;

    public MockInventoryView(PlayerInventory bottomInventory) {
        this(bottomInventory, "");
    }

    public MockInventoryView(PlayerInventory bottomInventory, String originalTitle) {
        this(bottomInventory, null, originalTitle);
    }

    public MockInventoryView(PlayerInventory bottomInventory, Inventory topInventory) {
        this(bottomInventory, topInventory, "");
    }

    public MockInventoryView(PlayerInventory bottomInventory, Inventory topInventory, String originalTitle) {
        this.topInventory = topInventory;
        this.bottomInventory = bottomInventory;
        this.title = originalTitle;
        this.originalTitle = originalTitle;
    }

    @NotNull
    @Override
    public HumanEntity getPlayer() {
        return Objects.requireNonNull(this.bottomInventory.getHolder());
    }

    @NotNull
    @Override
    public InventoryType getType() {
        return this.topInventory == null ? InventoryType.PLAYER : this.topInventory.getType();
    }
}
