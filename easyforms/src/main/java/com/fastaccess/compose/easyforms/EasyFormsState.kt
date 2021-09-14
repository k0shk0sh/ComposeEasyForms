package com.fastaccess.compose.easyforms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.TextFieldValue

abstract class EasyFormsState<ST, CT>(
    private val easyFormsValidationType: EasyFormsValidationType? = null,
) {
    abstract val state: ST
    abstract val onValueChangedCallback: (CT) -> Unit
    val errorState: MutableState<EasyFormsErrorState> by lazy {
        mutableStateOf(EasyFormsErrorState.INITIAL)
    }

    abstract fun mapToResult(name: Any): EasyFormsResult

    fun isValid(value: String): EasyFormsErrorState {
        return when (easyFormsValidationType?.isValid(value)) {
            true -> EasyFormsErrorState.VALID
            else -> EasyFormsErrorState.INVALID
        }
    }
}

data class EasyFormsTextFieldState(
    private val defaultValue: String = "",
    private val easyFormsValidationType: EasyFormsValidationType,
) : EasyFormsState<MutableState<TextFieldValue>, TextFieldValue>(easyFormsValidationType) {

    override val state: MutableState<TextFieldValue> = mutableStateOf(
        TextFieldValue(defaultValue)
    )

    override val onValueChangedCallback: (TextFieldValue) -> Unit = { value ->
        state.value = value
        errorState.value = isValid(state.value.text)
    }

    override fun mapToResult(name: Any): EasyFormsResult = EasyFormsResult.StringResult(
        name = name,
        easyFormsErrorState = errorState.value,
        value = state.value.text,
    )

    @Composable
    fun rememberSaveable(): MutableState<TextFieldValue> {
        return androidx.compose.runtime.saveable.rememberSaveable(stateSaver = TextFieldValue.Saver) {
            state
        }
    }
}

data class EasyFormsCheckboxState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Boolean>, Boolean>() {
    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
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

    override fun mapToResult(name: Any): EasyFormsResult = EasyFormsResult.BooleanResult(
        name = name,
        easyFormsErrorState = errorState.value,
        value = state.value,
    )

    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }
}

data class EasyFormsTriCheckboxState(
    private val defaultValue: ToggleableState = ToggleableState.Indeterminate,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<ToggleableState>, ToggleableState>() {
    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

    override val state: MutableState<ToggleableState> = mutableStateOf(defaultValue)

    val onClick = {
        val newState = when (state.value) {
            ToggleableState.On -> ToggleableState.Indeterminate
            ToggleableState.Off -> ToggleableState.On
            ToggleableState.Indeterminate -> ToggleableState.Off
        }
        state.value = newState
        if (isRequired) {
            errorState.value = when (newState) {
                ToggleableState.On -> EasyFormsErrorState.VALID
                ToggleableState.Off -> EasyFormsErrorState.INVALID
                ToggleableState.Indeterminate -> EasyFormsErrorState.INITIAL
            }
        }
    }

    /**
     * @hide
     */
    override val onValueChangedCallback: (ToggleableState) -> Unit
        get() = throw IllegalAccessError(
            "can't be used in toggleable checkbox, please use onClick instead"
        )

    override fun mapToResult(name: Any): EasyFormsResult = EasyFormsResult.ToggleableStateResult(
        name = name,
        easyFormsErrorState = errorState.value,
        value = state.value,
    )

    @Composable
    fun rememberSaveable(): MutableState<ToggleableState> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }
}

data class EasyFormsRadioButtonState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Boolean>, Boolean>() {
    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

    override val state: MutableState<Boolean> = mutableStateOf(defaultValue)

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
    override val onValueChangedCallback: (Boolean) -> Unit
        get() = throw IllegalAccessError(
            "can't be used in RadioButton, please use onClick instead"
        )

    override fun mapToResult(name: Any): EasyFormsResult = EasyFormsResult.BooleanResult(
        name = name,
        value = state.value,
        easyFormsErrorState = errorState.value
    )

    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }
}

data class EasyFormsSwitchState(
    private val defaultValue: Boolean = false,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Boolean>, Boolean>() {
    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
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

    override fun mapToResult(name: Any): EasyFormsResult = EasyFormsResult.BooleanResult(
        name = name,
        easyFormsErrorState = errorState.value,
        value = state.value,
    )

    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }
}

data class EasyFormsSliderState(
    private val defaultValue: Float = 0F,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<Float>, Float>() {
    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

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

    override fun mapToResult(name: Any): EasyFormsResult.SliderStateResult {
        return EasyFormsResult.SliderStateResult(
            name = name,
            easyFormsErrorState = errorState.value,
            value = state.value,
        )
    }

    @Composable
    fun rememberSaveable(): MutableState<Float> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }
}

data class EasyFormsRangeSliderState(
    private val defaultValue: ClosedFloatingPointRange<Float> = 0F..0F,
    private val isRequired: Boolean = true,
) : EasyFormsState<MutableState<ClosedFloatingPointRange<Float>>, ClosedFloatingPointRange<Float>>() {
    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

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

    override fun mapToResult(name: Any): EasyFormsResult.RangeSliderStateResult {
        return EasyFormsResult.RangeSliderStateResult(
            name = name,
            easyFormsErrorState = errorState.value,
            value = state.value,
        )
    }

    @Composable
    fun rememberSaveable(): MutableState<ClosedFloatingPointRange<Float>> {
        return androidx.compose.runtime.saveable.rememberSaveable(stateSaver = Saver) {
            state
        }
    }

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