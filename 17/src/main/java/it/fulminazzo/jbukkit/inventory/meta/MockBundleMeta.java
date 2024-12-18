package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mock implementation for {@link BundleMeta}.
 */
@Getter
@Setter
public class MockBundleMeta extends MockItemMeta implements BundleMeta {
    private final List<ItemStack> items;

    /**
     * Instantiates a new Mock bundle meta.
     */
    public MockBundleMeta() {
        this.items = new LinkedList<>();
    }

    @Override
    public boolean hasItems() {
        return !this.items.isEmpty();
    }

    @Override
    public void setItems(@Nullable List<ItemStack> list) {
        this.items.clear();
        if (list != null) this.items.addAll(list);
    }

    @Override
    public void addItem(@NotNull ItemStack itemStack) {
        this.items.add(itemStack);
    }

}
