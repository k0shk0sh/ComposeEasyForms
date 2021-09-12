package com.fastaccess.compose.easyforms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.TextFieldValue

abstract class EasyFormsState<T>(
    private val easyFormsValidationType: EasyFormsValidationType? = null,
) {
    abstract val state: MutableState<T>
    abstract val onValueChangedCallback: (T) -> Unit
    val errorState: MutableState<EasyFormsErrorState> by lazy {
        mutableStateOf(EasyFormsErrorState.INITIAL)
    }

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
) : EasyFormsState<TextFieldValue>(easyFormsValidationType) {

    override val state: MutableState<TextFieldValue> = mutableStateOf(
        TextFieldValue(defaultValue)
    )

    override val onValueChangedCallback: (TextFieldValue) -> Unit = { value ->
        state.value = value
        errorState.value = isValid(state.value.text)
    }

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
) : EasyFormsState<Boolean>() {
    override val state: MutableState<Boolean> = mutableStateOf(
        defaultValue
    )

    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

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
) : EasyFormsState<ToggleableState>() {
    override val state: MutableState<ToggleableState> = mutableStateOf(
        defaultValue
    )

    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

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
) : EasyFormsState<Boolean>() {
    override val state: MutableState<Boolean> = mutableStateOf(
        defaultValue
    )

    init {
        if (!isRequired) errorState.value = EasyFormsErrorState.VALID
    }

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

    @Composable
    fun rememberSaveable(): MutableState<Boolean> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }
}