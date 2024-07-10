package com.example.your100daysofleisure.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.your100daysofleisure.adapters.Your100DaysOfLeisureAdapter
import com.example.your100daysofleisure.data.Leisure
import com.example.your100daysofleisure.data.Your100DaysOfLeisureApiService
import com.example.your100daysofleisure.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    lateinit var leisure: Leisure

    lateinit var adapter: Your100DaysOfLeisureAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("LEISURE_ID")!!
        val title = intent.getStringExtra("LEISURE_TITLE")

        getLeisureData(url)
    }


    private fun loadData() {
        binding.titleEventTextView.text = leisure.title
        binding.priceTextView.text = if (leisure.isFree) "Gratis" else leisure.price
        binding.eventLocationTextView.text = leisure.place
        binding.localityTextView.text = leisure.address?.locality()
        binding.eventZipCodeTextView.text = leisure.address?.postalCode()
        binding.addressTextView.text = leisure.address?.streetAddress()
        binding.accesibilityTextView.text = leisure.organization?.access.toString()
        //binding.progressBar

        binding.visitWebPageButton.setOnClickListener {
            visitWebPage()
        }



    }

    fun visitWebPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(leisure.link))
        startActivity(browserIntent)
    }

    private fun getLeisureData(url: String) {
        if (title!=null) {
            binding.progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    val apiService =
                        getRetrofit(url).create(Your100DaysOfLeisureApiService::class.java)
                    val result = apiService.findLeisureById(url)

                    leisure = result.results.first()

                    runOnUiThread {
                        loadData()
                        binding.progressBar.visibility = View.GONE
                    }
                    //Log.i("HTTP", "${result.results}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}