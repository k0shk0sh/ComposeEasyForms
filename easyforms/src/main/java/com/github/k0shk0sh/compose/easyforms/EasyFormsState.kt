package com.github.k0shk0sh.compose.easyforms

import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
     * therefore please ignore its implementation when its not necessary.
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

    @CallSuper
    open fun saveState(bundle: Bundle) {
        bundle.putSerializable("error", errorState.value)
    }

    @CallSuper
    open fun restoreState(bundle: Bundle) {
        errorState.value = bundle.getSerializable("error") as EasyFormsErrorState
    }
}

/**
 * @property defaultValue the default value to be used in the [TextField] if any provided.
 * @property easyFormsValidationType pass one of EasyForms validationTypes
 * or your custom type.
 */
data class EasyFormsTextFieldState(
    private val defaultValue: String = "",
    private val easyFormsValidationType: EasyFormsValidationType,
) : EasyFormsState<State<TextFieldValue>, TextFieldValue>() {

    init {
        if (defaultValue.isNotBlank()) {
            errorState.value = isValid(defaultValue)
        }
    }

    private val _state = mutableStateOf(TextFieldValue(defaultValue))
    override val state: State<TextFieldValue> get() = _state

    override val onValueChangedCallback: (TextFieldValue) -> Unit = { value ->
        _state.value = value
        errorState.value = isValid(value.text)
    }

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.StringResult(
        key = key,
        easyFormsErrorState = errorState.value,
        value = _state.value.text,
    )

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putString("value", _state.value.text)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = TextFieldValue(bundle.getString("value", defaultValue))
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
) : EasyFormsState<State<Boolean>, Boolean>() {
    init {
        if (!isRequired || defaultValue) errorState.value = EasyFormsErrorState.VALID
    }

    private val _state = mutableStateOf(defaultValue)

    override val state: State<Boolean> get() = _state

    override val onValueChangedCallback: (Boolean) -> Unit = { value ->
        _state.value = value
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
        value = _state.value,
    )

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putBoolean("value", _state.value)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = bundle.getBoolean("value", defaultValue)
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
) : EasyFormsState<State<ToggleableState>, Nothing>() {
    init {
        if (!isRequired || defaultValue == ToggleableState.On) {
            errorState.value = EasyFormsErrorState.VALID
        }
    }

    private val _state = mutableStateOf(defaultValue)
    override val state: State<ToggleableState> get() = _state

    /**
     * Used instead of [onValueChangedCallback] as this widget only accepts onClick.
     */
    val onClick = {
        val newState = when (state.value) {
            ToggleableState.On -> ToggleableState.Indeterminate
            ToggleableState.Off -> ToggleableState.On
            ToggleableState.Indeterminate -> ToggleableState.Off
        }
        _state.value = newState
        if (isRequired) {
            errorState.value = when (newState) {
                ToggleableState.Off -> EasyFormsErrorState.INVALID
                else -> EasyFormsErrorState.VALID
            }
        }
    }

    override val onValueChangedCallback: (Nothing) -> Unit
        get() = throw IllegalAccessError(
            "can't be used in toggleable checkbox, please use onClick instead"
        )

    override fun mapToResult(key: Any): EasyFormsResult = EasyFormsResult.ToggleableStateResult(
        key = key,
        easyFormsErrorState = errorState.value,
        value = _state.value,
    )

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putSerializable("value", state.value)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = bundle.getSerializable("value") as ToggleableState
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
) : EasyFormsState<State<Boolean>, Nothing>() {
    init {
        if (!isRequired || defaultValue) errorState.value = EasyFormsErrorState.VALID
    }

    private val _state = mutableStateOf(defaultValue)
    override val state: State<Boolean> get() = _state

    /**
     * Used instead of [onValueChangedCallback] as this widget only accepts onClick.
     */
    val onClick = {
        _state.value = !_state.value
        if (isRequired) {
            errorState.value = if (!_state.value) {
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
        value = _state.value,
        easyFormsErrorState = errorState.value
    )

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putBoolean("value", _state.value)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = bundle.getBoolean("value", defaultValue)
    }
}

/**
 * @property defaultValue the default value to be used in the [Switch].
 * @property isRequired defines if the [Switch] has to be toggled to be considered valid.
 */
data class EasyFormsSwitchState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<State<Boolean>, Boolean>() {
    init {
        if (!isRequired || defaultValue) errorState.value = EasyFormsErrorState.VALID
    }

    private val _state = mutableStateOf(defaultValue)
    override val state: State<Boolean> get() = _state

    override val onValueChangedCallback: (Boolean) -> Unit = { value ->
        _state.value = value
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
        value = _state.value,
    )

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putBoolean("value", _state.value)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = bundle.getBoolean("value", defaultValue)
    }
}

/**
 * @property defaultValue the default value to be used in the [Slider].
 * @property isRequired defines if the [Slider] value must be > 0 to be considered valid.
 */
data class EasyFormsSliderState(
    private val defaultValue: Float = 0F,
    private val isRequired: Boolean = true,
) : EasyFormsState<State<Float>, Float>() {
    init {
        if (!isRequired || defaultValue > 0F) errorState.value = EasyFormsErrorState.VALID
    }

    private val _state = mutableStateOf(defaultValue)
    override val state: State<Float> get() = _state

    override val onValueChangedCallback: (Float) -> Unit = {
        _state.value = it
    }

    /**
     * Triggered when the user stops sliding the [Slider] to determine the error state.
     */
    val onValueChangeFinished: () -> Unit = {
        if (isRequired) {
            errorState.value = when (_state.value) {
                0F -> EasyFormsErrorState.INVALID
                else -> EasyFormsErrorState.VALID
            }
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult.SliderStateResult {
        return EasyFormsResult.SliderStateResult(
            key = key,
            easyFormsErrorState = errorState.value,
            value = _state.value,
        )
    }

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putFloat("value", _state.value)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = bundle.getFloat("value", defaultValue)
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
) : EasyFormsState<State<ClosedFloatingPointRange<Float>>, ClosedFloatingPointRange<Float>>() {
    init {
        if (!isRequired || (defaultValue.start > 0F || defaultValue.endInclusive > 0F)) {
            errorState.value = EasyFormsErrorState.VALID
        }
    }

    private val _state = mutableStateOf(defaultValue)
    override val state: State<ClosedFloatingPointRange<Float>> get() = _state

    override val onValueChangedCallback: (ClosedFloatingPointRange<Float>) -> Unit = {
        _state.value = it
    }

    /**
     * Triggered when the user stops sliding the [RangeSlider] to determine the error state.
     */
    val onValueChangeFinished: () -> Unit = {
        if (isRequired) {
            errorState.value = when (_state.value) {
                0F..0F -> EasyFormsErrorState.INVALID
                else -> EasyFormsErrorState.VALID
            }
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult.RangeSliderStateResult {
        return EasyFormsResult.RangeSliderStateResult(
            key = key,
            easyFormsErrorState = errorState.value,
            value = _state.value,
        )
    }

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putFloat("start", _state.value.start)
        bundle.putFloat("end", _state.value.endInclusive)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = bundle.getFloat(
            "start", defaultValue.start,
        )..bundle.getFloat(
            "end", defaultValue.endInclusive,
        )
    }
}