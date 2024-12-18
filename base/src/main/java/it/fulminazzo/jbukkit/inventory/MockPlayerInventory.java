package it.fulminazzo.jbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class MockPlayerInventory extends MockInventory implements PlayerInventory {
    private static final int STORAGE_SIZE = 36;

    @Override
    public @NotNull ItemStack[] getArmorContents() {
        return Arrays.copyOfRange(getContents(), EquipSlot.BOOTS.slot, EquipSlot.HELMET.slot);
    }

    @Override
    public @NotNull ItemStack[] getExtraContents() {
        return new ItemStack[EquipSlot.OFF_HAND.slot];
    }

    @Override
    public @Nullable ItemStack getHelmet() {
        return getArmorContents()[EquipSlot.HELMET.slot];
    }

    @Override
    public @Nullable ItemStack getChestplate() {
        return getArmorContents()[EquipSlot.CHESTPLATE.slot];
    }

    @Override
    public @Nullable ItemStack getLeggings() {
        return getArmorContents()[EquipSlot.LEGGINGS.slot];
    }

    @Override
    public @Nullable ItemStack getBoots() {
        return getArmorContents()[EquipSlot.BOOTS.slot];
    }

    @Override
    public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item) {

    }

    @Override
    public @Nullable ItemStack getItem(@NotNull EquipmentSlot slot) {
        return null;
    }

    @Override
    public void setArmorContents(@Nullable ItemStack[] items) {

    }

    @Override
    public void setExtraContents(@Nullable ItemStack[] items) {

    }

    @Override
    public void setHelmet(@Nullable ItemStack helmet) {
        setItem(EquipSlot.HELMET.slot, helmet);
    }

    @Override
    public void setChestplate(@Nullable ItemStack chestplate) {
        setItem(EquipSlot.CHESTPLATE.slot, chestplate);

    }

    @Override
    public void setLeggings(@Nullable ItemStack leggings) {
        setItem(EquipSlot.LEGGINGS.slot, leggings);
    }

    @Override
    public void setBoots(@Nullable ItemStack boots) {
        setItem(EquipSlot.BOOTS.slot, boots);
    }

    @Override
    public @NotNull ItemStack getItemInMainHand() {
        return null;
    }

    @Override
    public void setItemInMainHand(@Nullable ItemStack item) {

    }

    @Override
    public @NotNull ItemStack getItemInOffHand() {
        ItemStack itemStack = getItem(EquipSlot.OFF_HAND.slot);
        if (itemStack == null) return new ItemStack(Material.AIR);
        return itemStack;
    }

    @Override
    public void setItemInOffHand(@Nullable ItemStack item) {
        setItem(EquipSlot.OFF_HAND.slot, item);
    }

    @Override
    public @NotNull ItemStack getItemInHand() {
        return null;
    }

    @Override
    public void setItemInHand(@Nullable ItemStack stack) {

    }

    @Override
    public int getHeldItemSlot() {
        return 0;
    }

    @Override
    public void setHeldItemSlot(int slot) {

    }

    private enum EquipSlot {
        OFF_HAND(STORAGE_SIZE + 4, "OFF_HAND"),
        BOOTS(STORAGE_SIZE, "FEET"),
        LEGGINGS(STORAGE_SIZE + 1, "LEGS"),
        CHESTPLATE(STORAGE_SIZE + 2, "CHEST"),
        HELMET(STORAGE_SIZE + 3, "HEAD"),
        ;

        private final int slot;
        // String necessary for compatibility reasons
        private final String toEquipmentSlot;

        EquipSlot(int slot, String toEquipmentSlot) {
            this.slot = slot;
            this.toEquipmentSlot = toEquipmentSlot;
        }

        public static EquipSlot fromEquipmentSlot(EquipmentSlot equipmentSlot) {
            for (EquipSlot equipSlot : values()) 
                if (equipSlot.toEquipmentSlot.equals(equipmentSlot.name()))
                    return equipSlot;
            throw new IllegalArgumentException("Unknown equip slot " + equipmentSlot);
        }
    }
    
}
