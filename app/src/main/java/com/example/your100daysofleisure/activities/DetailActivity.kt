package com.example.your100daysofleisure.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.your100daysofleisure.R
import com.example.your100daysofleisure.adapters.Your100DaysOfLeisureAdapter
import com.example.your100daysofleisure.data.Leisure
import com.example.your100daysofleisure.data.Your100DaysOfLeisureApiService
import com.example.your100daysofleisure.databinding.ActivityDetailBinding
import com.example.your100daysofleisure.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding


    lateinit var adapter: Your100DaysOfLeisureAdapter

    var isFavorite = false
    lateinit var leisure: Leisure

    lateinit var favoriteMenuItem: MenuItem

    lateinit var session: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(this)

        val url = intent.getStringExtra("LEISURE_ID")!!
        val title = intent.getStringExtra("LEISURE_TITLE")

        getLeisureData(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)
        favoriteMenuItem = menu.findItem(R.id.menu_favorite)
        setFavoriteIcon()
        return true
    }


    fun setFavoriteIcon () {
        if (isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_selected)
        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_favorite -> {
                if (isFavorite) {
                    session.setFavoriteLeisure("")
                } else {
                    session.setFavoriteLeisure(leisure.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
                true
            }
            R.id.menu_share -> {
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, leisure.link)
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadData() {
        binding.titleEventTextView.text = leisure.title
        binding.priceTextView.text = if (leisure.isFree) getString(R.string.free) else leisure.price
        binding.eventLocationTextView.text = leisure.place
        binding.localityTextView.text = leisure.address?.locality()
        binding.eventZipCodeTextView.text = leisure.address?.postalCode()
        binding.addressTextView.text = leisure.address?.streetAddress()
        binding.accesibilityTextView.text = leisure.organization?.access.toString()
        //binding.progressBar

        binding.visitWebPageButton.setOnClickListener {
            visitWebPage()
        }

        isFavorite = session.isFavorite(leisure.id)
        setFavoriteIcon()

    }

    fun visitWebPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(leisure.link))
        startActivity(browserIntent)
    }

    private fun getLeisureData(url: String) {
        if (title!=null) {
            binding.progressBarDetail.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    val apiService =
                        getRetrofit(url).create(Your100DaysOfLeisureApiService::class.java)
                    val result = apiService.findLeisureById(url)

                    leisure = result.results.first()

                    runOnUiThread {
                        loadData()
                        binding.progressBarDetail.visibility = View.GONE
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