package com.shorttext;

import com.shorttext.word2vec.Word2VecUtil;
import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by hok on 1/25/17.
 */
abstract public class WordEmbeddingClassifier {
    protected Word2VecUtil model;
    protected TokenizerFactory tokenizerFactory;
    protected int vecSize;

    abstract public void train(Map<String, List<String>> trainData);

    abstract public Map<String, Double> score(String sentence);

    protected List<String> tokenize(String sentence) {
        if (this.tokenizerFactory==null) {
            return Arrays.asList(sentence.split(" "));
        } else {
            Tokenizer tokenizer = this.tokenizerFactory.create(sentence);
            return tokenizer.getTokens();
        }
    }

    protected INDArray convertSentenceToVector(String sentence) {
        INDArray sentVector = Nd4j.zeros(vecSize);
        for (String token: tokenize(sentence)) {
            if (this.model.hasWord(token)) {
                sentVector = sentVector.add(this.model.getWordMatrix(token));
            }
        }
        return sentVector;
    }

    protected void init(Word2VecUtil model) {
        this.model = model;
        try {
            this.tokenizerFactory = new UimaTokenizerFactory();
        } catch (ResourceInitializationException e) {
            e.printStackTrace();
            this.tokenizerFactory = null;
        }
        this.vecSize = this.model.getVectorSize();
    }

}
