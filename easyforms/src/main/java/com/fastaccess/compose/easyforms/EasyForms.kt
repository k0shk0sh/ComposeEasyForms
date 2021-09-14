package com.fastaccess.compose.easyforms

import androidx.compose.material.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.state.ToggleableState

/**
 * A class that manage all your forms states.
 * Always create one instance per ViewModel or Compose screen.
 */
class EasyForms {
    /**
     * A map that holds all [EasyFormsState].
     */
    private val forms = mutableMapOf<Any, EasyFormsState<*, *>>()

    /**
     * Create or Get a state that handle [TextField].
     * @param key a unique identifier for this state.
     * @param easyFormsValidationType the validation type to use for this state.
     * @param defaultValue the default value to be inserted into the [TextField].
     * @return [EasyFormsTextFieldState] that provide TextField state handling.
     */
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

    /**
     * Create or Get a state that handle [Checkbox].
     * @param key a unique identifier for this state.
     * @param defaultValue control if the [Checkbox] default state should be checked/unchecked.
     * @param isRequired if this is true, then the user must thick the checkbox to change its state to valid.
     * @return [EasyFormsCheckboxState] that provide Checkbox state handling.
     */
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

    /**
     * Create or Get a state that handle [Switch].
     * @param key a unique identifier for this state.
     * @param defaultValue control if the [Switch] default state should be checked/unchecked.
     * @param isRequired if this is true, then the user must toggle the Switch to change its state to valid.
     * @return [EasyFormsSwitchState] that provide Switch state handling.
     */
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

    /**
     * Create or Get a state that handle [TriStateCheckbox].
     * @param key a unique identifier for this state.
     * @param defaultValue control what the [TriStateCheckbox] default state should be.
     * @param isRequired if this is true, then the user must toggle the state to ON to be valid.
     * @return [EasyFormsTriCheckboxState] that provide TriStateCheckbox state handling.
     */
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

    /**
     * Create or Get a state that handle [RadioButton].
     * @param key a unique identifier for this state.
     * @param defaultValue control if the [RadioButton] default state should be checked/unchecked.
     * @param isRequired if this is true, then the user must toggle the RadioButton to change its state to valid.
     * @return [EasyFormsRadioButtonState] that provide RadioButton state handling.
     */
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

    /**
     * Create or Get a state that handle [Slider].
     * @param key a unique identifier for this state.
     * @param defaultValue control where the [Slider] position should be placed at.
     * @param isRequired if this is true, then the user must select a position that is bigger than 0F.
     * @return [EasyFormsSliderState] that provide Slider state handling.
     */
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

    /**
     * Create or Get a state that handle [RangeSlider].
     * @param key a unique identifier for this state.
     * @param defaultValue control where the [RangeSlider] position should be placed at.
     * @param isRequired if this is true, then the user must select a position between 0F and xF.
     * @return [EasyFormsRangeSliderState] that provide RangeSlider state handling
     */
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

    /**
     * Create or Get a custom state provided by you.
     * @param key a unique identifier for this state.
     * @param state your custom state that should be stored in [forms].
     * @return your custom state after its being added to [forms] if it wasn't already.
     */
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

    /**
     * @return All form states as in list of mutableState of [EasyFormsErrorState].
     */
    fun formStates() = forms.values.map { it.errorState }

    /**
     * This method is useful when you only want to enable/disable
     * your CTA until all required fields are Valid.
     * @return derivedState of all your form fields error states.
     */
    fun observeFormStates() = derivedStateOf { formStates() }

    /**
     * @return All keys provided by you.
     */
    fun formKeys() = forms.keys.toList()

    /**
     * @return All form filed data as in list of [EasyFormsResult].
     */
    fun formData() = forms.map { it.value.mapToResult(it.key) }
}