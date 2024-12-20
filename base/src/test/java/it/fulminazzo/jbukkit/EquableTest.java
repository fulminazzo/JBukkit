package it.fulminazzo.jbukkit;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EquableTest {

    @Test
    void testCompareNull() {
        assertTrue(new MockClass().compareNull(),
                "When comparing with null should return true");
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
        private List<String> l1 = new ArrayList<>();
        private Set<String> s2;
        private Collection<String> c1 = new ArrayList<>();
        private Map<String, Integer> m = new HashMap<>();
        private Map<String, Integer> m2;
        private Object o1;
        private Object o2;
        private MockClass o3;
    }

}