package com.shorttext.util.textpreprocess

import opennlp.tools.stemmer.PorterStemmer
import opennlp.tools.tokenize.SimpleTokenizer

object PipelineFactory {
  val tokenizer: SimpleTokenizer = SimpleTokenizer.INSTANCE

  val stopwordSet = StopwordsLoader.load()
  val stemmer = new PorterStemmer()

  def standardPipeline() : TextPreprocessingPipeline = {
    val funclist = List(
      (text: String) => text.replaceAll("[^\\w\\s]", ""),
      (text: String) => text.replaceAll("[\\d]", ""),
      (text: String) => text.toLowerCase,
      (text: String) => tokenizer.tokenize(text).
        filter( (token: String) => !(stopwordSet.contains(token))).
        reduce( (w1: String, w2: String) => w1+" "+w2),
      (text: String) => stemmer.stem(text)
    )
    new TextPreprocessingPipeline(funclist)
  }
}
