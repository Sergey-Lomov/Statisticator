package com.example.statisticator.service.factories

import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.*
import com.example.statisticator.service.ArrayAttributeAdapter
import com.example.statisticator.service.ColorsListAttributeAdapter
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.lang.Exception
import kotlin.reflect.KClass

class AttributeParsingException(message:String, var attributeId: String? = null): Exception(message)

class AttributesFactory : TypedEntitiesFactory<EventAttribute>() {

    override val typeToClass = mapOf(
        Constants.AttributeType.NumberInterval.value to NumberIntervalAttribute::class,
        Constants.AttributeType.TextField.value to TextFieldAttribute::class,
        Constants.AttributeType.ColorsList.value to ColorsListAttribute::class,
        Constants.AttributeType.StaticArray.value to ArrayAttribute::class,
        Constants.AttributeType.DynamicArray.value to ArrayAttribute::class
    )

    override val adapterToClass = mapOf(
        ColorsListAttributeAdapter() to ColorsListAttribute::class,
        ArrayAttributeAdapter() to ArrayAttribute::class
    )
}