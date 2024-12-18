package it.fulminazzo.jbukkit.inventory;

import lombok.Getter;
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
        return Arrays.copyOfRange(getContents(), STORAGE_SIZE, STORAGE_SIZE + 4);
    }

    @Override
    public @NotNull ItemStack[] getExtraContents() {
        return new ItemStack[STORAGE_SIZE + 4];
    }

    @Override
    public @Nullable ItemStack getHelmet() {
        return getArmorContents()[3];
    }

    @Override
    public @Nullable ItemStack getChestplate() {
        return getArmorContents()[2];
    }

    @Override
    public @Nullable ItemStack getLeggings() {
        return getArmorContents()[1];
    }

    @Override
    public @Nullable ItemStack getBoots() {
        return getArmorContents()[0];
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

    }

    @Override
    public void setChestplate(@Nullable ItemStack chestplate) {

    }

    @Override
    public void setLeggings(@Nullable ItemStack leggings) {

    }

    @Override
    public void setBoots(@Nullable ItemStack boots) {

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
        return null;
    }

    @Override
    public void setItemInOffHand(@Nullable ItemStack item) {

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

    @Getter
    private enum EquipSlot {
        HAND(-1),
        OFF_HAND(STORAGE_SIZE + 4),
        BOOTS(STORAGE_SIZE),
        LEGGINGS(STORAGE_SIZE + 1),
        CHESTPLATE(STORAGE_SIZE + 2),
        HELMET(STORAGE_SIZE + 3),
        ;

        private final int slot;

        EquipSlot(int slot) {
            this.slot = slot;
        }
    }
}
