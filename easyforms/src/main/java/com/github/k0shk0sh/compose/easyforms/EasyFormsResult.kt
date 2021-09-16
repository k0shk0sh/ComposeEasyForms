package com.github.k0shk0sh.compose.easyforms

import androidx.compose.material.*
import androidx.compose.ui.state.ToggleableState

/**
 * A class that represent must of the Compose form fields result states.
 * @param key a unique identifier for this state.
 * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
 */
sealed class EasyFormsResult(
    open val key: Any,
    open val easyFormsErrorState: EasyFormsErrorState,
) {
    /**
     * Default class to handle [String] result from ex: [TextField].
     * @param key a unique identifier for this state.
     * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
     * @param value the text entered in [TextField].
     */
    data class StringResult(
        override val key: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: String,
    ) : EasyFormsResult(key = key, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [Boolean] result from ex: [Checkbox].
     * @param key a unique identifier for this state.
     * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
     * @param value the toggleable state from [Checkbox], [RadioButton], [Switch] etc.
     */
    data class BooleanResult(
        override val key: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: Boolean,
    ) : EasyFormsResult(key = key, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [ToggleableState] result from ex: [TriStateCheckbox].
     * @param key a unique identifier for this state.
     * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
     * @param value the [ToggleableState] of [TriStateCheckbox].
     */
    data class ToggleableStateResult(
        override val key: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: ToggleableState,
    ) : EasyFormsResult(key = key, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [Float] result from ex: [Slider].
     * @param key a unique identifier for this state.
     * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
     * @param value the [Slider] position.
     */
    data class SliderStateResult(
        override val key: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: Float,
    ) : EasyFormsResult(key = key, easyFormsErrorState = easyFormsErrorState)

    /**
     * Default class to handle [ClosedFloatingPointRange] result from ex: [RangeSlider].
     * @param key a unique identifier for this state.
     * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
     * @param value the [ClosedFloatingPointRange] of [RangeSlider].
     */
    data class RangeSliderStateResult(
        override val key: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        val value: ClosedFloatingPointRange<Float>,
    ) : EasyFormsResult(key = key, easyFormsErrorState = easyFormsErrorState)

    /**
     * Abstract class that provide devs to hook their custom result class.
     * @property V defines which type to use as the value.
     * @param key a unique identifier for this state.
     * @param easyFormsErrorState the current [EasyFormsErrorState] this state has.
     * @param value the [V] value.
     */
    abstract class GenericStateResult<V>(
        override val key: Any,
        override val easyFormsErrorState: EasyFormsErrorState,
        open val value: V,
    ) : EasyFormsResult(key = key, easyFormsErrorState = easyFormsErrorState)
}