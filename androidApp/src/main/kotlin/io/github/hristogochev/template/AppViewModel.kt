package io.github.hristogochev.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.hristogochev.template.domain.repository.UserRepository
import io.github.hristogochev.template.presentation.app.AppPrepared
import io.github.hristogochev.template.presentation.app.prepare
import io.github.hristogochev.template.util.notification.NotificationDataOptional
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val userRepository: UserRepository,
    notificationDataOptional: NotificationDataOptional,
) : ViewModel() {
    private val _state = MutableStateFlow<AppPrepared>(AppPrepared.Preparing)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val prepared = prepare(userRepository, notificationDataOptional.data)

            _state.update { prepared }
        }
    }
}