package com.shorttext.util.wordembed;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.File;
import java.util.Map;

/**
 * Created by hok on 1/19/17.
 */
public class MapDBWord2VecUtil implements WordEmbeddingModelUtil {
    Map<String, double[]> word2VecMap;


    public MapDBWord2VecUtil(File mapDBFile) {
        DB db = DBMaker.fileDB(mapDBFile).closeOnJvmShutdown().make();
        this.word2VecMap = db.hashMap("map").keySerializer(Serializer.STRING).valueSerializer(Serializer.DOUBLE_ARRAY).create();
    }

    public double[] getWordVector(String word) {
        return word2VecMap.get(word);
    }

    public boolean hasWord(String word) {
        return this.word2VecMap.containsKey(word);
    }

    public int getVectorSize() {
        return this.getWordVector("apple").length;
    }

    public INDArray getWordMatrix(String word) {
        return Nd4j.create(getWordVector(word));
    }

    public double similarity(String word1, String word2) {
        return Transforms.cosineSim(getWordMatrix(word1), getWordMatrix(word2));
    }
}
