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

    data class SliderStateResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: Float,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    data class RangeSliderStateResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: ClosedFloatingPointRange<Float>,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    abstract class GenericStateResult<V>(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        open val value: V,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)
}