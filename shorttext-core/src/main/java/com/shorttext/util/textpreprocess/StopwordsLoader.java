package com.shorttext.util.textpreprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class StopwordsLoader {
    public static Set<String> load(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Set<String> stopwordSet = new HashSet<String>();
        String word;
        while ((word = reader.readLine())!=null) {
            stopwordSet.add(word.trim());
        }
        reader.close();
        return stopwordSet;
    }

    public static Set<String> load() throws IOException {
        return load(StopwordsLoader.class.getResourceAsStream("stopwords.txt"));
    }
}
