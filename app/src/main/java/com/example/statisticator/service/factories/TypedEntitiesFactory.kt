package com.example.statisticator.service.factories

import com.example.statisticator.constants.Constants
import com.google.gson.*
import java.io.Serializable
import java.lang.Exception
import kotlin.reflect.KClass

class MissedEntityTypeException(val json: String): Exception("Missed entity type")
class UnsupportedEntityTypeException(val json: String): Exception("Unsupported entity type")
class InvalidEntityStructureException(val error: String?, val json: String): Exception("Invalid entity structure")

abstract class TypedEntitiesFactory<T: Serializable> {

    private val typeKey = Constants.UniversalParsingKeys.Type.value
    protected abstract val typeToClass: Map<String, KClass<out T>>
    protected abstract val adapterToClass: Map<Any, KClass<out T>>

    private val gson: Gson by lazy {
        val builder = GsonBuilder()
        adapterToClass.forEach {
            builder.registerTypeAdapter(it.value.java, it.key)
        }
        builder.create()
    }

    fun entityFromJson(jsonObject: JsonObject): T {
        val type = if (jsonObject.has(typeKey)) jsonObject[typeKey].asString
            else throw MissedEntityTypeException(jsonObject.toString())
        val entityClass = typeToClass[type]
            ?: throw UnsupportedEntityTypeException(jsonObject.toString())
        try {
            return gson.fromJson(jsonObject, entityClass.java)
        } catch (e: Exception) {
            throw InvalidEntityStructureException(e.message, jsonObject.toString())
        }
    }

    fun attributesFromJson(jsonObjects: Iterable<JsonObject>): Iterable<T> {
        return jsonObjects.map { entityFromJson(it) }
    }

    fun jsonFromEntity(attribute: T): JsonObject {
        val string = gson.toJson(attribute)
        return JsonParser.parseString(string).asJsonObject
    }
}