package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.jbukkit.annotations.After1_;
import it.fulminazzo.jbukkit.annotations.Before1_;
import it.fulminazzo.jbukkit.enchantments.MockEnchantment;
import it.fulminazzo.jbukkit.inventory.MockItemFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.Recipe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BukkitUtils {
    private static final Logger LOGGER = Logger.getLogger("Bukkit");
    private static final List<Recipe> RECIPES = new LinkedList<>();
    private static final String VERSION_FORMAT = "1\\.(\\d+)\\.(\\d+)-R\\d+\\.\\d+-SNAPSHOT";
    private static final String DEFAULT_VERSION = "1.20.4-R0.1-SNAPSHOT";
    static final String VERSION_NAME = "minecraft-version";
    @Getter
    static double numericalVersion;

    @BeforeEach
    protected void setUp() {
        check();
    }

    public static void setupVersion() {
        String version = System.getenv(VERSION_NAME);
        if (version == null) {
            LOGGER.warning(String.format("Could not find set version '%s'. Defaulting to value '%s'", VERSION_NAME, DEFAULT_VERSION));
            version = DEFAULT_VERSION;
        }
        Matcher matcher = Pattern.compile(VERSION_FORMAT).matcher(version);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Version '%s' did not match format '%s'", version, VERSION_FORMAT));
        numericalVersion = Double.parseDouble(matcher.group(1) + "." + matcher.group(2));
        LOGGER.info(String.format("Using version '%s'", numericalVersion));
    }

    public static void setupServer() {
        setupVersion();
        Server server = mock(Server.class);
        if (Arrays.stream(server.getClass().getDeclaredMethods()).anyMatch(m -> m.getName().equals("getRecipe")))
            when(server.getRecipe(any())).thenAnswer(r -> {
                Object key = r.getArgument(0);
                Iterator<Recipe> iterator = Bukkit.recipeIterator();
                while (iterator.hasNext()) {
                    Recipe recipe = iterator.next();
                    if (Objects.equals(new Refl<>(recipe).invokeMethod("getKey"), key)) return recipe;
                }
                return null;
            });
        when(server.getLogger()).thenReturn(LOGGER);
        when(server.getBukkitVersion()).thenReturn(String.format("1.%s-R0.1-SNAPSHOT", numericalVersion));
        when(server.addRecipe(any())).thenAnswer(r -> RECIPES.add(r.getArgument(0)));
        when(server.recipeIterator()).thenAnswer(r -> RECIPES.iterator());
        when(server.getItemFactory()).thenReturn(new MockItemFactory());
        new Refl<>(Bukkit.class).setFieldObject("server", server);
    }

    public static void setupEnchantments() {
        MockEnchantment.setupEnchantments();
    }

    /**
     * Allows to check the current method or class.
     * If they are annotated with {@link After1_} or with {@link Before1_},
     * and {@link #numericalVersion} does not match, then it is skipped.
     */
    public static void check() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        // The first should be getStackTrace, the second this method.
        // The third is the actual method of interest.
        StackTraceElement actualTrace = trace[2];

        final Class<?> clazz = ReflectionUtils.getClass(actualTrace.getClassName());
        check(clazz);

        final String methodName = actualTrace.getMethodName();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods)
            if (method.getName().equals(methodName)) check(method);
    }

    private static void check(AnnotatedElement element) {
        if (element.isAnnotationPresent(Before1_.class)) {
            double value = element.getAnnotation(Before1_.class).value();
            final String message = String.format("Skipping checks because of version higher than 1.%s", value);
            LOGGER.info(message);
            assumeTrue(numericalVersion < value, message);
        }
        if (element.isAnnotationPresent(After1_.class)) {
            double value = element.getAnnotation(After1_.class).value();
            final String message = String.format("Skipping checks because of version lower than 1.%s", value);
            LOGGER.info(message);
            assumeTrue(numericalVersion > value, message);
        }
    }
}
