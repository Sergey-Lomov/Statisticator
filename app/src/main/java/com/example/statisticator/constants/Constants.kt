package com.example.statisticator.constants

import com.example.statisticator.models.schema.attributes.*

class Constants {

    enum class UniversalParsingKeys(val value: String) {
        Type("type"),
    }

    enum class AttributeParsingKeys(val value: String) {
        Type(UniversalParsingKeys.Type.value),
        Title("title"),
        Id("id"),

        Colors("colors"),
        Size("size"),
        SizeVar("sizeVar"),
        Prototype("prototype")
    }

    enum class ModificatorParsingKeys(val value: String) {
        Type(UniversalParsingKeys.Type.value),
    }

    enum class AttributeType(val value: String) {
        NumberInterval("number_interval"),
        TextField("text_field"),
        ColorsList("colors_list"),
        StaticArray("static_array"),
        DynamicArray("dynamic_array")
    }

    enum class EventParsingKeys(val value: String) {
        Id("id"),
        Type(UniversalParsingKeys.Type.value),
        Timestamp("timestamp")
    }

    enum class ExtrasKeys(val value: String) {
        Menu("menuModel"),
        Event("eventModel"),
        LoggingState("loggingState"),
        RequestsState("requestsState"),
        Requests("requests")
    }

    companion object {

        const val EVENT_EDITING_REQUEST_CODE = 1
        const val EVENT_EDITING_OK = 200

        const val DELEGATE_BUNDLE_KEY = "delegate"
        const val SESSION_STATE_BUNDLE_KEY = "sessionState"
        const val ATTRIBUTE_BUNDLE_KEY = "attribute"
        const val INITIAL_VALUE_BUNDLE_KEY = "initialValue"
        const val PREDEFINED_TITLE_BUNDLE_KEY = "predefinedTitle"

        const val COLORS_LIST_ITEMS_PER_ROW = 5
        const val LIST_ITEMS_PER_ROW = 5
        const val MENU_ITEMS_PER_ROW = 4
    }
}