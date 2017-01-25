package com.shorttext.sumvec;

import com.shorttext.word2vec.Word2VecUtil;
import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hok on 1/19/17.
 */
public class SumVecClassifier {
    private Word2VecUtil model;
    private Map<String, INDArray> averageVectors;
    TokenizerFactory tokenizerFactory;
    private int vecSize;

    public SumVecClassifier(Word2VecUtil model) {
        this.model = model;
        try {
            this.tokenizerFactory = new UimaTokenizerFactory();
        } catch (ResourceInitializationException e) {
            e.printStackTrace();
            this.tokenizerFactory = null;
        }
        this.vecSize = this.model.getVectorSize();
    }

    private List<String> tokenize(String sentence) {
        if (this.tokenizerFactory==null) {
            return Arrays.asList(sentence.split(" "));
        } else {
            Tokenizer tokenizer = this.tokenizerFactory.create(sentence);
            return tokenizer.getTokens();
        }
    }

    private INDArray convertSentenceToVector(String sentence) {
        INDArray sentVector = Nd4j.zeros(vecSize);
        for (String token: tokenize(sentence)) {
            if (this.model.hasWord(token)) {
                sentVector = sentVector.add(this.model.getWordMatrix(token));
            }
        }
        return sentVector;
    }

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

    public Map<String, Double> score(String sentence) {
        Map<String, Double> scoreMap = new ConcurrentHashMap<String, Double>();
        INDArray sentVector = convertSentenceToVector(sentence);
        for (String label: averageVectors.keySet()) {
            scoreMap.put(label, Transforms.cosineSim(sentVector, averageVectors.get(label)));
        }
        return scoreMap;
    }
}
