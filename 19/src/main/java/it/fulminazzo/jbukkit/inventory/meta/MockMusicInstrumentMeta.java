package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.MusicInstrument;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mock implementation for {@link MusicInstrumentMeta}.
 */
@Getter
@Setter
public class MockMusicInstrumentMeta extends MockItemMeta implements MusicInstrumentMeta {
    private MusicInstrument instrument;

    @Override
    public @NotNull MockMusicInstrumentMeta clone() {
        return (MockMusicInstrumentMeta) super.clone();
    }

}
