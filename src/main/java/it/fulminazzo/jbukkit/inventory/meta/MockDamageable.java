package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class MockDamageable extends MockItemMeta implements Damageable {
    private int damage;
    private Integer maxDamage;

    public boolean hasDamage() {
        return this.damage > 0;
    }

    public int getMaxDamage() {
        return this.maxDamage;
    }

    @Override
    public boolean hasMaxDamage() {
        return this.maxDamage != null;
    }

    public @NotNull Damageable clone() {
        return (Damageable) super.clone();
    }
}
