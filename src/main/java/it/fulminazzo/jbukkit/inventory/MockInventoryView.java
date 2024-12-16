package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
@Setter
public class MockInventoryView implements InventoryView {
    private Inventory topInventory;
    private final PlayerInventory bottomInventory;
    private String title;
    private final String originalTitle;
    private ItemStack cursor;

    public MockInventoryView(PlayerInventory bottomInventory) {
        this(bottomInventory, "");
    }

    public MockInventoryView(PlayerInventory bottomInventory, String originalTitle) {
        this(bottomInventory, null, originalTitle);
    }

    public MockInventoryView(PlayerInventory bottomInventory, int size) {
        this(bottomInventory, new MockInventory(size));
    }

    public MockInventoryView(PlayerInventory bottomInventory, Inventory topInventory) {
        this(bottomInventory, topInventory, "");
    }

    public MockInventoryView(PlayerInventory bottomInventory, int size, String originalTitle) {
        this(bottomInventory, new MockInventory(size), originalTitle);
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

    @Override
    public void setItem(int slot, @Nullable ItemStack item) {
        getInventory(slot).setItem(convertSlot(slot), item);
    }

    @Override
    public @Nullable ItemStack getItem(int slot) {
        return getInventory(slot).getItem(convertSlot(slot));
    }

    @Override
    public @Nullable Inventory getInventory(int rawSlot) {
        return rawSlot >= getTopInventory().getSize() ? getBottomInventory() : getTopInventory();
    }

    @Override
    public int convertSlot(int rawSlot) {
        return rawSlot >= getTopInventory().getSize() ? rawSlot - getTopInventory().getSize() : rawSlot;
    }

    @Override
    public @NotNull InventoryType.SlotType getSlotType(int slot) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void close() {
        getPlayer().closeInventory();
    }

    @Override
    public int countSlots() {
        return getTopInventory().getSize() + getBottomInventory().getSize();
    }

    @Override
    public boolean setProperty(@NotNull InventoryView.Property prop, int value) {
        throw new IllegalStateException("Not implemented");
    }
}
