package com.shorttext.word2vec;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.File;
import java.util.Map;

/**
 * Created by hok on 1/19/17.
 */
public class MapDBWord2VecUtil implements Word2VecUtil {
    Map<String, double[]> word2VecMap;

    public MapDBWord2VecUtil(File mapDBFile) {
        DB db = DBMaker.newFileDB(mapDBFile).make();
        this.word2VecMap = db.getHashMap("map");
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
