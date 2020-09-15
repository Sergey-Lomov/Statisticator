package com.example.statisticator.service

import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.lang.Exception

class AttributeParsingException(message:String, var attributeId: String? = null): Exception(message)

class AttributesFactory {

    private val idKey = Constants.AttributeParsingKeys.Id.value

    private val attributedTypeToClass = mapOf(
        "number_interval" to NumberIntervalAttribute::class,
        "text_field" to TextFieldAttribute::class,
        "colors_list" to ColorsListAttribute::class,
        "array" to ArrayAttribute::class
    )

    fun attributeFromJson(jsonObject: JsonObject): EventAttribute {
        try {
            val type = jsonObject[Constants.AttributeParsingKeys.Type.value].asString
            val attributeClass =
                (attributedTypeToClass[type] ?: error("Unsupported attribute type")).java
            return gson.fromJson(jsonObject, attributeClass) as EventAttribute
        } catch(e: Exception) {
            if(jsonObject.has(idKey)) {
                val id = jsonObject[idKey].asString
                throw AttributeParsingException("Invalid attribute structure", id)
            } else {
                throw AttributeParsingException("Attribute without id")
            }
        }
    }

    fun attributesFromJson(jsonObjects: Iterable<JsonObject>): Iterable<EventAttribute> {
        return jsonObjects.map { attributeFromJson(it) }
    }

    fun jsonFromAttribute(attribute: EventAttribute): JsonObject {
        val string = gson.toJson(attribute)
        return JsonParser.parseString(string).asJsonObject
    }

    companion object {
        private val gson = GsonBuilder()
            .registerTypeAdapter(ColorsListAttribute::class.java, ColorsListAttributeAdapter())
            .registerTypeAdapter(ArrayAttribute::class.java, ArrayAttributeAdapter())
            .create()
    }
}