package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mock implementation for {@link MockSuspiciousStewMeta}.
 */
@Getter
public class MockSuspiciousStewMeta extends MockItemMeta implements SuspiciousStewMeta {
    private final List<PotionEffect> customEffects;

    /**
     * Instantiates a new Mock suspicious stew meta.
     */
    public MockSuspiciousStewMeta() {
        this.customEffects = new LinkedList<>();
    }

    @Override
    public boolean hasCustomEffects() {
        return !this.customEffects.isEmpty();
    }

    @Override
    public boolean addCustomEffect(@NotNull PotionEffect effect, boolean overwrite) {
        if (!hasCustomEffect(effect.getType()) && !overwrite) return false;
        this.customEffects.add(effect);
        return true;
    }

    @Override
    public boolean removeCustomEffect(@NotNull PotionEffectType type) {
        return this.customEffects.removeIf(e -> e.getType().equals(type));
    }

    @Override
    public boolean hasCustomEffect(@NotNull PotionEffectType type) {
        return this.customEffects.stream().anyMatch(e -> e.getType().equals(type));
    }

    @Override
    public boolean clearCustomEffects() {
        if (hasCustomEffects()) {
            this.customEffects.clear();
            return true;
        }
        return false;
    }

    @Override
    public @NotNull MockSuspiciousStewMeta clone() {
        return (MockSuspiciousStewMeta) super.clone();
    }

}
