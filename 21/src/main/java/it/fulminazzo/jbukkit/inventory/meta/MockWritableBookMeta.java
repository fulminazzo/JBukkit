package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import org.bukkit.inventory.meta.WritableBookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mock implementation for {@link WritableBookMeta}.
 */
@Getter
public class MockWritableBookMeta extends MockItemMeta implements WritableBookMeta {
    private final List<String> pages;

    public MockWritableBookMeta() {
        this.pages = new LinkedList<>();
    }

    @Override
    public boolean hasPages() {
        return !this.pages.isEmpty();
    }

    @Override
    public @NotNull String getPage(int page) {
        return this.pages.get(page);
    }

    @Override
    public void setPage(int page, @NotNull String data) {
        this.pages.set(page, data);
    }

    @Override
    public void setPages(@NotNull List<String> pages) {
        this.pages.clear();
        this.pages.addAll(Objects.requireNonNull(pages));
    }

    @Override
    public void setPages(@NotNull String... pages) {
        setPages(Arrays.asList(pages));
    }

    @Override
    public void addPage(@NotNull String... pages) {
        this.pages.addAll(Arrays.asList(pages));
    }

    @Override
    public int getPageCount() {
        return this.pages.size();
    }

    @Override
    public @NotNull MockWritableBookMeta clone() {
        return (MockWritableBookMeta) super.clone();
    }

}
