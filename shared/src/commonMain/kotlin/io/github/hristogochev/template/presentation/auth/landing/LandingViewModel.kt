package io.github.hristogochev.template.presentation.auth.landing

import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstState
import io.github.hristogochev.template.presentation.auth.register_first.RegisterFirstState
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateNavEffect

class LandingViewModel(initialState: LandingState) :
    ViewModel<LandingState, Nothing, Nothing, LandingNavEvent, LandingNavEffect>(initialState) {

    override suspend fun onNavEventProtected(event: LandingNavEvent) {
        when (event) {
            LandingNavEvent.Register -> onRegister()
            LandingNavEvent.Login -> onLogin()
        }
    }

    private fun onRegister() {
        val initialState = RegisterFirstState()
        _navEffect.updateNavEffect(LandingNavEffect.Register(initialState))
    }

    private fun onLogin() {
        val initialState = LoginFirstState()
        _navEffect.updateNavEffect(LandingNavEffect.Login(initialState))
    }
}