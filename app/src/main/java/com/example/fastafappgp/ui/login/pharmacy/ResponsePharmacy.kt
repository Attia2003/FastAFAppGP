import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponsePharmacyItem(

	@field:SerializedName("isBranch")
	val isBranch: Boolean? = null,
	@field:SerializedName("expiryThreshold")
	val expiryThreshold: Int? = null,
	@field:SerializedName("createdAt")
	val createdAt: String? = null,
	@field:SerializedName("address")
	val address: String? = null,
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("id")
	val id: Int? = null,
	@field:SerializedName("mainBranch")
	val mainBranch: String? = null,
	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
