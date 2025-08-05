package com.example.cft_test_task.user_info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cft_test_task.databinding.ActivityUserInfoBinding
import com.example.cft_test_task.model.User
import java.net.URLEncoder

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var presenter: UserInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        presenter = UserInfoPresenter()

        val userData = intent.getParcelableExtra<User>("user")
        if (userData == null) {
            finish()
            return
        }

        with(binding){
            Glide.with(imageView.context)
                .load(userData.picture.medium)
                .into(binding.imageView)

            nameTextView.text = "${userData.name.title} ${userData.name.first} ${userData.name.last}"
            addressTextView.text = listOf(
                userData.location.street.number,
                userData.location.street.name,
                userData.location.city,
                userData.location.state,
                userData.location.country,
                userData.location.postcode,
                userData.location.coordinates.latitude,
                userData.location.coordinates.longitude,
                userData.location.timezone.offset,
                userData.location.timezone.description
            ).joinToString(separator = ", ")
            phoneNumberTextView.text = userData.phone
            emailTextView.text = userData.email
            dobTextView.text = "${presenter.formatDate(userData.dob.date)} (age ${userData.dob.age})"
            idTextView.text = "${userData.id.name}: ${userData.id.value.orEmpty()}"

            emailTextView.apply {
                isClickable = true
                setOnClickListener {
                    val uri = Uri.parse("mailto:${userData.email}")
                    val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
                    startActivity(emailIntent)
                }
            }

            phoneNumberTextView.apply {
                isClickable = true
                setOnClickListener {
                    val uri = Uri.parse("tel:${userData.phone}")
                    val dialIntent = Intent(Intent.ACTION_DIAL, uri)
                    startActivity(dialIntent)
                }
            }

            val address = listOf(
                "${userData.location.street.number} ${userData.location.street.name}",
                userData.location.city,
                userData.location.state,
                userData.location.country,
                userData.location.postcode
            ).joinToString(", ")
            addressTextView.apply {
                isClickable = true
                setOnClickListener {
                    val lat = userData.location.coordinates.latitude
                    val lng = userData.location.coordinates.longitude
                    val geoUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng(${URLEncoder.encode(address, "UTF-8")})")
                    val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
                    startActivity(mapIntent)
                }
            }
        }
    }
}