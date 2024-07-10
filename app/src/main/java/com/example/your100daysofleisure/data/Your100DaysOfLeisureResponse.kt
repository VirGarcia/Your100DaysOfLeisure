package com.example.your100daysofleisure.data

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Your100DaysOfLeisureResponse (
    @SerializedName("@graph") val results: List<Leisure>
){
}

data class Leisure (
    @SerializedName("@id") val id: String,
    @SerializedName("id") val idNum: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("event-location") val place: String,
    @SerializedName("link") val link: String,
    @JsonAdapter(BooleanAdapter::class) @SerializedName("free") val isFree: Boolean,
    @SerializedName("price") val price: String,
    @JsonAdapter(DateAdapter::class) @SerializedName("dtstart") val beginDate: LocalDate,
    @JsonAdapter(DateAdapter::class) @SerializedName("dtend") val endDate: LocalDate,
    @SerializedName("address") val address: Address?,
    @SerializedName("organization") val organization: Organization?


)

data class Address (
    @SerializedName("area") val area: Area?,
    @SerializedName("locality") val locality: String?,
    @SerializedName("postal-code") val postalCode: String?,
    @SerializedName("street-address") val streetAddress: String?
) {
    fun locality() : String? {
        return locality ?: area?.locality
    }
    fun postalCode() : String? {
        return postalCode ?: area?.postalCode
    }
    fun streetAddress() : String? {
        return streetAddress ?: area?.streetAddress
    }
}

data class Area (
    @SerializedName("locality") val locality: String?,
    @SerializedName("postal-code") val postalCode: String?,
    @SerializedName("street-address") val streetAddress: String?
)

data class Organization (
    @SerializedName("organization-name") val organizationName: String,
    @SerializedName("accesibility") val access: String
)



data class Details (
    @SerializedName("title") val name: String,
    @SerializedName("price") val intelligence: Int,
    @SerializedName("event-location") val strength: String


)

class IntegerAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter?, value: Int) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Int {
        if (`in` != null) {
            val value: String = `in`.nextString()
            if (value != "null") {
                return value.toInt()
            }
        }
        return 0
    }

}

class BooleanAdapter : TypeAdapter<Boolean>() {
    override fun write(out: JsonWriter?, value: Boolean) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Boolean {
        if (`in` != null) {
            val value = `in`.nextInt()
            return value == 1
        }
        return false
    }

}

class DateAdapter : TypeAdapter<LocalDate>() {
    override fun write(out: JsonWriter?, value: LocalDate) {
        out?.value(value.toString())
    }

    override fun read(`in`: JsonReader?): LocalDate {
        if (`in` != null) {
            val value = `in`.nextString()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0")
            return LocalDate.parse(value, formatter)
        }
        return LocalDate.now()
    }

}