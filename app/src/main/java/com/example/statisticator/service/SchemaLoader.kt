package com.example.statisticator.service

import com.example.statisticator.constants.Constants
import com.example.statisticator.models.schema.attributes.EventAttribute
import com.example.statisticator.models.schema.*
import com.example.statisticator.models.schema.modificators.*
import com.example.statisticator.service.factories.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.Serializable
import java.lang.Exception

class SchemaLoader {

    private val classNameToFactory = mapOf (
        EventAttribute::class.simpleName to AttributesFactory()
    )

    private data class EventPrototype(val id: String,
                                      val type: String,
                                      val title: String? = null,
                                      val attributes: ArrayList<JsonObject>?,
                                      val modificators: ArrayList<JsonObject>?)
    private data class MenuItemPrototype (val id: String,
                                          val title: String,
                                          val icon: String?,
                                          val type: ItemTargetType?,
                                          val target: String?)
    private data class MenuPrototype(val id: String,
                                     val style: MenuStyle?,
                                     val items: ArrayList<String>)
    private data class SchemaPrototype (val title: String,
                                        val initialMenu: String,
                                        val menus: ArrayList<MenuPrototype>,
                                        val items: ArrayList<MenuItemPrototype>,
                                        val events: ArrayList<EventPrototype>)

    private val gson = GsonBuilder().create()

    data class Result(val schema: SchemaModel, val report: SchemaLoadingReport)

    fun loadFromJson(json: String): Result {
        val report = SchemaLoadingReport()
        val schemaPrototype = gson.fromJson(json, SchemaPrototype::class.java)

        val menusToPrototypes = schemaPrototype.menus.associateBy({
            MenuModel(
                it.id,
                it.style ?: MenuStyle.Full
            )
        }, {it})
        val menus = ArrayList<MenuModel>(menusToPrototypes.keys)

        val itemsToPrototypes = schemaPrototype.items.associateBy({
            MenuItemModel(
                it.id,
                it.title,
                it.icon
            )
        }, {it})
        val items = ArrayList<MenuItemModel>(itemsToPrototypes.keys)

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
            val itemsIds = menusToPrototypes[menuModel]?.items ?: arrayListOf()
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

            val protoAttributes = prototype.attributes ?: arrayListOf()
            val attributes = entitiesFrom(protoAttributes, AttributesFactory(), it, report)
            it.attributes.addAll(attributes)

            val protoModificators = prototype.modificators ?: arrayListOf()
            val modificators = entitiesFrom(protoModificators, ModificatorsFactory(), it, report)
            it.modificators.addAll(modificators)
        }

        val schema = SchemaModel(initialMenu, menus, items)
        return Result(schema, report)
    }

    private fun <T : Serializable>entitiesFrom(objects: Iterable<JsonObject>,
                                               factory: TypedEntitiesFactory<T>,
                                               event: EventModel,
                                               report: SchemaLoadingReport): Iterable<T> {
        try {
            return factory.attributesFromJson(objects)
        }
        catch(e: MissedEntityTypeException) {
            report.issues.add(EntityWithoutType(event, e.json))
        }
        catch(e: UnsupportedEntityTypeException) {
            report.issues.add(UnsupportedEntityType(event, e.json))
        }
        catch(e: InvalidEntityStructureException) {
            report.issues.add(InvalidEntityStructure(event, e.message, e.json))
        }
        return emptyList()
    }
}