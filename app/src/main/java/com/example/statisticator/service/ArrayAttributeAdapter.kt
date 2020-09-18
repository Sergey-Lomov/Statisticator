package com.example.statisticator.service

import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.*
import com.example.statisticator.service.factories.AttributeParsingException
import com.example.statisticator.service.factories.AttributesFactory
import com.google.gson.*
import java.lang.reflect.Type

class ArrayAttributeAdapter: JsonSerializer<ArrayAttribute>, JsonDeserializer<ArrayAttribute> {

    private val idKey = Constants.AttributeParsingKeys.Id.value
    private val titleKey = Constants.AttributeParsingKeys.Title.value
    private val typeKey = Constants.AttributeParsingKeys.Type.value
    private val sizeKey = Constants.AttributeParsingKeys.Size.value
    private val sizeVarKey = Constants.AttributeParsingKeys.SizeVar.value
    private val prototypeKey = Constants.AttributeParsingKeys.Prototype.value

    private val staticType = Constants.AttributeType.StaticArray.value
    private val dynamicType = Constants.AttributeType.DynamicArray.value

    override fun serialize(
        src: ArrayAttribute?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        val attribute = src ?: return jsonObject

        val prototypeObject = AttributesFactory().jsonFromEntity(attribute.prototype)
        jsonObject.addProperty(idKey, attribute.id)
        jsonObject.addProperty(titleKey, attribute.title)
        jsonObject.add(prototypeKey, prototypeObject)

        when (attribute) {
            is StaticArrayAttribute -> jsonObject.addProperty(sizeKey, attribute.size)
            is DynamicArrayAttribute ->  jsonObject.addProperty(sizeVarKey, attribute.sizeVar)
        }

        return jsonObject
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ArrayAttribute {
        val jsonObject = json as? JsonObject ?: throw AttributeParsingException("Error at Array Attribute parsing")

        val id = jsonObject[idKey].asString
        val type = jsonObject[typeKey].asString
        val title = if (jsonObject.has(titleKey)) jsonObject[titleKey].asString else null
        val prototypeObject = jsonObject[prototypeKey].asJsonObject
        val prototype = AttributesFactory().entityFromJson(prototypeObject) as? EditableAttribute
            ?: throw AttributeParsingException("Not editable attribute used as prototype in array attribute", id)

        return when (type) {
            staticType -> {
                val size = jsonObject[sizeKey].asInt
                StaticArrayAttribute(id, title, size, prototype)
            }
            dynamicType -> {
                val sizeVar = jsonObject[sizeVarKey].asString
                DynamicArrayAttribute(id, title, sizeVar, prototype)
            }
            else -> throw AttributeParsingException("Unsupported type of array attribute", id)
        }
    }
}