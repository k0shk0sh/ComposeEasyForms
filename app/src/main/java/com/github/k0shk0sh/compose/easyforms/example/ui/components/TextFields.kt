package com.github.k0shk0sh.compose.easyforms.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyFormKeys
import com.github.k0shk0sh.compose.easyforms.*

@Composable
fun NameTextField(easyForm: EasyForms) {
    val nameTextFieldState = easyForm.getTextFieldState(MyFormKeys.NAME, NameValidationType)
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
fun EmailTextField(easyForm: EasyForms) {
    val emailTextFieldState = easyForm.getTextFieldState(MyFormKeys.EMAIL, EmailValidationType)
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
fun PasswordTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState(MyFormKeys.PASSWORD, PasswordValidationType)
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
fun UrlTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState(MyFormKeys.URL, UrlValidationType)
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
fun PhoneTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState(MyFormKeys.PHONE, PhoneNumberValidationType)
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
fun CardTextField(easyForm: EasyForms) {
    val textFieldState = easyForm.getTextFieldState(MyFormKeys.CARD, CardValidationType)
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