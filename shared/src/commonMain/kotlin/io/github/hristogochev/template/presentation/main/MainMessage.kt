package io.github.hristogochev.template.presentation.main

sealed interface MainMessage {


    data object RequestLogout : MainMessage
}