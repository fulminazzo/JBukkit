package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.objects.Refl;
import lombok.NoArgsConstructor;
import org.bukkit.Material;

@NoArgsConstructor
public final class MaterialUtils {

    /**
     * Gets the material from its id.
     * Works on versions lower than 1.12.2.
     *
     * @param materialId the material id
     * @return the material
     */
    public static Material getMaterial(final int materialId) {
        return new Refl<>(Material.class).invokeMethod("getMaterial", materialId);
    }

}
