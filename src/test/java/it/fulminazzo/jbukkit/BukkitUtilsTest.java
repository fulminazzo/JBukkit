package it.fulminazzo.jbukkit;

import it.fulminazzo.jbukkit.annotations.After1_;
import it.fulminazzo.jbukkit.annotations.Before1_;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BukkitUtilsTest extends BukkitUtils {

    @After1_(10)
    @Test
    void testLowerVersion() {
        numericalVersion = 9.4;
        check();
        fail("Test should have stopped by now");
    }

    @Before1_(10)
    @Test
    void testHigherVersion() {
        numericalVersion = 12.2;
        check();
        fail("Test should have stopped by now");
    }

    @Before1_(12)
    @After1_(10)
    @Test
    void testVersionBetween() {
        boolean fail = true;
        try {
            numericalVersion = 11.2;
            check();
            fail = false;
        } finally {
            if (fail) fail("Fail was not set, so the test was cancelled");
        }
    }
}