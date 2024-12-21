package it.fulminazzo.jbukkit;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EquableTest {

    @Test
    void testCompareNull() {
        assertTrue(new MockClass().compareNull(),
                "When comparing with null should return true");
    }

    private static Object[] getCompareNotNullConsumers() {
        return new Consumer[] {
                m -> ((MockClass) m).i = 1,
                m -> ((MockClass) m).d = 1,
                m -> ((MockClass) m).f = 1,
                m -> ((MockClass) m).l = 1,
                m -> ((MockClass) m).b = true,
                m -> ((MockClass) m).s = 1,
                m -> ((MockClass) m).b1 = 1,
                m -> ((MockClass) m).c = 1,
                m -> ((MockClass) m).s1 = "Hello world",
                m -> ((MockClass) m).l1.add("Hello world"),
                m -> {
                    MockClass mc = (MockClass) m;
                    mc.s2 = new HashSet<>();
                    mc.s2.add("Hello world");
                },
                m -> ((MockClass) m).c1.add("Hello world"),
                m -> ((MockClass) m).m.put("Hello", 1),
                m -> {
                    MockClass mc = (MockClass) m;
                    mc.m2 = new HashMap<>();
                    mc.m2.put("Hello", 1);
                },
                m -> ((MockClass) m).o1 = new Refl<>(String.class),
                m -> ((MockClass) m).o2 = Integer.class,
                m -> {
                    MockClass mc = (MockClass) m;
                    mc.f = 1;
                    ((MockClass) m).o3 = mc;
                },
        };
    }

    @ParameterizedTest
    @MethodSource("getCompareNotNullConsumers")
    void testCompareNotNull(Consumer<MockClass> setter) {
        MockClass mockClass = new MockClass();
        setter.accept(mockClass);
        assertFalse(mockClass.compareNull(),
                "When comparing with null should not return true");
    }

    private static Object[][] getPrimitives() {
        return new Object[][] {
                new Object[] {boolean.class, false},
                new Object[] {byte.class, ((byte) 0)},
                new Object[] {short.class, ((short) 0)},
                new Object[] {char.class, ((char) 0)},
                new Object[] {int.class, 0},
                new Object[] {long.class, 0L},
                new Object[] {float.class, 0.0F},
                new Object[] {double.class, 0.0D},
        };
    }

    @ParameterizedTest
    @MethodSource("getPrimitives")
    void testCheckPrimitive(Class<?> type, Object value) {
        new MockClass();
        assertTrue(Equable.checkPrimitive(type, value),
                String.format("%s of value %s should have returned true", type, value));
    }

    @SuppressWarnings("unused")
    static class MockClass extends Equable {
        int i;
        double d;
        float f;
        long l;
        boolean b;
        short s;
        byte b1;
        char c;
        String s1;
        final List<String> l1 = new ArrayList<>();
        Set<String> s2;
        final Collection<String> c1 = new ArrayList<>();
        final Map<String, Integer> m = new HashMap<>();
        Map<String, Integer> m2;
        Object o1;
        Object o2;
        MockClass o3;
    }

}