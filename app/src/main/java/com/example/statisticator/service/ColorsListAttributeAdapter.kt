package com.example.statisticator.service

import android.graphics.Color
import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.ColorsListAttribute
import com.example.statisticator.service.factories.AttributeParsingException
import com.google.gson.*
import java.lang.reflect.Type

class ColorsListAttributeAdapter: JsonSerializer<ColorsListAttribute>, JsonDeserializer<ColorsListAttribute> {

    private val idKey = Constants.AttributeParsingKeys.Id.value
    private val titleKey = Constants.AttributeParsingKeys.Title.value
    private val colorsKey = Constants.AttributeParsingKeys.Colors.value

    override fun serialize(
        src: ColorsListAttribute?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        val attribute = src ?: return jsonObject

        val colorsArray = JsonArray()
        attribute.colors.forEach {
            val hexCode = String.format("#%06X", 0xFFFFFF and it)
            colorsArray.add(hexCode)
        }

        jsonObject.addProperty(idKey, attribute.id)
        jsonObject.addProperty(titleKey, attribute.title)
        jsonObject.add(colorsKey, colorsArray)

        return jsonObject
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ColorsListAttribute {
        val jsonObject = json as? JsonObject ?: throw AttributeParsingException("Error at Color List Attribute parsing")

        val id = jsonObject[idKey].asString
        val title = if (jsonObject.has(titleKey)) jsonObject[titleKey].asString else null
        val hexColors = jsonObject[colorsKey].asJsonArray
        val colors = hexColors.map {Color.parseColor(it.asString)}

        return ColorsListAttribute(id, title, ArrayList(colors))
    }
}