package com.github.k0shk0sh.compose.easyforms

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.TextFieldValue

/**
 * @param ST defines the State type, for ex: [MutableState], [SnapshotStateList] etc.
 * @param CT defines the type to use in [onValueChangedCallback].
 */
abstract class EasyFormsState<ST, CT> {
    /**
     * This is the [ST] you defined when you inherited from this class.
     */
    abstract val state: ST

    /**
     * This callback is usually being used by most of Form fields of Compose,
     * however there are some widgets that can't use this callback,
     * therefore please ignore its implementation when its not neccessary.
     */
    abstract val onValueChangedCallback: (CT) -> Unit

    /**
     * The error state that is used across all children of [EasyFormsState].
     */
    val errorState: MutableState<EasyFormsErrorState> by lazy {
        mutableStateOf(EasyFormsErrorState.INITIAL)
    }

    /**
     * @param key the identifier key for the state.
     * @return a mapped object of [EasyFormsResult].
     */
    abstract fun mapToResult(key: Any): EasyFormsResult
}

/**
 * @property defaultValue the default value to be used in the [TextField] if any provided.
 * @property easyFormsValidationType pass one of EasyForms validationTypes
 * or your custom type.
 */
data class EasyFormsTextFieldState(
    private val defaultValue: String = "",
    private val easyFormsValidationType: EasyFormsValidationType,
) : EasyFormsState<MutableState<TextFieldValue>, TextFieldValue>() {

    init {
        if (defaultValue.isNotBlank()) {
            errorState.value = isValid(defaultValue)
        }
    }

    override val state: MutableState<TextFieldValue> = mutableStateOf(
        TextFieldValue(defaultValue)
    )

    override val onValueChangedCallback: (TextFieldValue) -> Unit = { value ->
        state.value = value
        errorState.value = isValid(state.value.text)
    }

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.StringResult(
        key = key,
        easyFormsErrorState = errorState.value,
        value = state.value.text,
    )

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<TextFieldValue> {
        return rememberSaveable(stateSaver = TextFieldValue.Saver) {
            state
        }
    }

    private fun isValid(value: String): EasyFormsErrorState {
        return when (easyFormsValidationType.isValid(value)) {
            true -> EasyFormsErrorState.VALID
            false -> EasyFormsErrorState.INVALID
        }
    }
}

/**
 * @property defaultValue the default value to be used in the [Checkbox].
 * @property isRequired defines if the [Checkbox] has to be checked to be considered valid.
 */
data class EasyFormsCheckboxState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Boolean>, Boolean>() {
    init {
        if (!isRequired || defaultValue) errorState.value = EasyFormsErrorState.VALID
    }

    override val state: MutableState<Boolean> = mutableStateOf(defaultValue)

    override val onValueChangedCallback: (Boolean) -> Unit = { value ->
        state.value = value
        if (isRequired) {
            errorState.value = if (!value) {
                EasyFormsErrorState.INVALID
            } else {
                EasyFormsErrorState.VALID
            }
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.BooleanResult(
        key = key,
        easyFormsErrorState = errorState.value,
        value = state.value,
    )

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return rememberSaveable {
            state
        }
    }
}

/**
 * @property defaultValue The default value to be used in the [TriStateCheckbox].
 * @property isRequired defines if the [TriStateCheckbox]
 * can't be unchecked to be considered valid.
 */
data class EasyFormsTriCheckboxState(
    private val defaultValue: ToggleableState = ToggleableState.Indeterminate,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<ToggleableState>, Nothing>() {
    init {
        if (!isRequired || defaultValue == ToggleableState.On) {
            errorState.value = EasyFormsErrorState.VALID
        }
    }

    override val state: MutableState<ToggleableState> = mutableStateOf(defaultValue)

    /**
     * Used instead of [onValueChangedCallback] as this widget only accepts onClick.
     */
    val onClick = {
        val newState = when (state.value) {
            ToggleableState.On -> ToggleableState.Indeterminate
            ToggleableState.Off -> ToggleableState.On
            ToggleableState.Indeterminate -> ToggleableState.Off
        }
        state.value = newState
        if (isRequired) {
            errorState.value = when (newState) {
                ToggleableState.Off -> EasyFormsErrorState.INVALID
                else -> EasyFormsErrorState.VALID
            }
        }
    }

    /**
     * @hide
     */
    override val onValueChangedCallback: (Nothing) -> Unit
        get() = throw IllegalAccessError(
            "can't be used in toggleable checkbox, please use onClick instead"
        )

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.ToggleableStateResult(
        key = key,
        easyFormsErrorState = errorState.value,
        value = state.value,
    )

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<ToggleableState> {
        return rememberSaveable {
            state
        }
    }
}

/**
 * @property defaultValue the default value to be used in the [RadioButton].
 * @property isRequired defines if the [RadioButton]
 * has to be checked to be considered valid.
 */
data class EasyFormsRadioButtonState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Boolean>, Nothing>() {
    init {
        if (!isRequired || defaultValue) errorState.value = EasyFormsErrorState.VALID
    }

    override val state: MutableState<Boolean> = mutableStateOf(defaultValue)

    /**
     * Used instead of [onValueChangedCallback] as this widget only accepts onClick.
     */
    val onClick = {
        state.value = !state.value
        if (isRequired) {
            errorState.value = if (!state.value) {
                EasyFormsErrorState.INVALID
            } else {
                EasyFormsErrorState.VALID
            }
        }
    }

    /**
     * @hide
     */
    override val onValueChangedCallback: (Nothing) -> Unit
        get() = throw IllegalAccessError(
            "can't be used in RadioButton, please use onClick instead"
        )

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.BooleanResult(
        key = key,
        value = state.value,
        easyFormsErrorState = errorState.value
    )

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return rememberSaveable {
            state
        }
    }
}

/**
 * @property defaultValue the default value to be used in the [Switch].
 * @property isRequired defines if the [Switch] has to be toggled to be considered valid.
 */
data class EasyFormsSwitchState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Boolean>, Boolean>() {
    init {
        if (!isRequired || defaultValue) errorState.value = EasyFormsErrorState.VALID
    }

