package it.fulminazzo.jbukkit.map;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a mock for {@link MapView}.
 */
@Getter
@Setter
public class MockMapView implements MapView {
    private static int LATEST_ID = 0;
    private final int id;
    private final boolean virtual;
    private final List<MapRenderer> renderers;
    private Scale scale;
    private World world;
    private int centerX;
    private int centerZ;
    private boolean unlimitedTracking;
    private boolean trackingPosition;
    private boolean locked;

    /**
     * Instantiates a new Mock map view.
     */
    public MockMapView() {
        this(LATEST_ID++, false);
    }

    /**
     * Instantiates a new Mock map view.
     *
     * @param id      the id
     * @param virtual the virtual
     */
    public MockMapView(int id, boolean virtual) {
        this.id = id;
        this.virtual = virtual;
        this.renderers = new LinkedList<>();
    }

    @Override
    public void addRenderer(@NotNull MapRenderer renderer) {
        this.renderers.add(renderer);
    }

    @Override
    public boolean removeRenderer(@Nullable MapRenderer renderer) {
        return this.renderers.remove(renderer);
    }

}
