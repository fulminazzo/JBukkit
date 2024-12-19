package it.fulminazzo.jbukkit.potion;

import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.jbukkit.NotImplementedException;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents an implementation of {@link PotionEffectType}.
 */
@Getter
public class MockPotionEffectType extends PotionEffectType {
    private static int LAST_USED_ID = 1;
    private final int id;
    private final @NotNull NamespacedKey key;
    private final @NotNull String name;
    private final @NotNull PotionEffectTypeCategory category;
    private final @NotNull Color color;
    private final boolean instant;

    /**
     * Instantiates a new Mock potion effect type.
     *
     * @param key      the key
     * @param category the category
     * @param color    the color
     * @param instant  true if its effects are applied instantly
     */
    public MockPotionEffectType(final @NotNull NamespacedKey key,
                                final @NotNull PotionEffectTypeCategory category,
                                final @NotNull Color color, final boolean instant) {
        this.id = LAST_USED_ID++;
        this.key = Objects.requireNonNull(key);
        this.category = category;
        this.color = color;
        this.instant = instant;
        this.name = StringUtils.capitalize(this.key.getKey());
    }

    @Override
    public @NotNull PotionEffect createEffect(int duration, int amplifier) {
        return new PotionEffect(this, duration, amplifier);
    }

    @Override
    public double getDurationModifier() {
        return 1.0;
    }

    @Override
    public @NotNull String getTranslationKey() {
        throw new NotImplementedException();
    }

}
