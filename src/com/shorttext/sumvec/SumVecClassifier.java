package com.shorttext.sumvec;

import com.shorttext.word2vec.Word2VecUtil;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hok on 1/19/17.
 */
public class SumVecClassifier {
    private Word2VecUtil model;
    private Map<String, INDArray> averageVectors;


    public SumVecClassifier(Word2VecUtil model) {
        this.model = model;
    }

    public void train(Map<String, List<String>> trainData) {
        averageVectors = new ConcurrentHashMap<String, INDArray>();
        for (String label: trainData.keySet()) {

        }
    }
}
