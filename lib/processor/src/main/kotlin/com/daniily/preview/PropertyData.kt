package com.daniily.preview

import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueParameter

internal data class PropertyData(
    val name: String,
    val type: KSTypeReference,
    val defaultValue: String,
) {

    companion object {
        fun KSValueParameter.toPropertyData(): PropertyData {
            return PropertyData(
                name = name?.getShortName() ?: "",
                type = type,
                defaultValue = this.type.getDefaultValue()
            )
        }

        private fun KSTypeReference.getDefaultValue(defaultValue: String = "null") = with(resolve()) {
            if (isMarkedNullable) {
                defaultValue
            } else when (
                this.declaration.qualifiedName?.asString()
            ) {
                "kotlin.String" -> "\"\""
                "kotlin.Boolean" -> "false"
                else -> error("Unsupported type")
            }
        }
    }
}
