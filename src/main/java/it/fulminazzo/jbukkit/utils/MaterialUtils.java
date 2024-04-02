package it.fulminazzo.jbukkit.utils;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.bukkit.Material;

public class MaterialUtils {

    /**
     * Gets the material from its id.
     * Works on versions lower than 1.12.2.
     *
     * @param materialId the material id
     * @return the material
     */
    public static Material getMaterial(int materialId) {
        return new Refl<>(Material.class).invokeMethod("getMaterial", materialId);
    }
}
