package com.shorttext.util.wordembed

import java.io.File

import org.mapdb.{DB, DBMaker, Serializer}
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.ops.transforms.Transforms

class MapDBWordEmbedUtil(mapDBFile: File) extends WordEmbeddingModelUtil {
  val db : DB = DBMaker fileDB(mapDBFile) closeOnJvmShutdown() make
  val wordEmbedMap = db hashMap("map") keySerializer(Serializer.STRING) valueSerializer(Serializer.DOUBLE_ARRAY) create()

  override def getWordVector(word: String): Array[Double] = wordEmbedMap get(word)
  override def hasWord(word: String): Boolean = wordEmbedMap containsKey(word)
  override def getVectorSize: Int = wordEmbedMap get("apple") length
  override def getWordMatrix(word: String): INDArray = Nd4j.create(getWordVector(word))
  override def similarity(word1: String, word2: String): Double = Transforms cosineSim(getWordMatrix(word1), getWordMatrix(word2))
}
