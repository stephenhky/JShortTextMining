package com.shorttext.word2vec;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;

/**
 * Created by hok on 1/19/17.
 */
public class DL4JWord2VecUtil implements WordEmbeddingModelUtil {
    private Word2Vec word2vec;

    public DL4JWord2VecUtil(File word2VecFile) {
        System.err.println("Loading model: "+word2VecFile.getAbsolutePath());
        long time0 = System.nanoTime();
        this.word2vec = WordVectorSerializer.readWord2VecModel(word2VecFile);
        long time1 = System.nanoTime();
        System.err.println("Time elapsed = "+(time1-time0)*1e-9+" sec");
    }

    public double similarity(String word1, String word2) {
        return this.word2vec.similarity(word1, word2);
    }

    public double[] getWordVector(String word) {
        return this.word2vec.getWordVector(word);
    }

    public boolean hasWord(String word) {
        return this.word2vec.hasWord(word);
    }

    public int getVectorSize() {
        return this.word2vec.getWordVector("apple").length;
    }

    public INDArray getWordMatrix(String word) {
        return this.word2vec.getWordVectorMatrixNormalized(word);
    }
}
