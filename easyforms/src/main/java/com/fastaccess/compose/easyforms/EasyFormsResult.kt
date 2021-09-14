package com.fastaccess.compose.easyforms

import androidx.compose.material.*
import androidx.compose.ui.state.ToggleableState

/**
 * A class that represent must of the Compose form fields result states.
 */
sealed class EasyFormsResult(
    open val name: Any,
    open val easyFormsErrorState: EasyFormsErrorState,
) {
    /**
     * Default class to handle [String] result from ex: [TextField].
     */
    data class StringResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: String,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [Boolean] result from ex: [Checkbox].
     */
    data class BooleanResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: Boolean,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [ToggleableState] result from ex: [TriStateCheckbox].
     */
    data class ToggleableStateResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: ToggleableState,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [Float] result from ex: [Slider].
     */
    data class SliderStateResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: Float,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [ClosedFloatingPointRange] result from ex: [RangeSlider].
     */
    data class RangeSliderStateResult(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: ClosedFloatingPointRange<Float>,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)

    /**
     * Abstract class that provide devs to hook their custom result class.
     */
    abstract class GenericStateResult<V>(
        override val name: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        open val value: V,
    ) : EasyFormsResult(name = name, easyFormsErrorState = easyFormsErrorState)
}