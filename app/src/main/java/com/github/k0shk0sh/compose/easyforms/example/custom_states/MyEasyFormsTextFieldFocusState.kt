package com.github.k0shk0sh.compose.easyforms.example.custom_states

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.text.input.TextFieldValue
import com.github.k0shk0sh.compose.easyforms.EasyFormsErrorState
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.github.k0shk0sh.compose.easyforms.EasyFormsState
import com.github.k0shk0sh.compose.easyforms.EasyFormsValidationType

class MyEasyFormsTextFieldFocusState(
    private val easyFormsValidationType: EasyFormsValidationType,
) : EasyFormsState<State<TextFieldValue>, TextFieldValue>() {

    private var isFocused = false
    private val _state = mutableStateOf(TextFieldValue(""))
    override val state: State<TextFieldValue> get() = _state

    override val onValueChangedCallback: (TextFieldValue) -> Unit = {
        _state.value = it
        if (isValid(it.text) == EasyFormsErrorState.VALID) {
            errorState.value = EasyFormsErrorState.VALID
        }
    }

    val onFocusChanged: (FocusState) -> Unit = { focusState ->
        val newState = focusState.isFocused
        if (newState != isFocused) {
            if (!newState) errorState.value = isValid(_state.value.text)
            isFocused = focusState.isFocused
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult {
        return EasyFormsResult.StringResult(
            key = key,
            easyFormsErrorState = errorState.value,
            value = state.value.text,
        )
    }

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putString("value", _state.value.text)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        _state.value = TextFieldValue(bundle.getString("value", ""))
    }


    private fun isValid(value: String): EasyFormsErrorState {
        return when (easyFormsValidationType.isValid(value)) {
            true -> EasyFormsErrorState.VALID
            false -> EasyFormsErrorState.INVALID
        }
    }
}