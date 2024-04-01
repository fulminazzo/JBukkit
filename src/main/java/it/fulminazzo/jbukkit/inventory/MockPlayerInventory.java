package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MockPlayerInventory extends MockInventory implements PlayerInventory {
    private static final int MAX_PLAYER_SIZE = 41;
    private static final int HOT_BAR_SIZE = 9;
    private static final int STORAGE_SIZE = 27;
    private static final int ARMOR_SIZE = 4;
    private static final Map<EquipmentSlot, Integer> SLOT_CONVERTER = new HashMap<EquipmentSlot, Integer>(){{
        put(EquipmentSlot.OFF_HAND, MAX_PLAYER_SIZE - 1);
        put(EquipmentSlot.FEET, HOT_BAR_SIZE + STORAGE_SIZE);
        put(EquipmentSlot.LEGS, HOT_BAR_SIZE + STORAGE_SIZE + 1);
        put(EquipmentSlot.CHEST, HOT_BAR_SIZE + STORAGE_SIZE + 2);
        put(EquipmentSlot.HEAD, HOT_BAR_SIZE + STORAGE_SIZE + 3);
    }};
    private Player holder;
    private int heldItemSlot;

    public MockPlayerInventory() {
        super(MAX_PLAYER_SIZE);
    }

    @NotNull
    public ItemStack[] getArmorContents() {
        return cutContents(HOT_BAR_SIZE + STORAGE_SIZE, getSize() - 1);
    }

    @NotNull
    public ItemStack[] getExtraContents() {
        return cutContents(HOT_BAR_SIZE + STORAGE_SIZE);
    }

    @Nullable
    public ItemStack getHelmet() {
        return getItem(EquipmentSlot.HEAD);
    }

    @Nullable
    public ItemStack getChestplate() {
        return getItem(EquipmentSlot.CHEST);
    }

    @Nullable
    public ItemStack getLeggings() {
        return getItem(EquipmentSlot.LEGS);
    }

    @Nullable
    public ItemStack getBoots() {
        return getItem(EquipmentSlot.FEET);
    }

    public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item) {
        setItem(convertSlot(slot), item);
    }

    @Nullable
    public ItemStack getItem(@NotNull EquipmentSlot slot) {
        return getItem(convertSlot(slot));
    }

    public void setArmorContents(@Nullable ItemStack[] items) {
        for (int i = 0; i < ARMOR_SIZE; i++) setItem(i + HOT_BAR_SIZE + STORAGE_SIZE, items[i]);
    }

    public void setExtraContents(@Nullable ItemStack[] items) {
        for (int i = 0; i < ARMOR_SIZE + 1; i++) setItem(i + HOT_BAR_SIZE + STORAGE_SIZE, items[i]);
    }

    public void setHelmet(@Nullable ItemStack helmet) {
        setItem(convertSlot(EquipmentSlot.HEAD), helmet);
    }

    public void setChestplate(@Nullable ItemStack chestplate) {
        setItem(convertSlot(EquipmentSlot.CHEST), chestplate);
    }

    public void setLeggings(@Nullable ItemStack leggings) {
        setItem(convertSlot(EquipmentSlot.LEGS), leggings);
    }

    public void setBoots(@Nullable ItemStack boots) {
        setItem(convertSlot(EquipmentSlot.FEET), boots);
    }

    @NotNull
    public ItemStack getItemInMainHand() {
        ItemStack itemStack = getItem(convertSlot(EquipmentSlot.HAND));
        if (itemStack == null) {
            setItem(EquipmentSlot.HAND, new ItemStack(Material.AIR));
            return getItemInMainHand();
        } else return itemStack;
    }

    public void setItemInMainHand(@Nullable ItemStack item) {
        setItem(EquipmentSlot.HAND, item);
    }

    @NotNull
    public ItemStack getItemInOffHand() {
        ItemStack itemStack = getItem(convertSlot(EquipmentSlot.OFF_HAND));
        if (itemStack == null) {
            setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));
            return getItemInMainHand();
        } else return itemStack;
    }

    public void setItemInOffHand(@Nullable ItemStack item) {
        setItem(EquipmentSlot.OFF_HAND, item);
    }

    @NotNull
    public ItemStack getItemInHand() {
        return getItemInMainHand();
    }

    public void setItemInHand(@Nullable ItemStack stack) {
        setItemInMainHand(stack);
    }
    
    private int convertSlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.HAND) return this.heldItemSlot;
        else return SLOT_CONVERTER.get(slot);
    }

    private ItemStack[] cutContents(int min) {
        return Arrays.copyOfRange(this.contents, min, getSize());
    }

    private ItemStack[] cutContents(int min, int max) {
        return Arrays.copyOfRange(this.contents, min, max);
    }
}
