package com.fastaccess.compose.easyforms.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fastaccess.compose.easyforms.*
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
            PasswordTextField(easyForm)
            Space()
            NameTextField(easyForm)
            Space()
            UrlTextField(easyForm)
            Space()
            PhoneTextField(easyForm)
            Space()
            CardTextField(easyForm)
            Space()
            SmallText("Checkbox")
            CheckboxLayout(easyForm)
            Space()
            SmallText("TriCheckbox")
            TriCheckboxLayout(easyForm)
            Space()
            SmallText("RadioButton")
            RadioButtonLayout(easyForm)
            Space()
            SmallText("Slider")
            SliderLayout(easyForm)
            Space()
            SmallText("RangeSlider")
            RangeSliderLayout(easyForm)
            Space(34.dp)
            val formDataState = rememberSaveable() {
                mutableStateOf<String?>(null)
            }
            LoginButton(easyForm, formDataState)
            Space(34.dp)
            if (!formDataState.value.isNullOrEmpty()) {
                Text(formDataState.value!!, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
private fun LoginButton(
    easyForm: EasyForms,
    formDataState: MutableState<String?>,
) {
    val errorStates = easyForm.listenToErrorStates()
    Button(
        onClick = {
            formDataState.value = "${easyForm.formData()}"
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
private fun SliderLayout(easyForm: EasyForms) {
    Column {
        val state = easyForm.getSliderState("slider")
        val sliderPosition = state.rememberSaveable().value
        Text(text = "${state.errorState.value}:$sliderPosition")
        Slider(
            value = sliderPosition,
            onValueChange = state.onValueChangedCallback,
            valueRange = 0f..100f,
            onValueChangeFinished = state.onValueChangeFinished,
            steps = 5,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RangeSliderLayout(easyForm: EasyForms) {
    Column {
        val state = easyForm.getRangeSliderState("rangeslider")
        val sliderPosition = state.rememberSaveable().value

        Text(text = "${state.errorState.value}:$sliderPosition")
        RangeSlider(
            steps = 5,
            values = sliderPosition,
            onValueChange = state.onValueChangedCallback,
            valueRange = 0f..100f,
            onValueChangeFinished = state.onValueChangeFinished,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )
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
private fun PasswordTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState("password", PasswordValidationType)
    val textState = textFieldState.rememberSaveable()
    val passwordVisibility = remember { mutableStateOf(false) }
    TextField(
        value = textState.value,
        onValueChange = textFieldState.onValueChangedCallback,
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
        label = { Text("Password") },
        placeholder = { Text("email@example.com") },
        leadingIcon = {
            Icon(
                Icons.Outlined.Password,
                "Password",
            )
        },
        trailingIcon = {
            val image = if (passwordVisibility.value) {
                Icons.Outlined.Visibility
            } else {
                Icons.Outlined.VisibilityOff
            }

            IconButton(onClick = {
                passwordVisibility.value = !passwordVisibility.value
            }) {
                Icon(imageVector = image, "")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun UrlTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState("url", UrlValidationType)
    val textState = textFieldState.rememberSaveable()
    TextField(
        value = textState.value,
        onValueChange = textFieldState.onValueChangedCallback,
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
        label = { Text("Url") },
        placeholder = { Text("https://example.come") },
        leadingIcon = {
            Icon(
                Icons.Outlined.Web,
                "Password",
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun PhoneTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState("phone", PhoneNumberValidationType)
    val textState = textFieldState.rememberSaveable()
    TextField(
        value = textState.value,
        onValueChange = textFieldState.onValueChangedCallback,
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
        label = { Text("Phone") },
        placeholder = { Text("+1 (222) 123 1234") },
        leadingIcon = {
            Icon(
                Icons.Outlined.Phone,
                "Phone",
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun CardTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState("creditcard", CardValidationType)
    val textState = textFieldState.rememberSaveable()
    TextField(
        value = textState.value,
        onValueChange = textFieldState.onValueChangedCallback,
        isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
        label = { Text("Card") },
        placeholder = { Text("5555 5555 5555 5555") },
        leadingIcon = {
            Icon(
                Icons.Outlined.CreditCard,
                "Card",
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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