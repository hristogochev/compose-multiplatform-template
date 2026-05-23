package io.github.hristogochev.template.presentation.auth.register_first

import io.github.hristogochev.template.domain.error.PhoneNumberError
import io.github.hristogochev.template.domain.entity.request.RegisteredRequest
import io.github.hristogochev.template.domain.repository.RemoteRepository
import io.github.hristogochev.template.domain.validator.validatePhoneNumber
import kotlinx.coroutines.flow.update
import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstState
import io.github.hristogochev.template.presentation.auth.register_second.RegisterSecondState
import io.github.hristogochev.template.util.ApiResponse
import io.github.hristogochev.template.util.getOrElse
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateEffect
import io.github.hristogochev.template.util.updateNavEffect

class RegisterFirstViewModel(
    private val remoteRepository: RemoteRepository,
    initialState: RegisterFirstState,
) : ViewModel<RegisterFirstState, RegisterFirstEvent, RegisterFirstEffect, RegisterFirstNavEvent, RegisterFirstNavEffect>(
    initialState, initialEffect = RegisterFirstEffect.FocusTextField
) {

    override fun onEvent(event: RegisterFirstEvent) {
        when (event) {
            is RegisterFirstEvent.UpdatePhoneNumber -> {
                updatePhoneNumber(event.value)
            }
        }
    }

    override suspend fun onNavEventProtected(event: RegisterFirstNavEvent) {
        when (event) {
            is RegisterFirstNavEvent.Continue -> onContinue(event.phoneNumber)
            is RegisterFirstNavEvent.Login -> onLogin(event.phoneNumber)
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
                    if (error.code == 429) RegisterFirstEffect.TooManyRequests
                    else RegisterFirstEffect.Failure.Failed
                }

                is ApiResponse.Error.NetworkError -> RegisterFirstEffect.Failure.ConnectionError
                is ApiResponse.Error.SerializationError -> RegisterFirstEffect.Failure.Failed
            }

            _effect.updateEffect(effect)
            return
        }

        if (response.registered) {
            _state.update { it.copy(phoneNumberError = PhoneNumberError.AlreadyTaken) }
            return
        }

        val initialState = RegisterSecondState(phoneNumber)

        _navEffect.updateNavEffect(RegisterFirstNavEffect.Continue(initialState))
    }

    private fun onLogin(phoneNumber: String) {
        val initialState = LoginFirstState(phoneNumber)
        _navEffect.updateNavEffect(RegisterFirstNavEffect.Login(initialState))
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