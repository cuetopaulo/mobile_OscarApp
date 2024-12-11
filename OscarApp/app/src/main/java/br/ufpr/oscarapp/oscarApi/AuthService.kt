import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class AuthRequest(val username: String, val password: String)
data class TokenResponse(val access: String, val refresh: String)

interface AuthService {
    @POST("token/")
    fun authenticate(@Body authRequest: AuthRequest): Call<TokenResponse>
}
