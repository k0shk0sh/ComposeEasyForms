package com.fastaccess.compose.easyforms

import androidx.compose.ui.state.ToggleableState

class EasyForms {
    private val forms = mutableMapOf<Any, EasyFormsState<*>>()

    fun getTextFieldState(
        key: Any,
        easyFormsValidationType: EasyFormsValidationType,
        defaultValue: String = "",
    ): EasyFormsTextFieldState {
        val currentState = forms[key]
        if (currentState is EasyFormsTextFieldState) return currentState
        val state = EasyFormsTextFieldState(
            defaultValue = defaultValue,
            easyFormsValidationType = easyFormsValidationType
        )
        forms[key] = state
        return state
    }

    fun getCheckboxState(
        key: Any,
        defaultValue: Boolean = false,
        isRequired: Boolean = true,
    ): EasyFormsCheckboxState {
        val currentState = forms[key]
        if (currentState is EasyFormsCheckboxState) return currentState
        val state = EasyFormsCheckboxState(
            defaultValue = defaultValue,
            isRequired = isRequired
        )
        forms[key] = state
        return state
    }

    fun getTriCheckboxState(
        key: Any,
        defaultValue: ToggleableState = ToggleableState.Indeterminate,
        isRequired: Boolean = true,
    ): EasyFormsTriCheckboxState {
        val currentState = forms[key]
        if (currentState is EasyFormsTriCheckboxState) return currentState
        val state = EasyFormsTriCheckboxState(
            defaultValue = defaultValue,
            isRequired = isRequired
        )
        forms[key] = state
        return state
    }

    fun getRadioButtonState(
        key: Any,
        defaultValue: Boolean = false,
        isRequired: Boolean = true,
    ): EasyFormsRadioButtonState {
        val currentState = forms[key]
        if (currentState is EasyFormsRadioButtonState) return currentState
        val state = EasyFormsRadioButtonState(
            defaultValue = defaultValue,
            isRequired = isRequired
        )
        forms[key] = state
        return state
    }

    fun errorStates() = forms.values.map { it.errorState }
    fun formKeys() = forms.keys.toList()
    fun formData() = forms.map { it.value.mapToResult(it.key) }
}