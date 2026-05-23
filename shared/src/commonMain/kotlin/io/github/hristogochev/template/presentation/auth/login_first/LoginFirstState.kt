package io.github.hristogochev.template.presentation.auth.login_first

import io.github.hristogochev.template.domain.error.PhoneNumberError
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class LoginFirstState(
    val phoneNumber: String = "",
    val phoneNumberError: PhoneNumberError? = null,
) : Parcelable
