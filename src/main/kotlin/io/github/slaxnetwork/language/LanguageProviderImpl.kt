package io.github.slaxnetwork.language

import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import kotlinx.serialization.json.*
import java.io.File
import java.util.Collections

class LanguageProviderImpl : LanguageProvider {
    private val _languages = mutableSetOf<String>()
    override val languages: Set<String>
        get() = Collections.unmodifiableSet(_languages)

    private val _messages = mutableMapOf<String, MutableMap<String, String>>()
    override val messages: Map<String, Map<String, String>>
        get() = Collections.unmodifiableMap(_messages)

    override fun register() {
        val languagesDir = File("languages")
        if(!languagesDir.exists() || !languagesDir.isDirectory) {
            throw RuntimeException("languages are not initialized in server environment.")
        }

        for(file in languagesDir.walk()) {
            if(!file.isFile || !file.extension.equals("json", true)) {
                continue
            }
            val obj = Json.parseToJsonElement(file.readText())
                .jsonObject

            val metaObj = obj["meta"]?.jsonObject
                ?: throw NullPointerException("meta null")
            val meta = Json.decodeFromJsonElement<LanguageFileMeta>(metaObj)

            _languages.add(meta.id)

            val messages = obj["messages"]?.jsonObject
                ?: throw NullPointerException("messages null")

            loadMessagesFromLanguage(meta.id, messages)
        }
    }

    private fun loadMessagesFromLanguage(languageId: String, messagesObj: JsonObject, prefix: String="") {
        // well we gotta init it at least.
        _messages[languageId] = mutableMapOf()

        for((k, v) in messagesObj.entries) {
            if(v is JsonPrimitive && v.isString) {
                _messages[languageId]
                    ?.put(
                        if(prefix.isEmpty()) k
                        else "${prefix}${k}",

                        v.content
                    )

                continue
            }

            if(v is JsonArray) {
                // TODO: 3/22/2023 tmp, please combine into one.
                val string = v.filterIsInstance<JsonPrimitive>()
                    .filter { it.isString }
                    .joinToString("\n") { it.content }

                _messages[languageId]
                    ?.put(
                        if(prefix.isEmpty()) k
                        else "${prefix}${k}",

                        string
                    )

                continue
            }

            if(v is JsonObject) {
                loadMessagesFromLanguage(languageId, v, "${prefix}${k}.")
            }
        }
    }
}