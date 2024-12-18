package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.SkullMeta;

@Getter
@Setter
public class MockSkullMeta extends MockItemMeta implements SkullMeta {
    private String owner;

    public boolean setOwner(String owner) {
        this.owner = owner;
        return true;
    }

    @Override
    public boolean hasOwner() {
        return this.owner != null;
    }

    @Override
    public MockSkullMeta clone() {
        return (MockSkullMeta) super.clone();
    }
}
