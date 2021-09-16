
![build-status](https://github.com/k0shk0sh/ComposeEasyForms/actions/workflows/build.yml/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.k0shk0sh/compose-easyforms/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.k0shk0sh/compose-easyforms)

# Compose EasyForms
Focus on building your form UI while the library do the heavy work for you.

## Features

### Built in support for most of the Form widgets in Compose
- [TextField](#TextField)
- [Checkbox](#Checkbox)
- [TriStateCheckbox](#TriStateCheckbox)
- [RadioButton](#RadioButton)
- [Switch](#Switch)
- [Slider](#Slider)
- [RangeSlider](#RangeSlider)
- [Create your own](#CustomState)
- [Obsever Form state](#ObserveState)

## Examples

##### TextField
EasyForms provide some of the commom used textfields validation:
- Email validation
```kotlin
    @Composable
    fun EmailTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.EMAIL, EmailValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```
- Password validation
```kotlin
    @Composable
    fun PasswordTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.PASSWORD, PasswordValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```

- Phone validation
```kotlin
    @Composable
    fun PhoneTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.PHONE, PhoneValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```

- URL validation
```kotlin
    @Composable
    fun UrlTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.URL, UrlValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```

- Name validation
```kotlin
    @Composable
    fun NameTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.NAME, NameValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```
- Cards validation
```kotlin
    @Composable
    fun CardTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.CARD, CardValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```

Custom Validation:
- You can provide your own validator for EasyForms to use, for example you could provide a custom regex only, a min & max length only or combine them all together and EasyForms will ensure the validatiy base on your configuration.

```kotlin
    object MyCustomValidationType : EasyFormsValidationType(
        regex = "MyAwesomeRegex",
        minLength = 10,
        maxLength = 30,
    )

    object MyCustomRegexValidationType : EasyFormsValidationType(
        regex = "MyAwesomeRegex"
    )

    object MyCustomLengthValidationType : EasyFormsValidationType(
        minLength = 20,
        maxLength = 50,
    )
```

To use your custom validation:

```kotlin
    @Composable
    fun MyTextField(easyForm: EasyForms) {
        val textFieldState = easyForm.getTextFieldState(MyFormKeys.MY_KEY, MyCustomValidationType)
        val state = textFieldState.rememberSaveable()
        TextField(
            value = state.value,
            onValueChange = textFieldState.onValueChangedCallback,
            isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
         }
    }
```

##### Checkbox

```kotlin
    @Composable
    fun CheckboxLayout(easyForm: EasyForms) {
        val checkboxState = easyForm.getCheckboxState(
            MyFormKeys.CHECKBOX,
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = checkboxState.rememberSaveable()
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = checkboxState.onValueChangedCallback,
        ) 
    }
```

##### TriStateCheckbox

```kotlin
    @Composable
    fun TriCheckboxLayout(easyForm: EasyForms) {
        val checkboxState = easyForm.getTriCheckboxState(
            MyFormKeys.TRI_CHECKBOX,
            defaultValue = ToggleableState.Indeterminate,
            isRequired = true,
        )
        val checkedState = checkboxState.rememberSaveable()
        TriStateCheckbox(
            state = checkedState.value,
            onClick = checkboxState.onClick,
        )
    }
```

##### RadioButton

```kotlin
    @Composable
    fun RadioButtonLayout(easyForm: EasyForms) {
        val radioButtonState = easyForm.getRadioButtonState(
            MyFormKeys.RADIO_BUTTON,
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = radioButtonState.rememberSaveable()
        RadioButton(
            state = checkedState.value,
            onClick = radioButtonState.onClick,
        )
    }
```

##### Switch

```kotlin
    @Composable
    fun SwitchLayout(easyForm: EasyForms) {
        val switchState = easyForm.getSwitchState(
            MyFormKeys.SWITCH,
            defaultValue = false,
            isRequired = true,
        )
        val checkedState = switchState.rememberSaveable()
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = switchState.onValueChangedCallback,
        ) 
    }
```

##### Slider

```kotlin
    @Composable
    fun SliderLayout(easyForm: EasyForms) {
        val state = easyForm.getSliderState(
            key = MyFormKeys.SLIDER,
            defaultValue = 0F,
            isRequired = true,
        )
        val sliderPosition = state.rememberSaveable()
        Slider(
            value = sliderPosition.value,
            onValueChange = state.onValueChangedCallback,
            onValueChangeFinished = state.onValueChangeFinished,
        )
    }
```

##### RangeSlider

```kotlin
    @Composable
    fun RangeSliderLayout(easyForm: EasyForms) {
        val state = easyForm.getRangeSliderState(
            key = MyFormKeys.RANGE_SLIDER,
            defaultValue = 0F..0F,
            isRequired = true
        )
        val sliderPosition = state.rememberSaveable()
        RangeSlider(
            value = sliderPosition.value,
            onValueChange = state.onValueChangedCallback,
            onValueChangeFinished = state.onValueChangeFinished,
        )
    }
```

##### CustomState

You can use one of the already defined `EasyFormsState` for most cases, however when you need something that `EasyFormsState` doesn't provide then you could simply create your own. Please follow [this link](https://github.com/k0shk0sh/ComposeEasyForms/blob/main/app/src/main/java/com/github/k0shk0sh/compose/easyforms/example/ui/components/Dropdown.kt#L22) for more details.


##### ObserveState

```kotlin
    @Composable 
    fun LoginButton(
        easyForms: EasyForms,
        onClick: () -> Unit,
    ) {
        val errorStates = easyForms.observeFormStates()
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = errorStates.value.all {
                it.value == EasyFormsErrorState.VALID 
            }
        ) {
            Text("Submit")
        }
    }
```


## Download

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.k0shk0sh:compose-easyforms:<version>")
}
```

> For more examples with all widgets and how to create your own custom states handler please refer to the project example.


## Contributions
Please contribute! We will gladly review any pull requests.
Make sure to read the [Contributing](.github/CONTRIBUTING.md) page first though.
