package it.fulminazzo.jbukkit.annotations;

import java.lang.annotation.*;

/**
 * An annotation used to identify tests that should be run on Minecraft versions equal or higher than Minecraft 1.{@link #value()}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface After1_ {

    /**
     *
     * @return the version
     */
    double value();
}
