
# Compose EasyForms
Focus on building your form UI while the library do the heavy work for you.

### Out of the box EasyForms support below Compose widgets:
- TextField
- Checkbox
- TriStateCheckbox
- RadioButton
- Switch
- Slider
- RangeSlider
- Your own custom widget state handler

### Out of the box EasyForms provide TextField validation:
- Email validation
- Password validation
- Phone validation
- URL validation
- Name validation
- Cards validation
- Your own custom validator

### How to use:

Define `val easyForms = EasyForms()` in your ViewModel or in your Activity/Fragment upper in UI tree to prevent reinitializing the object.

> You should have always one instance of `EasyForms` per Screen.

**TextField example with Email validation:**

```kotlin
@Composable
fun EmailTextField(easyForms: EasyForms) {
    val emailTextFieldState = easyForms.getTextFieldState(MyFormKeys.EMAIL, EmailValidationType)
    val emailState = emailTextFieldState.rememberSaveable()
    TextField(
        value = emailState.value,
        onValueChange = emailTextFieldState.onValueChangedCallback,
        isError = emailTextFieldState.errorState.value == EasyFormsErrorState.INVALID,
    )
}
```

**Checkbox example**
```kotlin
@Composable  
fun CheckboxLayout(easyForms: EasyForms) {
    val checkboxState = easyForms.getCheckboxState(
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
**CTA state handling example**

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

> For more examples with all widgets and how to create your own custom states handler please refer to the project example.


## Contributions
Please contribute! We will gladly review any pull requests.
Make sure to read the [Contributing](.github/CONTRIBUTING.md) page first though.
