package io.github.hristogochev.template.presentation.auth.login_first

import io.github.hristogochev.template.domain.error.PhoneNumberError
import io.github.hristogochev.template.domain.entity.request.RegisteredRequest
import io.github.hristogochev.template.domain.repository.RemoteRepository
import io.github.hristogochev.template.domain.validator.validatePhoneNumber
import io.github.hristogochev.template.presentation.auth.login_second.LoginSecondState
import io.github.hristogochev.template.presentation.auth.register_first.RegisterFirstState
import io.github.hristogochev.template.util.ApiResponse
import io.github.hristogochev.template.util.getOrElse
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateEffect
import io.github.hristogochev.template.util.updateNavEffect
import kotlinx.coroutines.flow.update

class LoginFirstViewModel(
    private val remoteRepository: RemoteRepository,
    initialState: LoginFirstState,
) : ViewModel<LoginFirstState, LoginFirstEvent, LoginFirstEffect, LoginFirstNavEvent, LoginFirstNavEffect>(
    initialState, initialEffect = LoginFirstEffect.FocusTextField
) {
    override fun onEvent(event: LoginFirstEvent) {
        when (event) {
            is LoginFirstEvent.UpdatePhoneNumber -> updatePhoneNumber(event.value)
        }
    }

    override suspend fun onNavEventProtected(event: LoginFirstNavEvent) {
        when (event) {
            is LoginFirstNavEvent.Continue -> onContinue(event.phoneNumber)
            is LoginFirstNavEvent.Register -> onRegister(event.phoneNumber)
        }
    }

    private suspend fun onContinue(phoneNumber: String) {
        val phoneNumberError = phoneNumber.validatePhoneNumber()

        if (phoneNumberError != null) {
            _state.update { it.copy(phoneNumberError = phoneNumberError) }
            return
        }

        val request = RegisteredRequest(phoneNumber = phoneNumber)

        val response = remoteRepository.sendRegisteredRequest(request).getOrElse { error ->
            val effect = when (error) {
                is ApiResponse.Error.HttpError -> {
                    if (error.code == 429) LoginFirstEffect.TooManyRequests
                    else LoginFirstEffect.Failure.Failed
                }

                is ApiResponse.Error.NetworkError -> LoginFirstEffect.Failure.ConnectionError
                is ApiResponse.Error.SerializationError -> LoginFirstEffect.Failure.Failed
            }

            _effect.updateEffect(effect)
            return
        }

        if (!response.registered) {
            _state.update { it.copy(phoneNumberError = PhoneNumberError.DoesNotExist) }
            return
        }

        val initialState = LoginSecondState(phoneNumber)

        _navEffect.updateNavEffect(LoginFirstNavEffect.Continue(initialState))
    }

    private fun onRegister(phoneNumber: String) {
        val initialState = RegisterFirstState(phoneNumber)
        _navEffect.updateNavEffect(LoginFirstNavEffect.Register(initialState))
    }

    private fun updatePhoneNumber(value: String) {
        val phoneNumber = value
        val error = phoneNumber.validatePhoneNumber()
        _state.update {
            it.copy(
                phoneNumber = phoneNumber,
                phoneNumberError = if (it.phoneNumberError != null) error else null,
            )
        }
    }
}