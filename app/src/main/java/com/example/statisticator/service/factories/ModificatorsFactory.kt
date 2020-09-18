package com.example.statisticator.service.factories

import com.example.statisticator.models.schema.modificators.*
import kotlin.reflect.KClass

class ModificatorsFactory : TypedEntitiesFactory<SessionStateModificator>() {

    override val typeToClass = mapOf(
        "add_addition" to AddAdditionModificator::class,
        "remove_addition" to RemoveAdditionModificator::class,
        "clear_additions" to ClearAdditionsModificator::class,
        "set_var" to SetVarModificator::class,
        "remove_var" to RemoveVarModificator::class,
        "clear_vars" to ClearVarsModificator::class
    )

    override val adapterToClass: Map<Any, KClass<SessionStateModificator>> = emptyMap()
}