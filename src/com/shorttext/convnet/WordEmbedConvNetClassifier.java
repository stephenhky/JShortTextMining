package com.shorttext.convnet;

import com.shorttext.WordEmbeddingClassifier;
import com.shorttext.word2vec.WordEmbeddingModelUtil;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.List;
import java.util.Map;

/**
 * Created by hok on 1/25/17.
 */
public class WordEmbedConvNetClassifier extends WordEmbeddingClassifier {
    private int numEpoch = 10;
    private int numFilters = 1200;
    private int filterLength = 2;
    private int maxLen = 15;
    private MultiLayerNetwork neuralNet;
    private int seed = 123;
    private int vecSize = 300;

    public WordEmbedConvNetClassifier(WordEmbeddingModelUtil model) {
        init(model);
    }

    public void train(Map<String, List<String>> trainData) {
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(1)
                .regularization(true).l2(0.0)
                .learningRate(.01)//.biasLearningRate(0.02)
                //.learningRateDecayPolicy(LearningRatePolicy.Inverse).lrPolicyDecayRate(0.001).lrPolicyPower(0.75)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.LBFGS)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .list()
                .layer(0, new ConvolutionLayer.Builder(filterLength, filterLength)
                            .nIn(1)
                            .stride(1)
                            .nOut(numFilters)
                            .activation(Activation.RELU)
                            .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                            .kernelSize(maxLen-filterLength+1, maxLen-filterLength+1)
                            .stride(1, 1)
                            .build())
                .layer(2, new DenseLayer.Builder()
                            .build())
                .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY)
                            .nOut(trainData.size())
                            .activation(Activation.SOFTMAX)
                            .build())
                .pretrain(false).backprop(true)
                .setInputType(InputType.convolutional(maxLen, vecSize, numFilters))
                .build();
        MultiLayerNetwork neuralNet = new MultiLayerNetwork(config);
        neuralNet.init();


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

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getNumFilters() {
        return numFilters;
    }

    public void setNumFilters(int numFilters) {
        this.numFilters = numFilters;
    }

    public int getFilterLength() {
        return filterLength;
    }

    public void setFilterLength(int filterLength) {
        this.filterLength = filterLength;
    }

    public int getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }

    public int getVecSize() {
        return vecSize;
    }

    public void setVecSize(int vecSize) {
        this.vecSize = vecSize;
    }
}
