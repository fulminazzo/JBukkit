package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkEffectMeta;

@Getter
@Setter
public class MockFireworkEffectMeta extends MockItemMeta implements FireworkEffectMeta {
    private FireworkEffect effect;

    @Override
    public boolean hasEffect() {
        return effect != null;
    }

    @Override
    public MockFireworkEffectMeta clone() {
        return (MockFireworkEffectMeta) super.clone();
    }

}