    override val state: MutableState<Boolean> = mutableStateOf(defaultValue)

    override val onValueChangedCallback: (Boolean) -> Unit = { value ->
        state.value = value
        if (isRequired) {
            errorState.value = if (!value) {
                EasyFormsErrorState.INVALID
            } else {
                EasyFormsErrorState.VALID
            }
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.BooleanResult(
        key = key,
        easyFormsErrorState = errorState.value,
        value = state.value,
    )

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return rememberSaveable {
            state
        }
    }
}

/**
 * @property defaultValue the default value to be used in the [Slider].
 * @property isRequired defines if the [Slider] value must be > 0 to be considered valid.
 */
data class EasyFormsSliderState(
    private val defaultValue: Float = 0F,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Float>, Float>() {
    init {
        if (!isRequired || defaultValue > 0F) errorState.value = EasyFormsErrorState.VALID
    }

    /**
     * Triggered when the user stops sliding the [Slider] to determine the error state.
     */
    val onValueChangeFinished: () -> Unit = {
        if (isRequired) {
            errorState.value = when (state.value) {
                0F -> EasyFormsErrorState.INVALID
                else -> EasyFormsErrorState.VALID
            }
        }
    }

    override val state: MutableState<Float> = mutableStateOf(defaultValue)

    override val onValueChangedCallback: (Float) -> Unit = {
        state.value = it
    }

    override fun mapToResult(key: Any): EasyFormsResult.SliderStateResult {
        return EasyFormsResult.SliderStateResult(
            key = key,
            easyFormsErrorState = errorState.value,
            value = state.value,
        )
    }

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<Float> {
        return rememberSaveable {
            state
        }
    }
}

/**
 * @property defaultValue the default value to be used in the [RangeSlider].
 * @property isRequired defines if the [RangeSlider]
 * range must be in 0f..xf to be considered valid.
 */
data class EasyFormsRangeSliderState(
    private val defaultValue: ClosedFloatingPointRange<Float> = 0F..0F,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<ClosedFloatingPointRange<Float>>, ClosedFloatingPointRange<Float>>() {
    init {
        if (!isRequired || (defaultValue.start > 0F || defaultValue.endInclusive > 0F)) {
            errorState.value = EasyFormsErrorState.VALID
        }
    }

    /**
     * Triggered when the user stops sliding the [RangeSlider] to determine the error state.
     */
    val onValueChangeFinished: () -> Unit = {
        if (isRequired) {
            errorState.value = when (state.value) {
                0F..0F -> EasyFormsErrorState.INVALID
                else -> EasyFormsErrorState.VALID
            }
        }
    }

    override val state: MutableState<ClosedFloatingPointRange<Float>> = mutableStateOf(defaultValue)

    override val onValueChangedCallback: (ClosedFloatingPointRange<Float>) -> Unit = {
        state.value = it
    }

    override fun mapToResult(key: Any): EasyFormsResult.RangeSliderStateResult {
        return EasyFormsResult.RangeSliderStateResult(
            key = key,
            easyFormsErrorState = errorState.value,
            value = state.value,
        )
    }

    /**
     * Returns a [rememberSaveable] state that will survive across configuration changes
     */
    @Composable
    fun rememberSaveable(): MutableState<ClosedFloatingPointRange<Float>> {
        return rememberSaveable(stateSaver = Saver) {
            state
        }
    }

    /**
     * @suppress
     */
    companion object {
        /**
         * The default [Saver] implementation for [ClosedFloatingPointRange].
         */
        private val Saver = Saver<ClosedFloatingPointRange<Float>, List<Float>>(
            save = { arrayListOf(it.start, it.endInclusive) },
            restore = { it[0]..it[1] }
        )
    }
}