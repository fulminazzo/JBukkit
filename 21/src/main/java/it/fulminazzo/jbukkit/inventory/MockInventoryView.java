package it.fulminazzo.jbukkit.inventory;

import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.mockito.Mockito.when;

/**
 * Represents a mock implementation of {@link InventoryView}.
 */
@Getter
@Setter
public class MockInventoryView implements InventoryView {
    private final @NotNull Inventory topInventory;
    private final @NotNull Player player;
    private final @NotNull String originalTitle;
    @Setter
    private @NotNull String title;
    private ItemStack cursor;

    /**
     * Instantiates a new Mock inventory view.
     *
     * @param topInventory the inventory displayed
     * @param player       the player viewing it
     * @param title        the title
     */
    public MockInventoryView(final @NotNull Inventory topInventory, final @NotNull Player player,
                             final @NotNull String title) {
        this.topInventory = topInventory;
        this.player = player;
        this.title = title;
        this.originalTitle = title;
        if (player.getClass().getCanonicalName().contains("MockitoMock"))
            when(player.getOpenInventory()).thenReturn(this);
    }

    @Override
    public @NotNull Inventory getBottomInventory() {
        return this.player.getInventory();
    }

    @Override
    public @NotNull InventoryType getType() {
        return this.topInventory.getType();
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
        return rawSlot < this.topInventory.getSize() ? this.topInventory : this.player.getInventory();
    }

    @Override
    public int convertSlot(int rawSlot) {
        return rawSlot < this.topInventory.getSize() ? rawSlot : rawSlot - this.topInventory.getSize();
    }

    @Override
    public @NotNull InventoryType.SlotType getSlotType(int slot) {
        return InventoryType.SlotType.CONTAINER;
    }

    @Override
    public void close() {
        this.player.closeInventory();
    }

    @Override
    public int countSlots() {
        return this.topInventory.getSize() + this.player.getInventory().getSize();
    }

    @Override
    public boolean setProperty(@NotNull InventoryView.Property prop, int value) {
        throw new NotImplementedException();
    }

}
