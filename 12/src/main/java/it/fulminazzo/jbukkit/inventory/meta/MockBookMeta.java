package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.BaseComponentSerializer;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mock implementation for {@link BookMeta}.
 */
@Getter
@Setter
public class MockBookMeta extends MockItemMeta implements BookMeta {
    private final List<String> pages;
    private String title;
    private String author;
    private Generation generation;

    /**
     * Instantiates a new Mock book meta.
     */
    public MockBookMeta() {
        this.pages = new LinkedList<>();
    }

    public boolean setTitle(String title) {
        this.title = title;
        return true;
    }

    @Override
    public boolean hasTitle() {
        return this.title != null;
    }

    @Override
    public boolean hasAuthor() {
        return this.author != null;
    }

    @Override
    public boolean hasGeneration() {
        return this.generation != null;
    }

    @Override
    public boolean hasPages() {
        return !this.pages.isEmpty();
    }

    @Override
    public String getPage(int page) {
        return this.pages.get(page);
    }

    @Override
    public void setPage(int page, String data) {
        this.pages.set(page, data);
    }

    @Override
    public void setPages(List<String> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
    }

    @Override
    public void setPages(String... pages) {
        setPages(Arrays.asList(pages));
    }

    @Override
    public void addPage(String... pages) {
        this.pages.addAll(Arrays.asList(pages));
    }

    @Override
    public int getPageCount() {
        return this.pages.size();
    }

    @Override
    public MockBookMeta clone() {
        return (MockBookMeta) super.clone();
    }

    @Override
    public BookMeta.Spigot spigot() {
        return new MockSpigot(this);
    }

    private static class MockSpigot extends BookMeta.Spigot {
        private final @NotNull MockBookMeta meta;

        private MockSpigot(final @NotNull MockBookMeta meta) {
            this.meta = meta;
        }

        @Override
        public BaseComponent[] getPage(int page) {
            return null;
        }

        @Override
        public void setPage(int page, BaseComponent... data) {
            super.setPage(page, data);
        }

        @Override
        public List<BaseComponent[]> getPages() {
            return super.getPages();
        }

        @Override
        public void setPages(List<BaseComponent[]> pages) {
            super.setPages(pages);
        }

        @Override
        public void setPages(BaseComponent[]... pages) {
            super.setPages(pages);
        }

        @Override
        public void addPage(BaseComponent[]... pages) {
            super.addPage(pages);
        }

        @Override
        public boolean isUnbreakable() {
            return this.meta.isUnbreakable();
        }

        @Override
        public void setUnbreakable(boolean unbreakable) {
            this.meta.setUnbreakable(unbreakable);
        }

    }

}
