package com.shorttext.convnet;

import com.shorttext.WordEmbeddingClassifier;
import com.shorttext.word2vec.WordEmbeddingModelUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by hok on 1/25/17.
 */
public class WordEmbedConvNetClassifier extends WordEmbeddingClassifier {
    private int numEpoch = 10;

    public WordEmbedConvNetClassifier(WordEmbeddingModelUtil model) {
        init(model);
    }

    public void train(Map<String, List<String>> trainData) {

    }

    public Map<String, Double> score(String sentence) {
        return null;
    }

    public int getNumEpoch() {
        return numEpoch;
    }

    public void setNumEpoch(int numEpoch) {
        this.numEpoch = numEpoch;
    }
}
