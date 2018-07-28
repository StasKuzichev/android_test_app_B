package com.rdc.android_test_app_b.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkTest {

    @Test
    public void setStatus() {
        Link link = new Link();
        link.setStatus(6);
        int result = link.getStatus();
        int expected = 3;
        assertEquals(expected, result);
    }
}