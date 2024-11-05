package org.example

import io.github.cdimascio.dotenv.dotenv
import com.google.cloud.translate.v3.*
import com.google.cloud.translate.v3.TranslationServiceClient
import com.google.cloud.translate.v3.TranslationServiceSettings

class TranslationService {

    private val dotenv = dotenv {
        directory = "./"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

    private val projectId = dotenv["PROJECT_ID"] ?: error("PROJECT_ID is not set in .env file")

    fun translateText(text: String, targetLanguage: String) {
        val settings = TranslationServiceSettings.newBuilder().build()
        TranslationServiceClient.create(settings).use { client ->
            // Make request
            val request = TranslateTextRequest.newBuilder()
                .setParent(LocationName.of(projectId, "global").toString())
                .addContents(text)
                .setMimeType("text/plain")
                .setTargetLanguageCode(targetLanguage)
                .build()

            // Call API
            val response = client.translateText(request)
            for (translation in response.translationsList) {
                println("Translated text: ${translation.translatedText}")
            }
        }
    }

}