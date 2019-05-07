package com.aries.phoenix;

import org.junit.Test;

public class ThriftServerTest {
    @Test
    public void serverTest() {
        new ThriftServer().start();
    }
}
