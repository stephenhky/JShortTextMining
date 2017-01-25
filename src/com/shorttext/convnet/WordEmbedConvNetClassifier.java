package com.shorttext.convnet;

import com.shorttext.WordEmbeddingClassifier;
import com.shorttext.word2vec.WordEmbeddingModelUtil;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.List;
import java.util.Map;

/**
 * Created by hok on 1/25/17.
 */
public class WordEmbedConvNetClassifier extends WordEmbeddingClassifier {
    private int numEpoch = 10;
    private MultiLayerNetwork neuralNet;

    public WordEmbedConvNetClassifier(WordEmbeddingModelUtil model) {
        init(model);
    }

    public void train(Map<String, List<String>> trainData) {
        MultiLayerConfiguration config;
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
