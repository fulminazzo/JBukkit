package it.fulminazzo.jbukkit.inventory;

import it.fulminazzo.jbukkit.NotImplementedException;
import it.fulminazzo.jbukkit.utils.MaterialUtils;
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

/**
 * Represents an implementation for {@link Inventory}.
 */
@Getter
@Setter
public class MockInventory implements Inventory {
    private final ItemStack[] contents;
    private final InventoryType type;
    @Getter
    private String title;
    private int maxStackSize;
    private final List<HumanEntity> viewers;
    private @Nullable InventoryHolder holder;

    /**
     * Instantiates a new Mock inventory.
     *
     * @param size the size
     */
    public MockInventory(final int size) {
        this.contents = new ItemStack[size];
        this.type = InventoryType.CHEST;
        this.title = null;
        this.viewers = new LinkedList<>();
    }

    @Override
    public void setContents(@NotNull ItemStack[] items) throws IllegalArgumentException {
        setContents(items, 0, items.length);
    }

    /**
     * Sets contents from the start index to the end index.
     *
     * @param items the items
     * @param start the start
     * @param end   the end
     */
    protected void setContents(@Nullable ItemStack[] items, int start, int end) {
        if (items == null)
            throw new IllegalArgumentException("items cannot be null");
        int size = end - start;
        if (items.length != size)
            throw new IllegalArgumentException("Cannot set contents of " + items.length + " items when size is " + size);
        System.arraycopy(items, 0, this.contents, start, items.length);
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
    public @NotNull HashMap<Integer, ItemStack> addItem(final ItemStack @NotNull ... itemStacks) throws IllegalArgumentException {
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
    private int first(ItemStack item, ItemStack[] inventory) {
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) continue;

            if (item.isSimilar(inventory[i])) {
                return i;
            }
        }
        return -1;
    }

    // Copied from actual Spigot code
    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItem(final ItemStack @NotNull ... itemStacks) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> leftover = new HashMap<>();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            int toDelete = item.getAmount();

            while (true) {
                // Paper start - Allow searching entire contents
                ItemStack[] toSearch = getContents();
                int first = this.first(item, toSearch);
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

    public @NotNull ItemStack[] getStorageContents() {
        return Arrays.copyOfRange(this.contents, 0, getStorageContentsSize());
    }

    public void setStorageContents(@NotNull ItemStack[] items) throws IllegalArgumentException {
        if (getStorageContentsSize() >= 0) System.arraycopy(items, 0, this.contents, 0, getStorageContentsSize());
    }

    private int getStorageContentsSize() {
        switch (this.type) {
            case ANVIL:
                return 2;
            case CHEST:
                return getSize();
            case BEACON:
            case FURNACE:
                return 1;
            case HOPPER:
                return 5;
            case PLAYER:
            case CREATIVE:
                return 9 * 4;
            case BREWING:
            case ENCHANTING:
            case MERCHANT:
                throw new NotImplementedException();
            case DROPPER:
            case ENDER_CHEST:
            case WORKBENCH:
            case DISPENSER:
            case CRAFTING:
                return 9;
            default:
                throw new IllegalArgumentException("Could not find storage contents of type: " + this.type);
        }
    }

    /**
     * Verifies if the given index is within bounds of the contents.
     *
     * @param index the index
     * @return true if it is
     */
    public boolean contains(int index) {
        return index >= 0 && index < getSize();
    }

    @Override
    public boolean contains(final @NotNull Material material) throws IllegalArgumentException {
        return Arrays.stream(getContents()).anyMatch(i -> i.getType().equals(material));
    }

    @Override
    public boolean contains(final @Nullable ItemStack itemStack) {
        return itemStack != null && Arrays.asList(getContents()).contains(itemStack);
    }

    /**
     * Legacy method to support id materials.
     * Uses {@link #contains(Material, int)}.
     *
     * @param materialId the material id
     * @param amount     the amount
     * @return the value from the method invocation
     */
    public boolean contains(int materialId, int amount) {
        return contains(MaterialUtils.getMaterial(materialId), amount);
    }

    @Override
    public boolean contains(@NotNull Material material, int amount) throws IllegalArgumentException {
        for (ItemStack itemStack : getContents())
            if (itemStack.getType().equals(material))
                amount -= itemStack.getAmount();
        return amount == 0;
    }

    @Override
    public boolean contains(@Nullable ItemStack item, int amount) {
        if (item == null) return false;
        for (ItemStack itemStack : getContents())
            if (itemStack.equals(item))
                amount -= itemStack.getAmount();
        return amount == 0;
    }

    @Override
    public boolean containsAtLeast(@Nullable ItemStack item, int amount) {
        if (item == null) return false;
        for (ItemStack itemStack : getContents())
            if (itemStack.equals(item)) {
                amount -= itemStack.getAmount();
                if (amount <= 0) return true;
            }
        return false;
    }

    /**
     * Legacy method to support id materials.
     * Uses {@link #all(Material)}.
     *
     * @param materialId the material id
     * @return the value from the method invocation
     */
    public HashMap<Integer, ? extends ItemStack> all(int materialId) {
        return all(MaterialUtils.getMaterial(materialId));
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> result = new HashMap<>();
        for (int i = 0; i < getSize(); i++) {
            ItemStack item = getItem(i);
            if (item != null && item.getType().equals(material))
                result.put(i, item);
        }
        return result;
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack item) {
        HashMap<Integer, ItemStack> result = new HashMap<>();
        if (item == null) return result;
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.equals(item)) result.put(i, itemStack);
        }
        return result;
    }

    /**
     * Legacy method to support id materials.
     * Uses {@link #first(Material)}.
     *
     * @param materialId the material id
     * @return the value from the method invocation
     */
    public int first(int materialId) {
        return first(MaterialUtils.getMaterial(materialId));
    }

    @Override
    public int first(@NotNull Material material) throws IllegalArgumentException {
        for (int i = 0; i < getSize(); i++) {
            ItemStack item = getItem(i);
            if (item != null && item.getType().equals(material))
                return i;
        }
        return -1;
    }

    @Override
    public int first(@NotNull ItemStack item) {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.equals(item))
                return i;
        }
        return -1;
    }

    @Override
    public int firstEmpty() {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack == null) return i;
        }
        return -1;
    }

    /**
     * Legacy method to support id materials.
     * Uses {@link #remove(Material)}.
     *
     * @param materialId the material id
     */
    public void remove(int materialId) {
        remove(MaterialUtils.getMaterial(materialId));
    }

    public boolean isEmpty() {
        return Arrays.stream(this.contents).allMatch(Objects::isNull);
    }

    @Override
    public void remove(@NotNull Material material) throws IllegalArgumentException {
        for (int i = 0; i < getSize(); i++) {
            ItemStack item = getItem(i);
            if (item != null && item.getType().equals(material))
                setItem(i, null);
        }
    }

    @Override
    public void remove(@NotNull ItemStack item) {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.equals(item))
                setItem(i, null);
        }
    }

    @Override
    public void clear(int index) {
        setItem(index, null);
    }

    @Override
    public void clear() {
        setContents(new ItemStack[getSize()]);
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator() {
        return Arrays.asList(this.contents).listIterator();
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator(int index) {
        return Arrays.asList(this.contents).subList(index, getSize()).listIterator();
    }

    public @Nullable Location getLocation() {
        return null;
    }

}
