import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.function.Predicate;

/**
 * A utility class for working with files.
 */
@NoArgsConstructor
public final class FileUtils {

    /**
     * Deletes the target file if the given predicate is verified.
     *
     * @param predicate the predicate
     * @param target    the target
     */
    public static void deleteIf(final @NotNull Predicate<File> predicate,
                                final @NotNull File target) {
        if (predicate.test(target) && !target.delete())
            throw new FileException("Failed to delete " + getFileType(target) + " " + target);
    }

    private static @NotNull String getFileType(final @NotNull File file) {
        return file.isDirectory() ? "directory" : file.isFile() ? "file" : "none";
    }

    private static class FileException extends RuntimeException {

        /**
         * Instantiates a new File exception.
         *
         * @param message the message
         */
        public FileException(final @NotNull String message) {
            super(message);
        }

    }
}
