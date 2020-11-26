package com.example.loginflow.util

import android.text.InputType
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.validate(): Boolean {
    val input = text.toString()
    when (inputType) {
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
            if (input.isEmpty()) {
                error = "Email can not be empty"
                return false
            }
        }

        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
            if (input.isEmpty()) {
                error = "Password can not be empty"
                return false
            }
        }
    }
    return true
}
