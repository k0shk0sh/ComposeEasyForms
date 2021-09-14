package com.fastaccess.compose.easyforms

import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.state.ToggleableState

class EasyForms {
    private val forms = mutableMapOf<Any, EasyFormsState<*, *>>()

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

    fun getSwitchState(
        key: Any,
        defaultValue: Boolean = false,
        isRequired: Boolean = true,
    ): EasyFormsSwitchState {
        val currentState = forms[key]
        if (currentState is EasyFormsSwitchState) return currentState
        val state = EasyFormsSwitchState(
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

    fun getSliderState(
        key: Any,
        defaultValue: Float = 0F,
        isRequired: Boolean = true,
    ): EasyFormsSliderState {
        val currentState = forms[key]
        if (currentState is EasyFormsSliderState) return currentState
        val state = EasyFormsSliderState(
            defaultValue = defaultValue,
            isRequired = isRequired
        )
        forms[key] = state
        return state
    }

    fun getRangeSliderState(
        key: Any,
        defaultValue: ClosedFloatingPointRange<Float> = 0F..0F,
        isRequired: Boolean = true,
    ): EasyFormsRangeSliderState {
        val currentState = forms[key]
        if (currentState is EasyFormsRangeSliderState) return currentState
        val state = EasyFormsRangeSliderState(
            defaultValue = defaultValue,
            isRequired = isRequired
        )
        forms[key] = state
        return state
    }

    @Suppress("unchecked_cast")
    fun <ST, CT, S : EasyFormsState<ST, CT>> addAndGetCustomState(
        key: Any,
        state: S,
    ): S {
        val currentState = forms[key]
        if ((currentState as? S) != null) return currentState
        forms[key] = state
        return state
    }

    fun errorStates() = forms.values.map { it.errorState }
    fun listenToErrorStates() = derivedStateOf { errorStates() }
    fun formKeys() = forms.keys.toList()
    fun formData() = forms.map { it.value.mapToResult(it.key) }
}