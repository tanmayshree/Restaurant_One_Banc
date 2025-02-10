package com.tanmay.restaurant_one_banc.Room

import androidx.room.TypeConverter
import com.tanmay.restaurant_one_banc.Screens.MainSection.Models.MakePaymentRequestBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromMakePaymentRequestBody(value: MakePaymentRequestBody): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toMakePaymentRequestBody(value: String): MakePaymentRequestBody {
        return Json.decodeFromString(value)
    }
}
