package com.shorttext.sumvec;

import com.shorttext.data.DataRetrieval;
import com.shorttext.word2vec.DL4JWord2VecUtil;
import com.shorttext.word2vec.MapDBWord2VecUtil;

import java.io.*;
import java.util.Map;

/**
 * Created by hok on 1/25/17.
 */
public class SumVecClassifierTester {
    public static void main(String[] args) throws IOException {
        InputStream trainDataInputStream = new FileInputStream(args[0]);
        File word2VecModelFile = new File(args[1]);
        boolean useDL4Jformat = (args.length>2)?(args[2].equalsIgnoreCase("--dl4j")):false;

        System.err.print("Load word-embedding model... ");
        long t0 = System.nanoTime();
        SumVecClassifier classifier = new SumVecClassifier(useDL4Jformat?(new DL4JWord2VecUtil(word2VecModelFile)):(new MapDBWord2VecUtil(word2VecModelFile)));
        long t1 = System.nanoTime();
        System.err.println("time elapsed = "+(t1-t0)/(1e-9)+" sec");

        System.err.print("Training... ");
        t0 = System.nanoTime();
        classifier.train(DataRetrieval.retrieveLabeledDataset(trainDataInputStream, true));
        t1 = System.nanoTime();
        System.err.println("time elapsed = "+(t1-t0)/(1e-9)+" sec");

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        boolean tonext = true;
        while (tonext) {
            System.out.print("sentence> ");
            String sentence = consoleReader.readLine().trim();
            Map<String, Double> scoreMap = classifier.score(sentence);
            for (String label: scoreMap.keySet()) {
                System.out.println(label+" : "+scoreMap.get(label));
            }

            System.out.print("continue? (y/n) ");
            tonext = (consoleReader.readLine().equalsIgnoreCase("y"));
        }
    }
}
