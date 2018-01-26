package com.shorttext.text;

import com.shorttext.data.DataRetrieval;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataRetrievalTest {

    @Test
    public void testSubDict() throws IOException {
        Map<String, List<String>> map = DataRetrieval.retrieveLabeledDataset(getClass().getResourceAsStream("shorttext_exampledata.csv"), true);
        Assert.assertEquals(3, map.size());
    }

}
