import com.example.campsnatch.model.CampgroundAvailabilitiesResponse
import com.example.campsnatch.model.CampgroundAvailabilityRequest
import com.example.campsnatch.model.CampgroundResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CampgroundService {
    @GET("/campgrounds")
    fun getCampgrounds(): Call<CampgroundResponse>

    @POST("/get_campground_availabilities")
    fun getCampgroundAvailabilities(@Body request: CampgroundAvailabilityRequest): Call<CampgroundAvailabilitiesResponse>
}
