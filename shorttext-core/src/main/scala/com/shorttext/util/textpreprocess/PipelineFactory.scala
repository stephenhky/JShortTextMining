package com.shorttext.util.textpreprocess

object PipelineFactory {
  def standardPipeline() : TextPreprocessingPipeline = {
    val funclist = List(
      (text: String) => text.replaceAll("[^\\w\\s]", ""),
      (text: String) => text.replaceAll("[\\d]", ""),
      (text: String) => text.toLowerCase

    )
    new TextPreprocessingPipeline(funclist)
  }
}
