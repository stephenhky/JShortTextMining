package com.shorttext.sumvec;

import com.shorttext.word2vec.Word2VecUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by hok on 1/19/17.
 */
public class SumVecClassifier {
    private Word2VecUtil model;

    public SumVecClassifier(Word2VecUtil model) {
        this.model = model;
    }

    public void train(Map<String, List<String>> trainData) {

    }
}
