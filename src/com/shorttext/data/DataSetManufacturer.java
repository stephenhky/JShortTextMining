package com.shorttext.data;

import com.shorttext.word2vec.WordEmbeddingModelUtil;
import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hok on 2/3/17.
 */
public class DataSetManufacturer {
    protected WordEmbeddingModelUtil model;
    protected Map<String, Integer> labelMap;
    protected String[] labels;
    protected TokenizerFactory tokenizerFactory;
    private int maxLen = 15;
    private int vecSize = 300;

    public DataSetManufacturer(WordEmbeddingModelUtil model) {
        this.model = model;
        try {
            this.tokenizerFactory = new UimaTokenizerFactory();
        } catch (ResourceInitializationException e) {
            e.printStackTrace();
        }
    }

    protected List<String> tokenize(String sentence) {
        if (this.tokenizerFactory==null) {
            return Arrays.asList(sentence.split(" "));
        } else {
            Tokenizer tokenizer = this.tokenizerFactory.create(sentence);
            return tokenizer.getTokens();
        }
    }

    public void makeLabelMap(Collection<String> labels) {
        labelMap = new ConcurrentHashMap<String, Integer>();
        this.labels = new String[labels.size()];

        int i = 0;
        for (String label: labels) {
            labelMap.put(label, i);
            this.labels[i] = label;
            i++;
        }
    }

    public DataSet convertToDataSet(String sentence, String label) {
        List<String> tokens = tokenize(sentence);

        // labels
        double[] basketArr = new double[labelMap.size()];
        Arrays.fill(basketArr, 0.0);   basketArr[labelMap.get(label)] = 1.0;
        INDArray basket = Nd4j.create(basketArr);

        // features
        INDArray feature = Nd4j.zeros(maxLen, vecSize);
        for (int i=0; i< Math.min(tokens.size(), maxLen); i++) {
            feature.putRow(i, model.getWordMatrix(tokens.get(i)));
        }

        // making dataset
        DataSet dataSet = new DataSet(feature, basket);

        return dataSet;
    }


}
