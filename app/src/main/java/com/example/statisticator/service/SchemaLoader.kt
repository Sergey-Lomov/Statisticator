package com.example.statisticator.service

import com.example.statisticator.models.schema.attributes.EventAttribute
import com.example.statisticator.models.schema.attributes.NumberIntervalAttribute
import com.example.statisticator.models.schema.*
import com.example.statisticator.models.schema.modificators.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.Exception

class SchemaLoader {

    private enum class ParsingKeys(val value: String) {
        attributeType("type"),
        modificatorType("type"),
        id("id")
    }

    private val attributedTypeToClass = mapOf("number_interval" to NumberIntervalAttribute::class)
    private val modificatorTypeToClass = mapOf(
        "add_addition" to AddAdditionModificator::class,
        "remove_addition" to RemoveAdditionModificator::class,
        "clear_additions" to ClearAdditionsModificator::class,
        "set_var" to SetVarModificator::class,
        "remove_var" to RemoveVarModificator::class,
        "clear_vars" to ClearVarsModificator::class)

    private data class EventPrototype(val id: String,
                                      val type: String,
                                      val title: String? = null,
                                      val attributes: Array<JsonObject>?,
                                      val modificators: Array<JsonObject>?)
    private data class MenuItemPrototype (val id: String,
                                          val title: String,
                                          val icon: String?,
                                          val type: ItemTargetType?,
                                          val target: String?)
    private data class MenuPrototype(val id: String,
                                     val style: MenuStyle?,
                                     val items: Array<String>)
    private data class SchemaPrototype (val title: String,
                                        val initialMenu: String,
                                        val menus: Array<MenuPrototype>,
                                        val items: Array<MenuItemPrototype>,
                                        val events: Array<EventPrototype>)

    data class Result(val schema: SchemaModel, val report: SchemaLoadingReport)

    fun loadFromJson(json: String): Result {
        val report = SchemaLoadingReport()
        val schemaPrototype = Gson().fromJson(json, SchemaPrototype::class.java)

        val menusToPrototypes = schemaPrototype.menus.associateBy({
            MenuModel(
                it.id,
                it.style ?: MenuStyle.Full
            )
        }, {it})
        val menus = menusToPrototypes.keys

        val itemsToPrototypes = schemaPrototype.items.associateBy({
            MenuItemModel(
                it.id,
                it.title,
                it.icon
            )
        }, {it})
        val items = itemsToPrototypes.keys

        val eventsToPrototypes = schemaPrototype.events.associateBy({
            EventModel(
                it.id,
                it.type,
                it.title
            )
        }, {it})
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

            val protoAttributes = prototype.attributes ?: emptyArray()
            val attributes = attributesFrom(protoAttributes, it, report)
            it.attributes.addAll(attributes)

            val protoModificators = prototype.modificators ?: emptyArray()
            val modificators = modificatorsFrom(protoModificators, it, report)
            it.modificators.addAll(modificators)
        }

        val schema = SchemaModel(
            initialMenu,
            menus.toTypedArray(),
            items.toTypedArray()
        )
        return Result(schema, report)
    }

    private fun attributesFrom(objects: Array<JsonObject>,
                               event: EventModel,
                               report: SchemaLoadingReport): ArrayList<EventAttribute> {
        val attributes = ArrayList<EventAttribute>()
        val typeKey = ParsingKeys.attributeType.value
        objects.forEach {
            try {
                val type = it[typeKey].asString
                val attributeClass = attributedTypeToClass[type]!!.java
                val attribute = Gson().fromJson(it, attributeClass) as EventAttribute
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
            }
        }

        return attributes
    }

    private fun modificatorsFrom(objects: Array<JsonObject>,
                                 event: EventModel,
                                 report: SchemaLoadingReport): ArrayList<SessionStateModificator> {
        val modificators = ArrayList<SessionStateModificator>()
        val typeKey = ParsingKeys.modificatorType.value
        objects.forEach {
            try {
                val type = it[typeKey].asString
                val modificatorClass = modificatorTypeToClass[type]!!.java
                val modificator = Gson().fromJson(it, modificatorClass) as SessionStateModificator
                modificators.add(modificator)
            }
            catch(e: Exception) {
                val issue = InvalidModificatorStructure(event)
                report.issues.add(issue)
            }
        }

        return modificators
    }
}