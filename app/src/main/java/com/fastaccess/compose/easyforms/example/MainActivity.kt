package com.fastaccess.compose.easyforms.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fastaccess.compose.easyforms.EasyForms
import com.fastaccess.compose.easyforms.EasyFormsErrorState
import com.fastaccess.compose.easyforms.EmailValidationType
import com.fastaccess.compose.easyforms.NameValidationType
import com.fastaccess.compose.easyforms.example.ui.theme.ComposeFormsValidationTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFormsValidationTheme {
                EasyForm(viewModel.easyForms)
            }
        }
    }
}

@Composable
private fun EasyForm(easyForm: EasyForms) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EasyForms") }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            EmailTextField(easyForm)
            Space()
            NameTextField(easyForm)
            Space()
            SmallText("Checkbox")
            CheckboxLayout(easyForm)
            Space()
            SmallText("TriCheckbox")
            TriCheckboxLayout(easyForm)
            Space()
            SmallText("RadioButton")
            RadioButtonLayout(easyForm)
            Space(34.dp)
            LoginButton(easyForm)
        }
    }
}

@Composable
private fun LoginButton(easyForm: EasyForms) {
    val errorStates = derivedStateOf { easyForm.errorStates() }
    Button(
        onClick = {
            Log.i("FormData", "${easyForm.formData()}")
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = errorStates.value.all { it.value == EasyFormsErrorState.VALID }
    ) {
        Text("Login")
    }
}

@Composable
private fun SmallText(text: String) {
    Text(text = text, style = MaterialTheme.typography.overline)
}

@Composable
private fun CheckboxLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkboxState = easyForm.getCheckboxState(
            "checkbox",
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = checkboxState.rememberSaveable()
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
private fun TriCheckboxLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkboxState = easyForm.getTriCheckboxState(
            "tricheckbox",
            defaultValue = ToggleableState.Indeterminate,
            isRequired = true,
        )
        val checkedState = checkboxState.rememberSaveable()
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
private fun RadioButtonLayout(easyForm: EasyForms) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val radioButtonState = easyForm.getRadioButtonState(
            "radio",
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = radioButtonState.rememberSaveable()
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

@Composable
private fun NameTextField(easyForm: EasyForms) {
    val nameTextFieldState = easyForm.getTextFieldState("name", NameValidationType)
    val nameState = nameTextFieldState.rememberSaveable()
    TextField(
        value = nameState.value,
        onValueChange = nameTextFieldState.onValueChangedCallback,
        isError = nameTextFieldState.errorState.value == EasyFormsErrorState.INVALID,
        label = { Text("Name") },
        placeholder = { Text("Joe") },
        leadingIcon = {
            Icon(
                Icons.Outlined.Person,
                contentDescription = "Name",
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun EmailTextField(easyForm: EasyForms) {
    val emailTextFieldState = easyForm.getTextFieldState("email", EmailValidationType)
    val emailState = emailTextFieldState.rememberSaveable()
    TextField(
        value = emailState.value,
        onValueChange = emailTextFieldState.onValueChangedCallback,
        isError = emailTextFieldState.errorState.value == EasyFormsErrorState.INVALID,
        label = { Text("Email") },
        placeholder = { Text("email@example.com") },
        leadingIcon = {
            Icon(
                Icons.Outlined.Email,
                "Email",
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun Space(
    padding: Dp = 16.dp,
) {
    Spacer(modifier = Modifier.padding(padding))
}

@Preview(showBackground = true)
@Composable
fun ReviewForm() {
    EasyForm(easyForm = EasyForms())
}