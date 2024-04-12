package it.fulminazzo.jbukkit.inventory;

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

@Getter
@Setter
public class MockInventory implements Inventory {
    protected final ItemStack[] contents;
    private final List<HumanEntity> viewers = new LinkedList<>();
    private int maxStackSize = 64;
    private InventoryHolder holder;
    private String title;

    public MockInventory(int size) {
        this.contents = new ItemStack[size];
    }

    public int getSize() {
        return this.contents.length;
    }

    public String getName() {
        return getType().name();
    }

    @Nullable
    public ItemStack getItem(int index) {
        return this.contents[index];
    }

    public void setItem(int index, @Nullable ItemStack itemStack) {
        this.contents[index] = itemStack;
    }

    @NotNull
    public HashMap<Integer, ItemStack> addItem(@NotNull ItemStack... items) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> remainders = new HashMap<>();
        main_loop:
        for (int i = 0; i < items.length; i++) {
            ItemStack itemStack = items[i];
            for (int j = 0; j < getSize(); j++) {
                ItemStack is = getItem(j);
                if (is == null || is.getType().equals(Material.AIR)) {
                    setItem(j, itemStack);
                    continue main_loop;
                } else if (itemStack.isSimilar(is)) {
                    int amount = is.getAmount(), curr = itemStack.getAmount();
                    int allowed = Math.min(getMaxStackSize(), is.getMaxStackSize()) - amount;
                    allowed = Math.min(curr, allowed);
                    is.setAmount(amount + allowed);
                    itemStack.setAmount(curr - allowed);
                    if (itemStack.getAmount() == 0) continue main_loop;
                }
            }
            remainders.put(i, itemStack);
        }
        return remainders;
    }

    @NotNull
    public HashMap<Integer, ItemStack> removeItem(@NotNull ItemStack... items) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> remainders = new HashMap<>();
        main_loop:
        for (int i = 0; i < items.length; i++) {
            ItemStack itemStack = items[i];
            for (int j = 0; j < getSize(); j++) {
                ItemStack is = getItem(j);
                if (itemStack.isSimilar(is)) {
                    setItem(j, null);
                    continue main_loop;
                }
            }
            remainders.put(i, itemStack);
        }
        return remainders;
    }

    public void setContents(@NotNull ItemStack[] items) throws IllegalArgumentException {
        if (Math.min(items.length, getSize()) >= 0)
            System.arraycopy(items, 0, this.contents, 0, Math.min(items.length, getSize()));
    }

    @NotNull
    public ItemStack[] getStorageContents() {
        return getContents();
    }

    public void setStorageContents(@NotNull ItemStack[] items) throws IllegalArgumentException {
        setContents(items);
    }

    public boolean contains(int materialId) {
        return contains(MaterialUtils.getMaterial(materialId));
    }

    public boolean contains(@NotNull Material material) throws IllegalArgumentException {
        return Arrays.stream(this.contents).anyMatch(i -> i != null && i.getType().equals(material));
    }

    public boolean contains(@Nullable ItemStack item) {
        return Arrays.stream(this.contents).anyMatch(i -> i != null && i.equals(item));
    }

    public boolean contains(int materialId, int amount) {
        return contains(MaterialUtils.getMaterial(materialId), amount);
    }

    public boolean contains(@NotNull Material material, int amount) throws IllegalArgumentException {
        for (ItemStack i : this.contents) {
            if (i == null) continue;
            if (i.getType().equals(material) && amount <= i.getAmount()) return true;
        }
        return false;
    }

    public boolean contains(@Nullable ItemStack item, int amount) {
        if (item == null) return false;
        for (ItemStack i : this.contents) {
            if (i == null) continue;
            if (i.equals(item) && amount <= i.getAmount()) return true;
        }
        return false;
    }

    public boolean containsAtLeast(@Nullable ItemStack item, int amount) {
        if (item == null) return false;
        for (ItemStack i : this.contents) {
            if (i == null) continue;
            if (i.equals(item)) amount -= i.getAmount();
            if (amount <= 0) return true;
        }
        return false;
    }

    public HashMap<Integer, ? extends ItemStack> all(int materialId) {
        return all(MaterialUtils.getMaterial(materialId));
    }

    @NotNull
    public HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.getType().equals(material)) map.put(i, itemStack);
        }
        return map;
    }

    @NotNull
    public HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack item) {
        HashMap<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.equals(item)) map.put(i, itemStack);
        }
        return map;
    }

    public int first(int materialId) {
        return first(MaterialUtils.getMaterial(materialId));
    }

    public int first(@NotNull Material material) throws IllegalArgumentException {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.getType().equals(material)) return i;
        }
        return -1;
    }

    public int first(@NotNull ItemStack item) {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.equals(item)) return i;
        }
        return -1;
    }

    public int firstEmpty() {
        for (int i = 0; i < getSize(); i++) if (getItem(i) == null) return i;
        return -1;
    }

    public boolean isEmpty() {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.getType() != Material.AIR) return false;
        }
        return true;
    }

    public void remove(int materialId) {
        remove(MaterialUtils.getMaterial(materialId));
    }

    public void remove(@NotNull Material material) throws IllegalArgumentException {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.getType().equals(material)) setItem(i, null);
        }
    }

    public void remove(@NotNull ItemStack item) {
        for (int i = 0; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if (itemStack != null && itemStack.equals(item)) setItem(i, null);
        }
    }

    public void clear(int index) {
        this.contents[index] = null;
    }

    public void clear() {
        for (int i = 0; i < getSize(); i++) this.contents[i] = null;
    }

    public void addViewer(HumanEntity viewer) {
        this.viewers.add(viewer);
    }

    @NotNull
    public InventoryType getType() {
        for (InventoryType t : InventoryType.values())
            if (t.getDefaultSize() == getSize()) return t;
        return InventoryType.CHEST;
    }

    @NotNull
    public ListIterator<ItemStack> iterator() {
        List<ItemStack> itemStacks = Arrays.asList(this.contents);
        return itemStacks.listIterator();
    }

    @NotNull
    public ListIterator<ItemStack> iterator(int index) {
        List<ItemStack> itemStacks = Arrays.asList(this.contents);
        return itemStacks.subList(index, itemStacks.size()).listIterator();
    }

    @Nullable
    public Location getLocation() {
        return null;
    }
}
