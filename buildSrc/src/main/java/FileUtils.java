import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * A utility class for working with files.
 */
@NoArgsConstructor
public final class FileUtils {

    /**
     * Creates the given directory if it not exists already.
     * Throws {@link FileException} in case of failure.
     * If the file already exists and is a file, it will be deleted.
     *
     * @param dir the dir
     */
    public static void createDirIfNotExists(final @NotNull File dir) {
        deleteIf(File::isFile, dir);
        if (!dir.isDirectory() && !dir.mkdir())
            throw new FileException("create", dir);
    }

    /**
     * Creates the given file if it not exists already.
     * Throws {@link FileException} in case of failure.
     * If the file already exists and is a directory, it will be deleted.
     *
     * @param file the file
     */
    public static void createFileIfNotExists(final @NotNull File file) {
        deleteIf(File::isDirectory, file);
        try {
            if (!file.exists() && !file.createNewFile())
                throw new FileException("create", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the target file if it exists.
     * Throws {@link FileException} in case of failure.
     *
     * @param file the file
     */
    public static void deleteIfExists(final @NotNull File file) {
        deleteIf(File::exists, file);
    }

    /**
     * Deletes the target file if the given predicate is verified.
     * Throws {@link FileException} in case of failure.
     *
     * @param predicate the predicate
     * @param target    the target
     */
    public static void deleteIf(final @NotNull Predicate<File> predicate,
                                final @NotNull File target) {
        if (predicate.test(target) && !target.delete())
            throw new FileException("delete", target);
    }

    private static @NotNull String getFileType(final @NotNull File file) {
        return file.isDirectory() ? "directory" : file.isFile() ? "file" : "none";
    }

    /**
     * Verifies that two files have the same contents.
     *
     * @param first  the first file
     * @param second the second file
     * @return true if they match
     */
    public static boolean sameContent(final @NotNull File first, final @NotNull File second) {
        if (!first.isFile()) throw new IllegalArgumentException(first.getPath() + " is not a file");
        if (!second.isFile()) throw new IllegalArgumentException(second.getPath() + " is not a file");
        try (FileInputStream firstStream = new FileInputStream(first);
             FileInputStream secondStream = new FileInputStream(second)) {
            if (firstStream.available() != secondStream.available()) return false;
            while (firstStream.available() > 0)
                if (firstStream.read() != secondStream.read()) return false;
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        /**
         * Instantiates a new File exception.
         * Generates a message of type "Failed to %action% %target_type% %target_path%"
         *
         * @param action the action
         * @param target the target
         */
        public FileException(final @NotNull String action, final @NotNull File target) {
            this(String.format("Failed to %s %s: %s", action, getFileType(target), target.getPath()));
        }

    }
}
