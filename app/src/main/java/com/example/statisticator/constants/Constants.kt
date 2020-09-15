package com.example.statisticator.constants

class Constants {

    enum class AttributeParsingKeys(val value: String) {
        Type("type"),
        Title("title"),
        Id("id"),

        Colors("colors"),
        Size("size"),
        Prototype("prototype")
    }

    enum class ModificatorParsingKeys(val value: String) {
        Type("type"),
    }

    companion object {

        const val MENU_EXTRAS_KEY = "menuModel"
        const val EVENT_EXTRAS_KEY = "eventModel"
        const val SESSION_STATE_EXTRAS_KEY = "sessionState"

        const val DELEGATE_BUNDLE_KEY = "delegate"
        const val ATTRIBUTE_BUNDLE_KEY = "attribute"
        const val INITIAL_VALUE_BUNDLE_KEY = "initialValue"
        const val PREDEFINED_TITLE_BUNDLE_KEY = "predefinedTitle"

        const val COLORS_LIST_ITEMS_PER_ROW = 5
        const val LIST_ITEMS_PER_ROW = 5
        const val MENU_ITEMS_PER_ROW = 4

        const val EVENT_ID_KEY = "id"
        const val EVENT_TYPE_KEY = "type"
        const val EVENT_TIMESTAMP_KEY = "timestamp"
    }
}