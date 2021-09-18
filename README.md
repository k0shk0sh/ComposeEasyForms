![build-status](https://github.com/k0shk0sh/ComposeEasyForms/actions/workflows/build.yml/badge.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.k0shk0sh/compose-easyforms/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.k0shk0sh/compose-easyforms)

# Compose EasyForms

Focus on building your form UI while the library do the heavy work for you.

## Features

### Built in support for most of the Form widgets in Compose

- [Initializing](#Initializing)
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

#### Initializing

> EasyForms handles process death out of the box 😎.

```kotlin
@Composable
fun BuildMyAwesomeForm(
  onClick: (EasyForms) -> Unit,
) {
  Scaffold(
    ....
  ) {
    // BuildEasyForms fun provided by EasyForms
    BuildEasyForms { easyForm ->
      Column {
        // your Composables
        LoginButton(easyForms) { onClick(easyForms) }
      }
    }
  }
}
```

##### TextField

EasyForms provide some of the commom used textfields validation:

- Email validation

```kotlin
@Composable
fun EmailTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(
    key = MyFormKeys.EMAIL,
    easyFormsValidationType = EmailValidationType,
    defaultValue = "",
  )
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

- Password validation

```kotlin
@Composable
fun PasswordTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(
    key = MyFormKeys.PASSWORD,
    easyFormsValidationType = PasswordValidationType,
    defaultValue = "",
  )
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

- Phone validation

```kotlin
@Composable
fun PhoneTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(
    key = MyFormKeys.PHONE,
    easyFormsValidationType = PhoneValidationType,
    defaultValue = "",
  )
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

- URL validation

```kotlin
@Composable
fun UrlTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(
    key = MyFormKeys.URL,
    easyFormsValidationType = UrlValidationType,
    defaultValue = "",
  )
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

- Name validation

```kotlin
@Composable
fun NameTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(
    key = MyFormKeys.NAME,
    easyFormsValidationType = NameValidationType,
    defaultValue = "",
  )
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

- Cards validation

```kotlin
@Composable
fun CardTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(
    key = MyFormKeys.CARD,
    easyFormsValidationType = CardValidationType,
    defaultValue = "",
  )
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

Custom Validation:

- You can provide your own validator for EasyForms to use, for example you could provide a custom
  regex only, a min and/or max length only or combine them all together and EasyForms will ensure the
  validity based on your configuration.

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
fun MyTextField(easyForms: EasyForms) {
  val textFieldState = easyForms.getTextFieldState(MyFormKeys.MY_KEY, MyCustomValidationType)
  val state = textFieldState.state
  TextField(
    value = state.value,
    onValueChange = textFieldState.onValueChangedCallback,
    isError = textFieldState.errorState.value == EasyFormsErrorState.INVALID,
  )
}
```

##### Checkbox

```kotlin
@Composable
fun CheckboxLayout(easyForms: EasyForms) {
  val checkboxState = easyForms.getCheckboxState(
    MyFormKeys.CHECKBOX,
    defaultValue = false,
    isRequired = true,
  )
  val checkedState = checkboxState.state
  Checkbox(
    checked = checkedState.value,
    onCheckedChange = checkboxState.onValueChangedCallback,
  )
}
```

##### TriStateCheckbox

```kotlin
@Composable
fun TriCheckboxLayout(easyForms: EasyForms) {
  val checkboxState = easyForms.getTriCheckboxState(
    MyFormKeys.TRI_CHECKBOX,
    defaultValue = ToggleableState.Indeterminate,
    isRequired = true,
  )
  val checkedState = checkboxState.state
  TriStateCheckbox(
    state = checkedState.value,
    onClick = checkboxState.onClick,
  )
}
```

##### RadioButton

```kotlin
@Composable
fun RadioButtonLayout(easyForms: EasyForms) {
  val radioButtonState = easyForms.getRadioButtonState(
    MyFormKeys.RADIO_BUTTON,
    defaultValue = false,
    isRequired = true,
  )
  val checkedState = radioButtonState.state
  RadioButton(
    state = checkedState.value,
    onClick = radioButtonState.onClick,
  )
}
```

##### Switch

```kotlin
@Composable
fun SwitchLayout(easyForms: EasyForms) {
  val switchState = easyForms.getSwitchState(
    MyFormKeys.SWITCH,
    defaultValue = false,
    isRequired = true,
  )
  val checkedState = switchState.state
  Checkbox(
    checked = checkedState.value,
    onCheckedChange = switchState.onValueChangedCallback,
  )
}
```

##### Slider

```kotlin
@Composable
fun SliderLayout(easyForms: EasyForms) {
  val state = easyForms.getSliderState(
    key = MyFormKeys.SLIDER,
    defaultValue = 0F,
    isRequired = true,
  )
  val sliderPosition = state.state
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
fun RangeSliderLayout(easyForms: EasyForms) {
  val state = easyForms.getRangeSliderState(
    key = MyFormKeys.RANGE_SLIDER,
    defaultValue = 0F..0F,
    isRequired = true
  )
  val sliderPosition = state.state
  RangeSlider(
    value = sliderPosition.value,
    onValueChange = state.onValueChangedCallback,
    onValueChangeFinished = state.onValueChangeFinished,
  )
}
```

##### CustomState

You can use one of the already defined `EasyFormsState` for most cases, however when you need
something that `EasyFormsState` doesn't provide then you could simply create your own. Please
follow [this link](https://github.com/k0shk0sh/ComposeEasyForms/blob/main/app/src/main/java/com/github/k0shk0sh/compose/easyforms/example/ui/components/Dropdown.kt#L22)
for more details.

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

> For more example please refer to the example app.

## Download

```kotlin
repositories {
  mavenCentral()
}

dependencies {
  implementation("com.github.k0shk0sh:compose-easyforms:<version>")
}
```

## Contributions

Please contribute! We will gladly review any pull requests. Make sure to read
the [Contributing](.github/CONTRIBUTING.md) page first though.
