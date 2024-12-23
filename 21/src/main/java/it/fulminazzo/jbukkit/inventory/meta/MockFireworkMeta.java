package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mock implementation for {@link FireworkMeta}.
 */
@Getter
@Setter
public class MockFireworkMeta extends MockItemMeta implements FireworkMeta {
    private final List<FireworkEffect> effects;
    private int power;

    /**
     * Instantiates a new Mock firework meta.
     */
    public MockFireworkMeta() {
        this.effects = new LinkedList<>();
    }

    @Override
    public void addEffect(FireworkEffect effect) throws IllegalArgumentException {
        this.effects.add(effect);
    }

    @Override
    public void addEffects(FireworkEffect... effects) throws IllegalArgumentException {
        this.effects.addAll(Arrays.asList(effects));
    }

    @Override
    public void addEffects(Iterable<FireworkEffect> effects) throws IllegalArgumentException {
        effects.forEach(this.effects::add);
    }

    @Override
    public int getEffectsSize() {
        return this.effects.size();
    }

    @Override
    public void removeEffect(int index) throws IndexOutOfBoundsException {
        this.effects.remove(index);
    }

    @Override
    public void clearEffects() {
        this.effects.clear();
    }

    @Override
    public boolean hasEffects() {
        return !this.effects.isEmpty();
    }

    @Override
    public boolean hasPower() {
        return this.power > 0;
    }

    @Override
    public MockFireworkMeta clone() {
        return (MockFireworkMeta) super.clone();
    }

}
