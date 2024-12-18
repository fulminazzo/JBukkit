package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.inventory.meta.MapMeta;

@Getter
@Setter
public class MockMapMeta extends MockItemMeta implements MapMeta {
    private boolean scaling;
    private String locationName;
    private Color color;

    @Override
    public MockMapMeta clone() {
        return (MockMapMeta) super.clone();
    }

    @Override
    public boolean hasLocationName() {
        return this.locationName != null;
    }

    @Override
    public boolean hasColor() {
        return this.color != null;
    }

}
