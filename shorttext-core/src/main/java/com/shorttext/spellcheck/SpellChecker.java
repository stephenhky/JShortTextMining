package com.shorttext.spellcheck;

import java.io.InputStream;

abstract public class SpellChecker {
    abstract public void train(InputStream trainInputStream);
    abstract public String correct(String wrongSpelling);
}
