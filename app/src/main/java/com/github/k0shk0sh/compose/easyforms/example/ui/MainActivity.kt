package com.github.k0shk0sh.compose.easyforms.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsErrorState
import com.github.k0shk0sh.compose.easyforms.example.MainViewModel
import com.github.k0shk0sh.compose.easyforms.example.model.listOfCheckboxes
import com.github.k0shk0sh.compose.easyforms.example.ui.components.*
import com.github.k0shk0sh.compose.easyforms.example.ui.theme.ComposeFormsValidationTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFormsValidationTheme {
                EasyForm {
                    viewModel.onButtonClicked(it)
                }
            }
        }
    }
}

@Composable
private fun EasyForm(
    onButtonClicked: (EasyForms) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EasyForms") }
            )
        }
    ) {
        BuildEasyForms { easyForm ->
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
                Salutation(easyForm)
                Space()
                NameTextField(easyForm)
                Space()
                UrlTextField(easyForm)
                Space()
                PhoneTextField(easyForm)
                Space()
                CardTextField(easyForm)
                Space()
                SmallText("Custom onUnfocus error")
                CustomOnNotFocusErrorTextField(easyForm)
                Space()
                SmallText("Checkbox")
                CheckboxLayout(easyForm)
                Space()
                SmallText("Custom ListCheckbox (Min Checked 2)")
                ListCustomCheckboxLayout(easyForm, listOfCheckboxes())
                Space()
                SmallText("TriCheckbox")
                TriCheckboxLayout(easyForm)
                Space()
                SmallText("RadioButton")
                RadioButtonLayout(easyForm)
                Space()
                SmallText("Switch")
                SwitchLayout(easyForm)
                Space()
                SmallText("Slider")
                SliderLayout(easyForm)
                Space()
                SmallText("RangeSlider")
                RangeSliderLayout(easyForm)
                Space(34.dp)
                LoginButton(easyForm, onClick = { onButtonClicked.invoke(easyForm) })
            }
        }
    }
}

@Composable
private fun LoginButton(
    easyForm: EasyForms,
    onClick: () -> Unit,
) {
    Column {
        val errorStates = easyForm.observeFormStates()
        val formDataState = rememberSaveable() { mutableStateOf<String?>(null) }
        Button(
            onClick = {
                formDataState.value = "${easyForm.formData()}"
                onClick.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = errorStates.value.all { it.value == EasyFormsErrorState.VALID }
        ) {
            Text("Login")
        }
        Space(34.dp)
        if (!formDataState.value.isNullOrEmpty()) {
            Text(formDataState.value!!, style = MaterialTheme.typography.caption)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewForm() {
    EasyForm {}
}