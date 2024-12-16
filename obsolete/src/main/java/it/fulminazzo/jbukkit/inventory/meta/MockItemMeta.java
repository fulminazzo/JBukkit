package it.fulminazzo.jbukkit.inventory.meta;

import org.bukkit.inventory.meta.ItemMeta;

public class MockItemMeta extends AbstractMockItemMeta implements ItemMeta {

    public Spigot spigot() {
        return new Spigot(){
            @Override
            public void setUnbreakable(boolean unbreakable) {
                MockItemMeta.this.unbreakable = unbreakable;
            }

            @Override
            public boolean isUnbreakable() {
                return MockItemMeta.this.unbreakable;
            }
        };
    }
}
