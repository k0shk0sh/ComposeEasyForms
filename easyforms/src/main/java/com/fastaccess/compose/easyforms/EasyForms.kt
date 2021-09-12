package com.fastaccess.compose.easyforms

import androidx.compose.ui.state.ToggleableState

class EasyForms {
    private val forms = mutableMapOf<Any, EasyFormState<*>>()

    fun getTextFieldState(
        key: Any,
        easyFormsValidationType: EasyFormsValidationType,
        defaultValue: String = "",
    ): EasyFormTextFieldState {
        val currentState = forms[key]
        if (currentState is EasyFormTextFieldState) return currentState
        val state = EasyFormTextFieldState(
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
    ): EasyFormCheckboxState {
        val currentState = forms[key]
        if (currentState is EasyFormCheckboxState) return currentState
        val state = EasyFormCheckboxState(
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
    ): EasyFormTriCheckboxState {
        val currentState = forms[key]
        if (currentState is EasyFormTriCheckboxState) return currentState
        val state = EasyFormTriCheckboxState(
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
    ): EasyFormRadioButtonState {
        val currentState = forms[key]
        if (currentState is EasyFormRadioButtonState) return currentState
        val state = EasyFormRadioButtonState(
            defaultValue = defaultValue,
            isRequired = isRequired
        )
        forms[key] = state
        return state
    }

    fun errorStates() = forms.values.map { it.errorState }
    fun formKeys() = forms.keys.toList()
    fun formData() = forms.map {
        when (val value = it.value) {
            is EasyFormTextFieldState -> EasyFormsResult.StringResult(
                name = it.key,
                easyFormsErrorState = value.errorState.value,
                value = value.state.value.text,
            )
            is EasyFormCheckboxState -> EasyFormsResult.BooleanResult(
                name = it.key,
                easyFormsErrorState = value.errorState.value,
                value = value.state.value,
            )
            is EasyFormRadioButtonState -> EasyFormsResult.BooleanResult(
                name = it.key,
                easyFormsErrorState = value.errorState.value,
                value = value.state.value,
            )
            is EasyFormTriCheckboxState -> EasyFormsResult.ToggleableStateResult(
                name = it.key,
                easyFormsErrorState = value.errorState.value,
                value = value.state.value,
            )
            else -> throw NullPointerException("${value::class.java} isn't supported yet!")
        }
    }
}