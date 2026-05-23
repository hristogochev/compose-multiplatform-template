package io.github.hristogochev.template.presentation.auth.register_second

import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import io.github.hristogochev.template.domain.error.CodeError
import io.github.hristogochev.template.domain.entity.request.RegisterSMSRequest
import io.github.hristogochev.template.domain.entity.request.RegisterValidateRequest
import io.github.hristogochev.template.domain.repository.RemoteRepository
import io.github.hristogochev.template.domain.repository.UserRepository
import io.github.hristogochev.template.domain.validator.validateCode
import io.github.hristogochev.template.presentation.auth.register_success.RegisterSuccessState
import io.github.hristogochev.template.presentation.main.MainMessage
import io.github.hristogochev.template.presentation.main.MainMessageCommunicator
import io.github.hristogochev.template.presentation.main.MainNavEffect
import io.github.hristogochev.template.util.ApiResponse
import io.github.hristogochev.template.util.getOrElse
import io.github.hristogochev.template.util.ifError
import io.github.hristogochev.template.util.ifSuccess
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateEffect
import io.github.hristogochev.template.util.updateNavEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSecondViewModel(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserRepository,
    initialState: RegisterSecondState,
) : ViewModel<RegisterSecondState, RegisterSecondEvent, RegisterSecondEffect, RegisterSecondNavEvent, RegisterSecondNavEffect>(
    initialState, initialEffect = RegisterSecondEffect.FocusTextField
) {

    init {
        onSendSms(initialState.phoneNumber)
    }

    override fun onEvent(event: RegisterSecondEvent) {
        when (event) {
            is RegisterSecondEvent.SendAgain -> onSendSms(event.phoneNumber)

            is RegisterSecondEvent.UpdateCode -> updateCode(event.value)
        }
    }

    override suspend fun onNavEventProtected(event: RegisterSecondNavEvent) {
        when (event) {
            is RegisterSecondNavEvent.Continue -> {
                onContinue(
                    phoneNumber = event.phoneNumber,
                    code = event.code
                )
            }

            RegisterSecondNavEvent.Back -> {
                _navEffect.updateNavEffect(RegisterSecondNavEffect.Back)
            }
        }
    }

    override fun onNavEventCompleted(event: RegisterSecondNavEvent) {
        when (event) {
            is RegisterSecondNavEvent.Continue -> {
            }

            RegisterSecondNavEvent.Back -> {}
        }
    }

    private suspend fun onContinue(phoneNumber: String, code: String) {
        val codeError = code.validateCode()
        if (codeError != null) {
            _state.update { it.copy(codeError = codeError) }
            return
        }

        val request = RegisterValidateRequest(phoneNumber = phoneNumber, code = code)

        val response =
            remoteRepository.sendRegisterValidateRequest(request).getOrElse { error ->
                when (error) {
                    is ApiResponse.Error.HttpError -> {
                        when (error.code) {
                            401 -> _state.update { it.copy(codeError = CodeError.Invalid) }
                            429 -> _effect.updateEffect(RegisterSecondEffect.TooManyRequests)
                            else -> _effect.updateEffect(RegisterSecondEffect.Failure.Failed)
                        }
                    }

                    is ApiResponse.Error.NetworkError -> _effect.updateEffect(RegisterSecondEffect.Failure.ConnectionError)

                    is ApiResponse.Error.SerializationError -> _effect.updateEffect(
                        RegisterSecondEffect.Failure.Failed
                    )
                }

                return
            }

        val userCredentials = UserCredentials(
            response.userId,
            response.phoneNumber,
            response.token
        )

        userRepository.save(userCredentials)

        val initialState = RegisterSuccessState(userCredentials)

        _navEffect.updateNavEffect(
            RegisterSecondNavEffect.Continue(initialState)
        )
    }

    private fun onSendSms(phoneNumber: String) {
        if (state.value.sendAgainTimeout > 0) return
        _state.update { it.copy(sendAgainTimeout = 60) }
        viewModelScope.launch {
            val request = RegisterSMSRequest(phoneNumber = phoneNumber)

            remoteRepository.sendRegisterSmsRequest(request).ifError { error ->
                when (error) {
                    is ApiResponse.Error.NetworkError -> _effect.updateEffect(RegisterSecondEffect.Failure.ConnectionError)
                    is ApiResponse.Error.SerializationError -> _effect.updateEffect(
                        RegisterSecondEffect.Failure.Failed
                    )

                    is ApiResponse.Error.HttpError -> {
                        if (error.code == 429) {
                            val time = error.message?.toIntOrNull() ?: 60
                            if (time <= 60) {
                                _state.update { it.copy(sendAgainTimeout = time) }
                            } else {
                                _effect.updateEffect(RegisterSecondEffect.TryAgainLater)
                            }
                        } else _effect.updateEffect(RegisterSecondEffect.Failure.Failed)
                    }
                }
            }.ifSuccess {
                if (it.result.autoFill) {
                    val code = "0000"
                    _state.update { state -> state.copy(code = code) }

                    onNavEvent(RegisterSecondNavEvent.Continue(phoneNumber, code))
                }
            }

            while (state.value.sendAgainTimeout > 0) {
                delay(1000)
                _state.update { it.copy(sendAgainTimeout = it.sendAgainTimeout - 1) }
            }
        }
    }

    private fun updateCode(value: String) {
        val code = value
        val codeError = code.validateCode()
        val previousState = _state.getAndUpdate {
            it.copy(
                code = code,
                codeError = if (it.codeError != null) codeError else null,
            )
        }

        val previousCode = previousState.code
        val phoneNumber = previousState.phoneNumber

        if (previousCode.length == 3 && code.length == 4) {
            onNavEvent(RegisterSecondNavEvent.Continue(phoneNumber, code))
        }
    }
}