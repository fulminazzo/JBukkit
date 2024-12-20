package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mock implementation for {@link BannerMeta}.
 */
@Getter
@Setter
public class MockBannerMeta extends MockItemMeta implements BannerMeta {
    private final List<Pattern> patterns;
    private DyeColor baseColor;

    /**
     * Instantiates a new Mock banner meta.
     */
    public MockBannerMeta() {
        this.patterns = new LinkedList<>();
    }

    @Override
    public void setPatterns(List<Pattern> patterns) {
        this.patterns.clear();
        if (patterns != null)
            this.patterns.addAll(patterns);
    }

    @Override
    public void addPattern(Pattern pattern) {
        this.patterns.add(pattern);
    }

    @Override
    public Pattern getPattern(int i) {
        return this.patterns.get(i);
    }

    @Override
    public Pattern removePattern(int i) {
        return this.patterns.remove(i);
    }

    @Override
    public void setPattern(int i, Pattern pattern) {
        this.patterns.set(i, pattern);
    }

    @Override
    public int numberOfPatterns() {
        return this.patterns.size();
    }

    @Override
    public MockBannerMeta clone() {
        return (MockBannerMeta) super.clone();
    }

}
