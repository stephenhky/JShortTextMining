package com.shorttext.spellcheck

import java.io.{File, FileInputStream, InputStream}

import scala.collection.mutable.Map
import scala.io.Source

// reference: https://norvig.com/spell-correct.html

class NorvigSpellChecker extends SpellChecker {
  var wordCounts : Map[String, Int] = Map()
  val alphabets = ('a' to 'z').toSet

  override def train(trainFile : File) : Unit = train(new FileInputStream(trainFile))

  def train(trainFileStream : InputStream) : Unit = {
    val lines = Source.fromInputStream(trainFileStream) mkString
    val wordREPattern = "[A-Za-z]+"
    wordREPattern.r.findAllIn(lines).foreach( txtWord => {
      val word = txtWord.toLowerCase
      if (wordCounts.keySet contains(word)) {
        wordCounts(word) = wordCounts(word)+1
      } else {
        wordCounts += (word -> 1)
      }
    })
  }

  def getSplittedCombinations(word : String) : Set[(String, String)] =
    (0 to word.length).map( idx => (word.substring(0, idx), word.substring(idx, word.length))).toSet

  def getEditOneSpellings(word: String) : Set[String] = {
    val splits = getSplittedCombinations(word)
    val deletes = splits.filter(s => s._2.length>0).map( s => s._1+s._2.substring(1))
    val transposes = splits.filter(s => s._2.length>1).map( s => s._1+s._2.charAt(1)+s._2.charAt(0)+s._2.substring(2))
    val replaces = splits.filter(s => s._2.length>0).map( s => alphabets.map(c => s._1+c+s._2.substring(1)).filter(s => !s.equals(word))).reduceRight( (set1, set2) => set1 | set2)
    val inserts = splits.map( s => alphabets.map(c => s._1+c+s._2)).reduceRight( (set1, set2) => set1 | set2)
    (deletes | transposes | replaces | inserts).intersect(wordCounts.keySet)
  }

  def getEditTwoSpellings(word: String) : Set[String] =
    getEditOneSpellings(word).map(getEditOneSpellings).reduceRight( (set1, set2) => set1 | set2)

  override def correct(wrongSpelling: String) : String = {
    val edit0words = Set(wrongSpelling) intersect wordCounts.keySet
    if (edit0words.size>0) return edit0words.maxBy( s => wordCounts(s))
    val edit1words = getEditOneSpellings(wrongSpelling)
    if (edit1words.size>0) return edit1words.maxBy( s => wordCounts(s))
    val edit2words = getEditTwoSpellings(wrongSpelling)
    edit2words.maxBy( s => wordCounts(s))
  }
}
