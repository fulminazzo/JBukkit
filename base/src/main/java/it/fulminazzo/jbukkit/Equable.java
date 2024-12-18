package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Printable;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * A class used to compare two objects field by field.
 */
@NoArgsConstructor
public class Equable extends Printable {

    @Override
    public boolean equals(Object o) {
        if (o instanceof Equable) return equals(this, o);
        return super.equals(o);
    }

    /**
     * Checks that the given objects are equal by checking their classes and their fields.
     *
     * @param o1 the first object
     * @param o2 the second object
     * @return true if they are equal
     */
    public static boolean equals(final @Nullable Object o1, final @Nullable Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        return o1.getClass().equals(o2.getClass()) && ReflectionUtils.equalsFields(o1, o2);
    }

}
