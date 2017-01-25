package com.shorttext.sumvec;

import com.shorttext.WordEmbeddingClassifier;
import com.shorttext.word2vec.Word2VecUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hok on 1/19/17.
 */
public class SumVecClassifier extends WordEmbeddingClassifier {
    private Map<String, INDArray> averageVectors;

    public SumVecClassifier(Word2VecUtil model) {
        init(model);
    }

    @Override
    public void train(Map<String, List<String>> trainData) {
        averageVectors = new ConcurrentHashMap<String, INDArray>();
        for (String label: trainData.keySet()) {
            INDArray classVector = Nd4j.zeros(vecSize);
            for (String sentence: trainData.get(label)) {
                classVector = classVector.add(convertSentenceToVector(sentence));
            }
            classVector = classVector.div(classVector.distance2(Nd4j.zeros(vecSize)));
            averageVectors.put(label, classVector);
        }
    }

    @Override
    public Map<String, Double> score(String sentence) {
        Map<String, Double> scoreMap = new ConcurrentHashMap<String, Double>();
        INDArray sentVector = convertSentenceToVector(sentence);
        for (String label: averageVectors.keySet()) {
            scoreMap.put(label, Transforms.cosineSim(sentVector, averageVectors.get(label)));
        }
        return scoreMap;
    }
}
