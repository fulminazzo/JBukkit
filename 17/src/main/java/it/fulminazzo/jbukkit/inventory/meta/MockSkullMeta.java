package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Represents a mock implementation for {@link SkullMeta}.
 */
@Getter
@Setter
public class MockSkullMeta extends MockItemMeta implements SkullMeta {
    private String owner;
    private OfflinePlayer owningPlayer;

    public boolean setOwner(String owner) {
        this.owner = owner;
        return true;
    }

    @Override
    public boolean setOwningPlayer(OfflinePlayer owningPlayer) {
        this.owningPlayer = owningPlayer;
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
