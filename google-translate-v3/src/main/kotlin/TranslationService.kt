package org.example

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import io.github.cdimascio.dotenv.dotenv
import java.io.IOException

class TranslationService {

    private val dotenv = dotenv {
        directory = "./src/main/resources"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

    private val apiKey = dotenv["API_KEY"] ?: error("API_KEY is not set in .env file")
    private val projectId = dotenv["PROJECT_ID"] ?: error("PROJECT_ID is not set in .env file")

    private val translateUrl = "https://translation.googleapis.com/v3/projects/$projectId/locations/global:translateText?key=$apiKey"

    // 번역할 텍스트와 대상 언어를 설정하는 함수
    fun translateText(text: String, targetLanguage: String) {
        val client = OkHttpClient()

        // JSON 요청 본문 생성
        val json = JSONObject()
        json.put("contents", listOf(text))
        json.put("targetLanguageCode", targetLanguage)

        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // API 요청 생성
        val request = Request.Builder()
            .url(translateUrl)
            .post(requestBody)
            .build()

        // API 호출
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("번역 요청 실패: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        println("번역 요청 실패: ${response.message}")
                        return
                    }

                    // 응답 JSON 처리
                    val responseData = response.body?.string()
                    val jsonResponse = JSONObject(responseData)
                    val translations = jsonResponse.getJSONArray("translations")
                    val translatedText = translations.getJSONObject(0).getString("translatedText")

                    println("번역된 텍스트: $translatedText")
                }
            }
        })
    }
}