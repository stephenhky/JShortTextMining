package com.shorttext.text;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hok on 1/9/17.
 */
public class Word2VecTester {

    public static void main(String[] args) throws IOException {
        long time0 = System.nanoTime();
        Word2Vec word2Vec = WordVectorSerializer.readWord2VecModel(args[0]);
        long time1 = System.nanoTime();
        System.out.println("Loading time = "+(time1-time0)*1e-9+" sec");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean tonext = true;
        while (tonext) {
            System.out.print("word1> ");
            String word1 = reader.readLine().trim();
            System.out.print("word2> ");
            String word2 = reader.readLine().trim();
            double similarity = word2Vec.similarity(word1, word2);
            System.out.println("similarity = "+similarity);
            System.out.println("word1 vec = "+word2Vec.getWordVectorMatrix(word1)+" "+word2Vec.hasWord(word1));
            System.out.println("word2 vec = "+word2Vec.getWordVectorMatrix(word2)+" "+word2Vec.hasWord(word2));

            System.out.print("continue? (y/n) ");
            tonext = (reader.readLine().equalsIgnoreCase("y"));
        }
    }
}
