package com.shorttext.data;

import com.opencsv.CSVParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hok on 1/19/17.
 */
public class DataRetrieval {
    public static Map<String, List<String>> retrieveLabeledDataset(InputStream inputStream, boolean header) throws IOException {
        // initialize
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        CSVParser csvParser = new CSVParser();
        Map<String, List<String>> dataMap = new ConcurrentHashMap<String, List<String>>();

        // read data
        String line = (header)?reader.readLine():"";   // skip first line
        while ((line=reader.readLine())!=null) {
            String[] components = csvParser.parseLine(line.trim());
            String label = components[0];     String sentence = components[1];
            if (!dataMap.containsKey(label)) {
                dataMap.put(label, Collections.synchronizedList(new ArrayList<String>()));
            }
            dataMap.get(label).add(sentence);
        }
        reader.close();

        // return map
        return dataMap;
    }
}
