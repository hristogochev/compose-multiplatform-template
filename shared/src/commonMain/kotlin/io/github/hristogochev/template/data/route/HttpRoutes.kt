package io.github.hristogochev.template.data.route

const val BASE_URL: String = "https://example.org/api/"

object HttpRoutes {
    const val VERSION = "version"
    const val GET_REGISTERED = "users/registered"
    const val POST_REGISTER_SMS = "users/register/sms"
    const val POST_REGISTER_VERIFY = "users/register/verify"
    const val POST_LOGIN_SMS = "users/login/sms"
    const val POST_LOGIN_VERIFY = "users/login/verify"
    const val POST_LOGOUT = "users/me/logout"
    const val PATCH_FULL_NAME = "users/me/full_name"
    const val PATCH_NOTIFICATIONS_ENABLED = "users/me/notifications_enabled"
    const val GET_USER_DETAILS = "users/me"
    const val GET_STATUS = "users/status"
    const val CREATE_DEVICE_TOKEN = "device_tokens"
    const val GET_ALL_CITIES = "cities"
}