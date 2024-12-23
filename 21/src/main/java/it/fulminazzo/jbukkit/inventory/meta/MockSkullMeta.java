package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock implementation for {@link SkullMeta}.
 */
@Getter
@Setter
public class MockSkullMeta extends MockItemMeta implements SkullMeta {
    private String owner;
    private OfflinePlayer owningPlayer;
    private PlayerProfile ownerProfile;
    private NamespacedKey noteBlockSound;

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
    public @NotNull MockSkullMeta clone() {
        return (MockSkullMeta) super.clone();
    }

}
