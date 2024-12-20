package it.fulminazzo.jbukkit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EquableTest {

    @Test
    void testCompareNull() {
        assertTrue(new MockClass().compareNull(),
                "When comparing with null should return true");
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
        private int i;
        private double d;
        private float f;
        private long l;
        private boolean b;
        private short s;
        private byte b1;
        private char c;
        private String s1;
        private final List<String> l1 = new ArrayList<>();
        private Set<String> s2;
        private final Collection<String> c1 = new ArrayList<>();
        private final Map<String, Integer> m = new HashMap<>();
        private Map<String, Integer> m2;
        private Object o1;
        private Object o2;
        private MockClass o3;
    }

}