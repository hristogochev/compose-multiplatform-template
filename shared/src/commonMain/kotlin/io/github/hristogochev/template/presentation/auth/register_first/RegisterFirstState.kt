package io.github.hristogochev.template.presentation.auth.register_first

import io.github.hristogochev.template.domain.error.PhoneNumberError
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize


@Parcelize
data class RegisterFirstState(
    val phoneNumber: String = (""),
    val phoneNumberError: PhoneNumberError? = null,
) : Parcelable
