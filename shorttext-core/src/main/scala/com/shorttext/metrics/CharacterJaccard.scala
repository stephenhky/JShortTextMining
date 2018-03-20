package com.shorttext.metrics

import com.aliasi.spell.EditDistance

object CharacterJaccard {

  def Similarity(token1: String, token2: String) : Double = {
    val maxLen : Double = Math.max(token1 length, token2 length)
    val editDistance : Double = EditDistance.editDistance(token1, token2, true)
    val lcp : Integer = LongestCommonPrefix(token1, token2)

    Math.max(1 - editDistance/maxLen, lcp/maxLen)
  }


  def LongestCommonPrefix(token1: String, token2: String) : Integer = {
    var lcp : Integer = 0
    var isCommon : Boolean = true
    val minLen = Math.min(token1 length, token2 length)

    while ((lcp < minLen) && (isCommon)) {
      if (token1.charAt(lcp)==token2.charAt(lcp)) {
        lcp += 1
      } else {
        isCommon = false
      }
    }

    lcp
  }
}
