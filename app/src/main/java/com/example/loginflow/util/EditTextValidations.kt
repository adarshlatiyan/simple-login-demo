package com.example.loginflow.util

import android.text.InputType
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.validate(): Boolean {
    val input = text.toString()
    when (inputType) {
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS -> {
            if (input.isEmpty()) {
//                setError("Email can not be empty", R.drawable.ic_warning)
                error = "Email can not be empty"
                return false
            }
        }

        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
            if (input.isEmpty()) {
//                setError("Password can not be empty", R.drawable.ic_warning)
                error = "Password can not be empty"
                return false
            }
        }
    }
    return true
}

private fun TextInputEditText.setError(error: String, id: Int) {
    setError(
        error,
        ResourcesCompat.getDrawable(resources, id, null)
    )
}
