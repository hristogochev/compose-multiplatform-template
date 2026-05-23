package io.github.hristogochev.template.presentation.auth.login_second

import io.github.hristogochev.template.domain.error.CodeError
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class LoginSecondState(
    val phoneNumber: String,
    val code: String ="",
    val codeError: CodeError? = null,
    val sendAgainTimeout: Int = 0,
) : Parcelable
