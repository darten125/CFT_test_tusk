package com.example.cft_test_task.user_info

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class UserInfoPresenter {

    fun formatDate(isoDate: String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = inputFormat.parse(isoDate)
        return SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(date)
    }
}