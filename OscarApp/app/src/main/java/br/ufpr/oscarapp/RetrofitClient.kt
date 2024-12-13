import br.ufpr.oscarapp.PostApi
import br.ufpr.oscarapp.TelaIP
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Função para obter o IP do servidor
    private fun getBaseUrl(): String {
        return "http://${TelaIP.IpServidor}:5000/"
    }

    val instance: PostApi by lazy {
        Retrofit.Builder()
            .baseUrl(getBaseUrl())  // Usando o IP informado
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }
}
