import com.example.campsnatch.model.CampgroundResponse
import retrofit2.Call
import retrofit2.http.GET

interface CampgroundService {
    @GET("/campgrounds")
    fun getCampgrounds(): Call<CampgroundResponse>
}
