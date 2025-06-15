package it.fulminazzo.jbukkit.inventory;

import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an implementation of {@link PlayerInventory}.
 * Uses {@link MockInventory}.
 */
@Getter
@Setter
public class MockPlayerInventory extends MockInventory implements PlayerInventory {
    private static final int STORAGE_SIZE = 35;
    private int heldItemSlot;
    private final Player holder;

    /**
     * Instantiates a new Mock player inventory.
     *
     * @param holder the player owner
     */
    public MockPlayerInventory(final @NotNull Player holder) {
        super((Arrays.stream(EquipmentSlot.values()).anyMatch(v -> v.name().equals("OFF_HAND")) ?
                EquipSlot.OFF_HAND.slot : EquipSlot.HELMET.slot) + 1);
        this.holder = Objects.requireNonNull(holder);
    }

    @Override
    public void setHolder(@Nullable InventoryHolder holder) {
        throw new IllegalStateException("Cannot invoke setHolder for PlayerInventory");
    }

    @Override
    public @Nullable HumanEntity getHolder() {
        return this.holder;
    }

    @Override
    public @NotNull ItemStack[] getArmorContents() {
        return Arrays.copyOfRange(getContents(), EquipSlot.BOOTS.slot, EquipSlot.OFF_HAND.slot);
    }

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

    public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item) {
        setItem(EquipSlot.fromEquipmentSlot(slot).slot, item);
    }

    public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot) {
        ItemStack itemStack = getItem(EquipSlot.fromEquipmentSlot(slot).slot);
        if (itemStack == null) itemStack = new ItemStack(Material.AIR);
        return itemStack;
    }

    @Override
    public void setArmorContents(@Nullable ItemStack[] items) {
        setContents(items, EquipSlot.BOOTS.slot, EquipSlot.OFF_HAND.slot);
    }

    public void setExtraContents(@Nullable ItemStack[] items) {
        setContents(items, EquipSlot.OFF_HAND.slot, EquipSlot.OFF_HAND.slot + 1);
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

    public @NotNull ItemStack getItemInMainHand() {
        ItemStack itemStack = getItem(getHeldItemSlot());
        if (itemStack == null) return new ItemStack(Material.AIR);
        return itemStack;
    }

    public void setItemInMainHand(@Nullable ItemStack item) {
        setItem(getHeldItemSlot(), item);
    }

    public @NotNull ItemStack getItemInOffHand() {
        ItemStack itemStack = getItem(EquipSlot.OFF_HAND.slot);
        if (itemStack == null) return new ItemStack(Material.AIR);
        return itemStack;
    }

    public void setItemInOffHand(@Nullable ItemStack item) {
        setItem(EquipSlot.OFF_HAND.slot, item);
    }

    @Override
    public @NotNull ItemStack getItemInHand() {
        ItemStack itemStack = getItem(getHeldItemSlot());
        if (itemStack == null) return new ItemStack(Material.AIR);
        return itemStack;
    }

    @Override
    public void setItemInHand(@Nullable ItemStack stack) {
        setItem(getHeldItemSlot(), stack);
    }

    @Override
    protected int getStorageContentsSize() {
        return 36;
    }

    /**
     * Clear int.
     *
     * @param id   the id
     * @param data the data
     * @return the int
     */
    public int clear(int id, int data) {
        throw new NotImplementedException();
    }

    private enum EquipSlot {
        /**
         * Off hand equip slot.
         */
        OFF_HAND(STORAGE_SIZE + 4, "OFF_HAND"),
        /**
         * Boots equip slot.
         */
        BOOTS(STORAGE_SIZE, "FEET"),
        /**
         * Leggings equip slot.
         */
        LEGGINGS(STORAGE_SIZE + 1, "LEGS"),
        /**
         * Chestplate equip slot.
         */
        CHESTPLATE(STORAGE_SIZE + 2, "CHEST"),
        /**
         * Helmet equip slot.
         */
        HELMET(STORAGE_SIZE + 3, "HEAD"),
        ;

        private final int slot;
        // String necessary for compatibility reasons
        private final String toEquipmentSlot;

        EquipSlot(final int slot, final @NotNull String toEquipmentSlot) {
            this.slot = slot;
            this.toEquipmentSlot = toEquipmentSlot;
        }

        /**
         * Converts {@link EquipmentSlot} to {@link EquipSlot}.
         *
         * @param equipmentSlot the equipment slot
         * @return the equip slot
         */
        public static @NotNull EquipSlot fromEquipmentSlot(final @NotNull EquipmentSlot equipmentSlot) {
            for (EquipSlot equipSlot : values()) 
                if (equipSlot.toEquipmentSlot.equals(equipmentSlot.name()))
                    return equipSlot;
            throw new IllegalArgumentException("Unknown equip slot " + equipmentSlot);
        }

    }
    
}
