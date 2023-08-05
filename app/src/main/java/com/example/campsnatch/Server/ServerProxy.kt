import com.example.campsnatch.model.CampgroundResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerProxy {

    private val baseUrl = "https://api.campsnatch.com"

    private val service: CampgroundService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CampgroundService::class.java)
    }

    fun getCampgrounds(callback: (CampgroundResponse?) -> Unit) {
        val call = service.getCampgrounds()
        call.enqueue(object : retrofit2.Callback<CampgroundResponse> {
            override fun onResponse(
                call: retrofit2.Call<CampgroundResponse>,
                response: retrofit2.Response<CampgroundResponse>
            ) {
                callback(response.body())
            }

            override fun onFailure(call: retrofit2.Call<CampgroundResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    // Add more methods for other endpoints here
}