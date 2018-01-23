package com.shorttext.util.wordembed;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by hok on 1/19/17.
 */
public interface WordEmbeddingModelUtil {
    public double similarity(String word1, String word2);
    public INDArray getWordMatrix(String word);
    public double[] getWordVector(String word);
    public boolean hasWord(String word);
    public int getVectorSize();
}
