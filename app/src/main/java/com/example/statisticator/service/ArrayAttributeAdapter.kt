package com.example.statisticator.service

import android.graphics.Color
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.ArrayAttribute
import com.example.statisticator.models.schema.attributes.ColorsListAttribute
import com.example.statisticator.models.schema.attributes.EditableAttribute
import com.google.gson.*
import java.lang.reflect.Type

class ArrayAttributeAdapter: JsonSerializer<ArrayAttribute>, JsonDeserializer<ArrayAttribute> {

    private val idKey = Constants.AttributeParsingKeys.Id.value
    private val titleKey = Constants.AttributeParsingKeys.Title.value
    private val sizeKey = Constants.AttributeParsingKeys.Size.value
    private val prototypeKey = Constants.AttributeParsingKeys.Prototype.value

    override fun serialize(
        src: ArrayAttribute?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        val attribute = src ?: return jsonObject

        val prototypeObject = AttributesFactory().jsonFromAttribute(attribute.prototype)
        jsonObject.addProperty(idKey, attribute.id)
        jsonObject.addProperty(titleKey, attribute.title)
        jsonObject.add(prototypeKey, prototypeObject)

        return jsonObject
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ArrayAttribute {
        val jsonObject = json as? JsonObject ?: throw AttributeParsingException("Error at Array Attribute parsing")

        val id = jsonObject[idKey].asString
        val title = jsonObject[titleKey].asString
        val size = jsonObject[sizeKey].asInt
        val prototypeObject = jsonObject[prototypeKey].asJsonObject
        val prototype = AttributesFactory().attributeFromJson(prototypeObject) as? EditableAttribute
            ?: throw AttributeParsingException("Not editable attribute used as prototype in array attribute", id)

        return ArrayAttribute(id, title, size, prototype)
    }
}