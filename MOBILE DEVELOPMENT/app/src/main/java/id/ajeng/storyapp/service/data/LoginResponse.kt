package id.ajeng.storyapp.service.data

data class LoginResponse (
    val error: Boolean? = null,
    val message: String = "",
    val loginResult: LoginResult? = null
)