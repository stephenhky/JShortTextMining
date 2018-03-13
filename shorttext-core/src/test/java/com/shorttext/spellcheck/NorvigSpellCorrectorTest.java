package com.shorttext.spellcheck;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NorvigSpellCorrectorTest {

    @Test
    public void testSpellCorrector() throws IOException {
        NorvigSpellChecker spellChecker = new NorvigSpellChecker();
        spellChecker.train(NorvigSpellCorrectorTest.class.getResourceAsStream("big.txt"));

        System.out.println(spellChecker.correct("apple"));
        Assert.assertEquals("apple", spellChecker.correct("apple"));
        System.out.println(spellChecker.correct("computter"));
        Assert.assertEquals("computer", spellChecker.correct("computter"));
    }
}
