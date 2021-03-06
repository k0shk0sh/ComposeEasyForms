package com.github.k0shk0sh.compose.easyforms.example.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.github.k0shk0sh.compose.easyforms.EasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsErrorState
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyEasyFormsCheckboxListState
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyFormKeys
import com.github.k0shk0sh.compose.easyforms.example.model.CheckboxModel

@Composable
fun CheckboxLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkboxState = easyForm.getCheckboxState(
            MyFormKeys.CHECKBOX,
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = checkboxState.state
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = checkboxState.onValueChangedCallback,
        )
        Space(8.dp)
        val errorState = checkboxState.errorState.value
        Text(errorState.toString(), color = if (errorState == EasyFormsErrorState.INVALID) {
            MaterialTheme.colors.error
        } else {
            MaterialTheme.colors.onBackground
        })
    }
}

@Composable
fun ListCustomCheckboxLayout(
    easyForm: EasyForms,
    list: List<CheckboxModel>,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val state = easyForm.addAndGetCustomState(
            MyFormKeys.LIST_CHECKBOX,
            MyEasyFormsCheckboxListState(
                list,
                isRequired = true,
            )
        )
        val checkedState = state.state
        Row {
            checkedState.forEach { checkboxModel ->
                Checkbox(
                    checked = checkboxModel.isSelected,
                    onCheckedChange = {
                        state.onValueChangedCallback(checkboxModel.id to it)
                    },
                )
            }
        }
        Space(8.dp)
        val errorState = state.errorState.value
        Text(errorState.toString(), color = if (errorState == EasyFormsErrorState.INVALID) {
            MaterialTheme.colors.error
        } else {
            MaterialTheme.colors.onBackground
        })
    }
}

@Composable
fun SwitchLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val state = easyForm.getSwitchState(
            MyFormKeys.SWITCH,
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = state.state
        Switch(
            checked = checkedState.value,
            onCheckedChange = state.onValueChangedCallback,
        )
        Space(8.dp)
        val errorState = state.errorState.value
        Text(errorState.toString(), color = if (errorState == EasyFormsErrorState.INVALID) {
            MaterialTheme.colors.error
        } else {
            MaterialTheme.colors.onBackground
        })
    }
}

@Composable
fun TriCheckboxLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkboxState = easyForm.getTriCheckboxState(
            MyFormKeys.TRI_CHECKBOX,
            defaultValue = ToggleableState.Indeterminate,
            isRequired = true,
        )
        val checkedState = checkboxState.state
        TriStateCheckbox(state = checkedState.value, onClick = checkboxState.onClick)
        Space(8.dp)
        val errorState = checkboxState.errorState.value
        Text(errorState.toString(), color = if (errorState == EasyFormsErrorState.INVALID) {
            MaterialTheme.colors.error
        } else {
            MaterialTheme.colors.onBackground
        })
    }
}

@Composable
fun RadioButtonLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val radioButtonState = easyForm.getRadioButtonState(
            MyFormKeys.RADIO_BUTTON,
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = radioButtonState.state
        RadioButton(
            selected = checkedState.value,
            onClick = radioButtonState.onClick,
        )
        Space(8.dp)
        val errorState = radioButtonState.errorState.value
        Text(errorState.toString(), color = if (errorState == EasyFormsErrorState.INVALID) {
            MaterialTheme.colors.error
        } else {
            MaterialTheme.colors.onBackground
        })
    }
}
