package com.example.statisticator.service

import android.graphics.Color
import com.example.statisticator.models.schema.attributes.ColorsListAttribute
import com.google.gson.*
import java.lang.reflect.Type

class ColorsListAttributeAdapter: JsonSerializer<ColorsListAttribute>, JsonDeserializer<ColorsListAttribute> {

    private val ID = "id"
    private val TITLE = "title"
    private val COLORS = "colors"

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

        jsonObject.addProperty(ID, attribute.id)
        jsonObject.addProperty(TITLE, attribute.title)
        jsonObject.add(COLORS, colorsArray)

        return jsonObject;
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ColorsListAttribute {
        val jsonObject = json as? JsonObject ?: error("Error at Color List Attribute parsing")

        val id = jsonObject[ID].asString
        val title = jsonObject[TITLE].asString
        val hexColors = jsonObject[COLORS].asJsonArray
        val colors = hexColors.map {Color.parseColor(it.asString)}

        return ColorsListAttribute(id, title, ArrayList(colors))
    }
}