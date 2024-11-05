package org.example

fun main() {
    val text = "Hello, world!"
    val targetLanguage = "ko"
    println("Original text: $text")

    val translator = TranslationService()
    translator.translateText(text, targetLanguage)
}