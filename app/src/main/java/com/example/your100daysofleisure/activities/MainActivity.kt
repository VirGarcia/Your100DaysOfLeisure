package com.example.your100daysofleisure.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.your100daysofleisure.adapters.Your100DaysOfLeisureAdapter
import com.example.your100daysofleisure.data.Leisure
import com.example.your100daysofleisure.data.Your100DaysOfLeisureApiService
import com.example.your100daysofleisure.databinding.ActivityMainBinding
import com.example.your100daysofleisure.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: Your100DaysOfLeisureAdapter

    lateinit var your100DaysOfLeisureList: List<Leisure>

    //lateinit var recyclerView: RecyclerView

    //lateinit var identifierView: LinearLayout
    //lateinit var userNameText: EditText
    //lateinit var userZipCodeText: EditText
    //lateinit var addButton: Button
    lateinit var session: SessionManager
    //lateinit var name: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        if (session.getUserName() == null) {
            binding.identifierView.visibility = View.VISIBLE
            binding.addButton.setOnClickListener{
                session.setUserName(binding.userNameText.text.toString())
                session.setUserZipCode(binding.userZipCodeText.text.toString())
                binding.identifierView.visibility = View.GONE
                showUserData()
            }
        } else {
            showUserData()
        }

        your100DaysOfLeisureList = emptyList()
        adapter = Your100DaysOfLeisureAdapter(your100DaysOfLeisureList) { position ->
            navigateToDetail(your100DaysOfLeisureList[position])
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

     searchAllLeisure("CENTRO")

    }

    fun showUserData() {
        binding.helloUser.visibility = View.VISIBLE
        binding.name.text = session.getUserName()
    }

    private fun navigateToDetail(leisure: Leisure) {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("LEISURE_ID", leisure.id)
        intent.putExtra("LEISURE_TITLE",  leisure.title.replace('.','-').replace(' ','-'))

        startActivity(intent)
    }

    private fun  searchAllLeisure(query: String){
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(Your100DaysOfLeisureApiService::class.java)
                val result = apiService.findAllLeisures()
                //val result = apiService.findLeisureByDistrict("CENTRO")

                runOnUiThread {
                    your100DaysOfLeisureList = result.results
                    adapter.updateData(your100DaysOfLeisureList)
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        /*val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/
        return Retrofit.Builder()
            //en postman para probar esta url, despues de la barra hay que meterle el método, y eso y viene en la api
            //en este ejemplo sería: https://superheroapi.com/api/7252591128153666/search/batman
            //ponemos search/el nombre de quién queremos buscar

            .baseUrl("https://datos.madrid.es/egob/catalogo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}