package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
@Setter
public class MockInventory implements Inventory {
    private ItemStack[] contents;
    private final String name;
    private int maxStackSize;

    public MockInventory(final int size) {
        this.contents = new ItemStack[size];
        this.name = "";
    }

    @Override
    public int getSize() {
        return this.contents.length;
    }

    @Override
    public int getMaxStackSize() {
        return Arrays.stream(this.contents)
                .filter(Objects::nonNull)
                .mapToInt(ItemStack::getMaxStackSize)
                .max().orElse(this.maxStackSize);
    }

    @Override
    public @Nullable ItemStack getItem(int index) {
        return contents[index];
    }

    @Override
    public void setItem(int index, final @Nullable ItemStack itemStack) {
        contents[index] = itemStack;
    }

    // Copied from actual Spigot code
    private int firstPartial(ItemStack item) {
        ItemStack[] inventory = this.getStorageContents();
        if (item == null) return -1;
        ItemStack filteredItem = item.clone();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack cItem = inventory[i];
            if (cItem != null && cItem.getAmount() < cItem.getMaxStackSize() && cItem.isSimilar(filteredItem)) {
                return i;
            }
        }
        return -1;
    }

    // Copied from actual Spigot code
    @Override
    public HashMap<Integer, ItemStack> addItem(final ItemStack @NotNull ... itemStacks) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> leftover = new HashMap<>();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            while (true) {
                // Do we already have a stack of it?
                int firstPartial = this.firstPartial(item);

                // Drat! no partial stack
                if (firstPartial == -1) {
                    // Find a free spot!
                    int firstFree = this.firstEmpty();

                    if (firstFree == -1) {
                        // No space at all!
                        leftover.put(i, item);
                        break;
                    } else {
                        // More than a single stack!
                        if (item.getAmount() > getMaxStackSize()) {
                            ItemStack stack = item.clone();
                            stack.setAmount(getMaxStackSize());
                            this.setItem(firstFree, stack);
                            item.setAmount(item.getAmount() - getMaxStackSize());
                        } else {
                            // Just store it
                            this.setItem(firstFree, item);
                            break;
                        }
                    }
                } else {
                    // So, apparently it might only partially fit, well lets do just that
                    ItemStack partialItem = this.getItem(firstPartial);

                    int amount = item.getAmount();
                    int partialAmount = partialItem.getAmount();
                    int maxAmount = partialItem.getMaxStackSize();

                    // Check if it fully fits
                    if (amount + partialAmount <= maxAmount) {
                        partialItem.setAmount(amount + partialAmount);
                        // To make sure the packet is sent to the client
                        this.setItem(firstPartial, partialItem);
                        break;
                    }

                    // It fits partially
                    partialItem.setAmount(maxAmount);
                    // To make sure the packet is sent to the client
                    this.setItem(firstPartial, partialItem);
                    item.setAmount(amount + partialAmount - maxAmount);
                }
            }
        }
        return leftover;
    }

    // Copied from actual Spigot code
    private int first(ItemStack item, boolean withAmount, ItemStack[] inventory) {
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) continue;

            if (withAmount ? item.equals(inventory[i]) : item.isSimilar(inventory[i])) {
                return i;
            }
        }
        return -1;
    }

    // Copied from actual Spigot code
    @Override
    public HashMap<Integer, ItemStack> removeItem(final ItemStack @NotNull ... itemStacks) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            int toDelete = item.getAmount();

            while (true) {
                // Paper start - Allow searching entire contents
                ItemStack[] toSearch = getContents();
                int first = this.first(item, false, toSearch);
                // Paper end

                // Drat! we don't have this type in the inventory
                if (first == -1) {
                    item.setAmount(toDelete);
                    leftover.put(i, item);
                    break;
                } else {
                    ItemStack itemStack = this.getItem(first);
                    int amount = itemStack.getAmount();

                    if (amount <= toDelete) {
                        toDelete -= amount;
                        // clear the slot, all used up
                        this.clear(first);
                    } else {
                        // split the stack and store
                        itemStack.setAmount(amount - toDelete);
                        this.setItem(first, itemStack);
                        toDelete = 0;
                    }
                }

                // Bail when done
                if (toDelete <= 0) {
                    break;
                }
            }
        }
        return leftover;
    }

    @Override
    public @NotNull ItemStack[] getStorageContents() {
        return new ItemStack[0];
    }

    @Override
    public void setStorageContents(@NotNull ItemStack[] items) throws IllegalArgumentException {

    }

    public boolean contains(int i) {
        return i >= 0 && i < getSize();
    }

    @Override
    public boolean contains(final @NotNull Material material) throws IllegalArgumentException {
        return Arrays.stream(getContents()).anyMatch(i -> i.getType().equals(material));
    }

    @Override
    public boolean contains(final @Nullable ItemStack itemStack) {
        return itemStack != null && Arrays.stream(getContents()).anyMatch(i -> i.equals(itemStack));
    }

}
