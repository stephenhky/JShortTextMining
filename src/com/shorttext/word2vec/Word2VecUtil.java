package com.shorttext.word2vec;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by hok on 1/19/17.
 */
public interface Word2VecUtil {
    public double similarity(String word1, String word2);
    public INDArray getWordMatrix(String word);
    public double[] getWordVector(String word);
}
