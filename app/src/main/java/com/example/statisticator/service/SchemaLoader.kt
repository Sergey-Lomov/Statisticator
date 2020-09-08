package com.example.statisticator.service

import android.content.res.Resources
import com.example.statisticator.R
import com.example.statisticator.models.*
import com.example.statisticator.models.attributes.EventAttributeModel
import com.example.statisticator.models.attributes.NumberIntervalAttribute
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.Exception
import kotlin.reflect.KClass

class SchemaLoader {

    private enum class ParsingKeys(val value: String) {
        attributeType("type"),
        id("id")
    }

    private val attributedTypeToClass = mapOf("number_interval" to NumberIntervalAttribute::class)

    private data class MenuItemPrototype (val id: String, val title: String, val type: ItemTargetType?, val target: String?)
    private data class MenuPrototype(val id: String, val type: MenuType?, val items: Array<String>)
    private data class EventPrototype(val id: String, val title: String? = null, val attributes: Array<JsonObject>)
    private data class SchemaPrototype (val initialMenu: String,
                                        val menus: Array<MenuPrototype>,
                                        val items: Array<MenuItemPrototype>,
                                        val events: Array<EventPrototype>)

    data class Result(val schema: SchemaModel, val report: SchemaLoadingReport)

    fun loadFromJson(json: String): Result {
        val report = SchemaLoadingReport()
        val schemaPrototype = Gson().fromJson(json, SchemaPrototype::class.java)

        val menusToPrototypes = schemaPrototype.menus.associateBy({ MenuModel(it.id, it.type ?: MenuType.Full) }, {it})
        val menus = menusToPrototypes.keys
        val itemsToPrototypes = schemaPrototype.items.associateBy({ MenuItemModel(it.id, it.title) }, {it})
        val items = itemsToPrototypes.keys
        val eventsToPrototypes = schemaPrototype.events.associateBy({ EventModel(it.id, it.title) }, {it})
        val events = eventsToPrototypes.keys

        val initialMenu = menus.first { it.id == schemaPrototype.initialMenu }
        menus.forEach {menuModel ->
            val itemsIds = menusToPrototypes[menuModel]?.items ?: emptyArray()
            val itItems = items.filter { itemsIds.contains(it.id) }
            menuModel.items.addAll(itItems)
        }

        items.forEach each@{item ->
            val prototype = itemsToPrototypes[item] ?: return@each
            val type = prototype.type ?: return@each
            when (type) {
                ItemTargetType.Menu -> item.target = menus.first { it.id == prototype.target }
                ItemTargetType.Event -> item.target = events.first { it.id == prototype.target }
            }
        }
        items.filter { it.target == null }.forEach {
            val issue = ItemWithoutTarget(it)
            report.issues.add(issue)
        }

        events.forEach eachEvent@{
            val prototype = eventsToPrototypes[it] ?: return@eachEvent
            val attributes = attributesFrom(prototype.attributes, it, report)
            it.attributes.addAll(attributes)
        }

        val schema = SchemaModel(initialMenu, menus.toTypedArray(), items.toTypedArray())
        return Result(schema, report)
    }

    private fun attributesFrom(objects: Array<JsonObject>,
                               event: EventModel,
                               report: SchemaLoadingReport): ArrayList<EventAttributeModel> {
        val attributes = ArrayList<EventAttributeModel>()
        val typeKey = ParsingKeys.attributeType.value
        objects.forEach eachAttribute@{
            try {
                val type = it[typeKey].asString
                val attributeClass = attributedTypeToClass[type]!!.java
                val attribute = Gson().fromJson(it, attributeClass) as EventAttributeModel
                attributes.add(attribute)
            }
            catch(e: Exception) {
                val idKey = ParsingKeys.id.value
                if(it.has(idKey)) {
                    val id = it[idKey].asString
                    val issue = InvalidAttributeStructure(event, id)
                    report.issues.add(issue)
                } else {
                    val issue = AttributeWithoutId(event)
                    report.issues.add(issue)
                }
                return@eachAttribute
            }
        }

        return attributes
    }
}