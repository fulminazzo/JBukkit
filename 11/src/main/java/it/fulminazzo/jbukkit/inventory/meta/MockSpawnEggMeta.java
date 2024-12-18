package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.SpawnEggMeta;

@Getter
@Setter
public class MockSpawnEggMeta extends MockItemMeta implements SpawnEggMeta {
    private EntityType spawnedType;

    @Override
    public MockSpawnEggMeta clone() {
        return (MockSpawnEggMeta) super.clone();
    }

}
