import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A utility for modules of the project.
 */
@NoArgsConstructor
public final class ModuleUtils {

    /**
     * Generates a new module by using the given number as parent directory
     * and copying every content from previous modules to the current one.
     *
     * @param module the module number
     */
    public static void generateModule(final int module) {
        File parent = new File(System.getProperty("user.dir"));
        File targetModuleDir = new File(parent, String.valueOf(module));
        if (!targetModuleDir.isDirectory() && !targetModuleDir.mkdir())
            throw new RuntimeException("Failed to create directory " + targetModuleDir.getPath());

        for (int i = 0; i < module; i++) {
            File moduleDir = new File(parent, String.valueOf(i));
            if (moduleDir.isDirectory())
                copySingleModule(moduleDir, targetModuleDir);
        }
    }

    private static void copySingleModule(final @NotNull File module,
                                         final @NotNull File targetModule) {
        if (module.isDirectory()) {
            File[] files = module.listFiles();
            if (files == null) return;
            if (!targetModule.isDirectory() && !targetModule.mkdir())
                throw new RuntimeException("Failed to create directory " + targetModule.getPath());
            for (File file : files)
                copySingleModule(file, new File(targetModule, file.getName()));
        } else {
            if (!module.getName().endsWith(".java")) return;
            if (targetModule.exists() && !targetModule.delete())
                throw new RuntimeException("Failed to delete " + targetModule.getPath());
            try {
                if (!targetModule.createNewFile())
                    throw new RuntimeException("Failed to create file " + targetModule.getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (FileInputStream input = new FileInputStream(module);
                 FileOutputStream output = new FileOutputStream(targetModule)) {
                while (input.available() > 0) output.write(input.read());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Looks for a module named <code>module</code> in the project folder.
     * Then, for every other numerical module present, checks if there are
     * repetitions of <b>.java</b> files and deletes them.
     *
     * @param module the module name
     */
    public static void checkModuleRepetitions(final int module) {
        File parent = new File(System.getProperty("user.dir"));
        File targetModuleDir = new File(parent, String.valueOf(module));
        if (!targetModuleDir.isDirectory())
            throw new IllegalArgumentException("Could not find module " + module);

        for (int i = 0; i < module; i++) {
            File moduleDir = new File(parent, String.valueOf(i));
            if (moduleDir.isDirectory())
                checkSingleModuleRepetitions(moduleDir, targetModuleDir);
        }
    }

    private static void checkSingleModuleRepetitions(final @NotNull File module,
                                                     final @NotNull File targetModule) {
        if (module.isDirectory()) {
            File[] files = module.listFiles();
            if (files == null) return;
            for (File file : files)
                checkSingleModuleRepetitions(file, new File(targetModule, file.getName()));
        } else {
            if (!targetModule.exists()) return;
            if (!targetModule.getName().endsWith(".java")) return;
            if (sameContent(module, targetModule) && !targetModule.delete())
                throw new RuntimeException("Failed to delete " + targetModule.getPath());
        }
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

}
