import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A utility for modules of the project.
 */
@NoArgsConstructor
public final class ModuleUtils {
    private static final String SETTINGS_MODULE_FORMAT = "// 1.%current%\n" +
            "include '%current%'\n";
    private static final String BUILD_GRADLE_FORMAT = "dependencies {\n" +
            "    compileOnly libs.spigot.v%current%\n" +
            "    testImplementation libs.spigot.v%current%\n" +
            "\n" +
            "    api project(':%previous%')\n" +
            "}";

    /**
     * Generates a new module by using the given number as parent directory
     * and copying every content from previous modules to the current one.
     *
     * @param module the module number
     */
    public static void generateModule(final int module) {
        File parent = getParent();
        File targetModuleDir = new File(parent, String.valueOf(module));
        FileUtils.createDirIfNotExists(targetModuleDir);

        for (int i = 0; i < module; i++) {
            File moduleDir = new File(parent, String.valueOf(i));
            if (moduleDir.isDirectory())
                copySingleModule(moduleDir, targetModuleDir);
        }
        generateModuleDeclaration(module);
        generateBuildGradle(module);
    }

    private static void generateModuleDeclaration(final int module) {
        File settingsFile = new File(getParent(), "settings.gradle");
        String output = formatModule(SETTINGS_MODULE_FORMAT, module);
        if (!FileUtils.contains(settingsFile, output)) {
            String read = FileUtils.readFile(settingsFile);
            while (read.substring(read.length() - 1).matches("[\r\n\t ]"))
                read = read.substring(0, read.length() - 1);
            read += "\n" + output;
            FileUtils.writeFile(settingsFile, read);
        }
    }

    private static void generateBuildGradle(final int module) {
        File buildFile = new File(getParent(), module + File.separator + "build.gradle");
        FileUtils.deleteIfExists(buildFile);
        FileUtils.createDirIfNotExists(buildFile);
        FileUtils.writeFile(buildFile, formatModule(BUILD_GRADLE_FORMAT, module));
    }

    private static String formatModule(final @NotNull String string,
                                       final int module) {
        return string
                .replace("%current%", String.valueOf(module))
                .replace("%previous%", String.valueOf(module - 1));
    }

    private static void copySingleModule(final @NotNull File module,
                                         final @NotNull File targetModule) {
        if (module.isDirectory()) {
            File[] files = module.listFiles();
            if (files == null) return;
            FileUtils.createDirIfNotExists(targetModule);
            for (File file : files)
                copySingleModule(file, new File(targetModule, file.getName()));
        } else {
            if (!module.getName().endsWith(".java")) return;
            FileUtils.deleteIfExists(targetModule);
            FileUtils.createFileIfNotExists(targetModule);
            FileUtils.writeFile(targetModule, FileUtils.readFile(module));
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
        File parent = getParent();
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
            FileUtils.deleteIf(f -> FileUtils.sameContent(module, f), targetModule);
        }
    }

    private static @NotNull File getParent() {
        return new File(System.getProperty("user.dir"));
    }

}
