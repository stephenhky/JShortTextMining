package com.shorttext.text;

import com.shorttext.util.textpreprocess.PipelineFactory;
import com.shorttext.util.textpreprocess.TextPreprocessingPipeline;
import org.junit.Assert;
import org.junit.Test;

public class TextPreprocessingTest {
    @Test
    public void testStandardPreprocessing() {
        TextPreprocessingPipeline standPipeline = PipelineFactory.standardPipeline();
        Assert.assertEquals(standPipeline.preprocess("Natural language processing and text mining on fire."), "natur languag process text mine fire");
    }
}
