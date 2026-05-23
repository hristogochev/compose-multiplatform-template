package io.github.hristogochev.template.domain.repository


import io.github.hristogochev.template.domain.entity.auth.UserDetails
import io.github.hristogochev.template.domain.entity.dto.City
import io.github.hristogochev.template.domain.entity.dto.Version
import io.github.hristogochev.template.domain.entity.request.CreateDeviceTokenRequest
import io.github.hristogochev.template.domain.entity.request.LoginSMSRequest
import io.github.hristogochev.template.domain.entity.request.LoginValidateRequest
import io.github.hristogochev.template.domain.entity.request.RegisterSMSRequest
import io.github.hristogochev.template.domain.entity.request.RegisterValidateRequest
import io.github.hristogochev.template.domain.entity.request.RegisteredRequest
import io.github.hristogochev.template.domain.entity.request.UpdateFullNameRequest
import io.github.hristogochev.template.domain.entity.request.UpdateNotificationsEnabledRequest
import io.github.hristogochev.template.domain.entity.response.RegisteredResponse
import io.github.hristogochev.template.domain.entity.response.SmsSuccessResponse
import io.github.hristogochev.template.domain.entity.response.SuccessResponse
import io.github.hristogochev.template.domain.entity.response.TokenResponse
import io.github.hristogochev.template.util.ApiResponse


interface RemoteRepository {
    suspend fun getVersion(): ApiResponse<Version>

    suspend fun sendRegisteredRequest(registeredRequest: RegisteredRequest): ApiResponse<RegisteredResponse>
    suspend fun sendRegisterSmsRequest(registerSMSRequest: RegisterSMSRequest): ApiResponse<SmsSuccessResponse>
    suspend fun sendRegisterValidateRequest(registerValidateRequest: RegisterValidateRequest): ApiResponse<TokenResponse>
    suspend fun sendLoginSmsRequest(loginSMSRequest: LoginSMSRequest): ApiResponse<SmsSuccessResponse>
    suspend fun sendLoginValidateRequest(loginValidateRequest: LoginValidateRequest): ApiResponse<TokenResponse>
    suspend fun sendLogoutRequest(token: String): ApiResponse<SuccessResponse>


    suspend fun getUserDetails(token: String): ApiResponse<UserDetails>

    suspend fun patchUserFullName(
        token: String,
        request: UpdateFullNameRequest,
    ): ApiResponse<SuccessResponse>

    suspend fun patchUserNotificationsEnabled(
        token: String,
        request: UpdateNotificationsEnabledRequest,
    ): ApiResponse<SuccessResponse>

    suspend fun getStatus(token: String): ApiResponse<SuccessResponse>

    suspend fun createDeviceToken(
        token: String,
        request: CreateDeviceTokenRequest,
    ): ApiResponse<SuccessResponse>

    suspend fun getCities(): ApiResponse<List<City>>
}