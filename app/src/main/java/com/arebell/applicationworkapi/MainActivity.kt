package com.arebell.applicationworkapi

import android.app.assist.AssistContent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arebell.applicationworkapi.api.MyRetrofit
import com.arebell.applicationworkapi.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recycleProducts: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleProducts = findViewById<RecyclerView>(R.id.recyclerProducts)
        recycleProducts.layoutManager = LinearLayoutManager(this)
        getData()
    }

    private fun getData() {
        val call: Call<List<Product>> =
            MyRetrofit.instance?.ProductApi()?.getProductApi() as Call<List<Product>>
        call.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: retrofit2.Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: retrofit2.Call<List<Product>>,
                response: Response<List<Product>>)
            {
                val adapter = ProductAdapter(this@MainActivity, response.body()!!.toList() )
                recycleProducts.adapter = adapter
            }

        })
    }
}