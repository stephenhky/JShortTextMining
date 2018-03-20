package com.shorttext.metrics;

import org.junit.Assert;
import org.junit.Test;

public class JaccardTest {

    @Test
    public void testJaccard() {
        Assert.assertEquals(CharacterJaccard.Similarity("driver", "diver"), 5./6., 1e-4);
    }
}
