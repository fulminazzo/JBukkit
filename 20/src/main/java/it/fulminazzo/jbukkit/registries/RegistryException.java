package it.fulminazzo.jbukkit.registries;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Registry exception.
 */
public class RegistryException extends RuntimeException {

    /**
     * Instantiates a new Registry exception.
     *
     * @param message the message
     */
    public RegistryException(final @NotNull String message) {
        super(Objects.requireNonNull(message));
    }

    /**
     * Instantiates a new Registry exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RegistryException(final @NotNull String message, final @NotNull Throwable cause) {
        super(Objects.requireNonNull(message), Objects.requireNonNull(cause));
    }

}
