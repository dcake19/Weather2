package com.example.utils_ui

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

fun highlight(text: String,textToHighlight: String,normalColor: Int,highlightColor: Int): Spannable{
    val lowerCaseText = text.toLowerCase()
    val lowerCaseTextToHighlight = textToHighlight.toLowerCase()

    val indicesToHighlight = mutableListOf<Int>()

    var startIndex = lowerCaseText.indexOf(lowerCaseTextToHighlight)

    if (startIndex >= 0 && textToHighlight.isNotEmpty()) {
        while (startIndex < lowerCaseText.length && startIndex >= 0) {
            for (index in startIndex until (startIndex + textToHighlight.length)) {
                indicesToHighlight.add(index)
            }
            startIndex = lowerCaseText.indexOf(lowerCaseTextToHighlight,startIndex + textToHighlight.length)
        }
    }

    val spannable = SpannableString(text)

    for (index in text.indices){
        val letterColor = if (indicesToHighlight.contains(index)) highlightColor else normalColor
        spannable.setSpan(ForegroundColorSpan(letterColor),index,index+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    return spannable
}