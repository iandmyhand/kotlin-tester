package org.example

fun main() {
    println("Hello World!")
    val text = "Hello, world!"
    val targetLanguage = "ko"

    val translator = TranslationService()
    translator.translateText(text, targetLanguage)
}