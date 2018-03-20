package com.shorttext.spellcheck;

import java.io.File;

abstract public class SpellChecker {
    abstract public void train(File trainFile);
    abstract public String correct(String wrongSpelling);
}
