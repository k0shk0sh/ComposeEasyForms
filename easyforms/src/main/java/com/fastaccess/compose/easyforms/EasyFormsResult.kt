package com.fastaccess.compose.easyforms

import androidx.compose.ui.state.ToggleableState

sealed class EasyFormsResult(
    open val name: Any,
    open val easyFormsErrorState: EasyFormsErrorState,
) {
    data class StringResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: String,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    data class BooleanResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: Boolean,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    data class ToggleableStateResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: ToggleableState,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)
}