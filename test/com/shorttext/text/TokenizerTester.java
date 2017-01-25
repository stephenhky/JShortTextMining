package com.shorttext.text;

import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;

/**
 * Created by hok on 1/25/17.
 */
public class TokenizerTester {
    public static void main(String[] args) throws ResourceInitializationException {
        TokenizerFactory tokenizerFactory = new UimaTokenizerFactory();
        Tokenizer tokenizer = tokenizerFactory.create("I love Mogu!");
        System.out.println(tokenizer.getTokens());
    }
}
