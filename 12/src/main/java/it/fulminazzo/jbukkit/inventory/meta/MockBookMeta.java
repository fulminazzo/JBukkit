package it.fulminazzo.jbukkit.inventory.meta;

import it.fulminazzo.jbukkit.Equable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a mock implementation for {@link BookMeta}.
 */
@Getter
@Setter
@NoArgsConstructor
public class MockBookMeta extends MockItemMeta implements BookMeta {
    private String title;
    private String author;
    private Generation generation;

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
        return !spigot().getPages().isEmpty();
    }

    @Override
    public String getPage(int page) {
        return TextComponent.toLegacyText(spigot().getPage(page));
    }

    @Override
    public void setPage(int page, String data) {
        spigot().setPage(page, TextComponent.fromLegacyText(data));
    }

    @Override
    public List<String> getPages() {
        return spigot().getPages().stream().map(TextComponent::toLegacyText).collect(Collectors.toList());
    }

    @Override
    public void setPages(List<String> pages) {
        spigot().setPages(pages.stream().map(TextComponent::fromLegacyText).collect(Collectors.toList()));
    }

    @Override
    public void setPages(String... pages) {
        setPages(Arrays.asList(pages));
    }

    @Override
    public void addPage(String... pages) {
        spigot().addPage(Arrays.stream(pages).map(TextComponent::fromLegacyText).toArray(BaseComponent[][]::new));
    }

    @Override
    public int getPageCount() {
        return spigot().getPages().size();
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
        private final List<BaseComponent[]> pages;

        private MockSpigot(final @NotNull MockBookMeta meta) {
            this.meta = meta;
            this.pages = new LinkedList<>();
        }

        @Override
        public BaseComponent[] getPage(int page) {
            return this.pages.get(page);
        }

        @Override
        public void setPage(int page, BaseComponent... data) {
            this.pages.set(page, data);
        }

        @Override
        public List<BaseComponent[]> getPages() {
            return this.pages;
        }

        @Override
        public void setPages(List<BaseComponent[]> pages) {
            this.pages.clear();
            if (pages != null)
                this.pages.addAll(pages);
        }

        @Override
        public void setPages(BaseComponent[]... pages) {
            setPages(Arrays.asList(pages));
        }

        @Override
        public void addPage(BaseComponent[]... pages) {
            this.pages.addAll(Arrays.asList(pages));
        }

        @Override
        public boolean isUnbreakable() {
            return this.meta.isUnbreakable();
        }

        @Override
        public void setUnbreakable(boolean unbreakable) {
            this.meta.setUnbreakable(unbreakable);
        }

        @Override
        public boolean equals(Object o) {
            return Equable.equals(this, o);
        }

    }

}
