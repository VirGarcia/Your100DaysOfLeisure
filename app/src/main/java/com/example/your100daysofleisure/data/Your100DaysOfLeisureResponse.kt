package com.example.your100daysofleisure.data

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class Your100DaysOfLeisureResponse (
    @SerializedName("@graph") val results: List<Leisure>
){
}

data class Leisure (
    @SerializedName("@id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: Address?


)

data class Address (
    @SerializedName("area") val area: Area
)

data class Area (
    @SerializedName("locality") val locality: String,
    @SerializedName("postal-code") val postalCode: String,
    @SerializedName("street-address") val street: String
)

data class Details(
    @SerializedName("title") val name: String,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("price") val intelligence: Int,
    @JsonAdapter(IntegerAdapter::class)@SerializedName("event-location") val strength: String

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