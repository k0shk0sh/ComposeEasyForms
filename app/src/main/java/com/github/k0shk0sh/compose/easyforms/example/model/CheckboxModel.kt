package com.github.k0shk0sh.compose.easyforms.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckboxModel(
    val id: String,
    val isSelected: Boolean,
) : Parcelable

fun listOfCheckboxes(): List<CheckboxModel> = listOf(
    CheckboxModel("1", false),
    CheckboxModel("2", false),
    CheckboxModel("3", true),
    CheckboxModel("4", false),
    CheckboxModel("5", false),
)