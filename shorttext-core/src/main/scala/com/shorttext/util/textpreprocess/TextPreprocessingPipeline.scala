package com.shorttext.util.textpreprocess

import scala.annotation.tailrec

class TextPreprocessingPipeline(funclist : List[String => String] = List( (x: String) => x)) {

  @tailrec
  final def preprocessRecursively(text: String, funclist: List[String => String]) : String = {
    if (funclist.length==0) {
      text
    } else {
      preprocessRecursively(funclist(0)(text), funclist.slice(1, funclist.length))
    }
  }

  def preprocess(text: String) : String = preprocessRecursively(text, funclist)
}