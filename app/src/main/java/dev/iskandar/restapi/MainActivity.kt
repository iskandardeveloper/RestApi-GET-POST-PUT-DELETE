package dev.iskandar.restapi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dev.iskandar.restapi.adapters.RvAdapter
import dev.iskandar.restapi.databinding.ActivityMainBinding
import dev.iskandar.restapi.models.GetTodoResponse
import dev.iskandar.restapi.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = Color.parseColor("#008DFF")
        onResume()
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        ApiClient.getApiService().getAllToDo()
            .enqueue(object : Callback<List<GetTodoResponse>> {
                override fun onResponse(
                    p0: Call<List<GetTodoResponse>>,
                    p1: Response<List<GetTodoResponse>>
                ) {
                    if (p1.isSuccessful) {
                        rvAdapter = RvAdapter(p1.body() as ArrayList, this@MainActivity)
                        binding.recyclerView.adapter = rvAdapter
                    }
                }

                override fun onFailure(p0: Call<List<GetTodoResponse>>, p1: Throwable) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun moreClick(getToDoResponse: GetTodoResponse, position: Int, image: ImageView) {
        val menu = PopupMenu(this, image)
        menu.inflate(R.menu.my_menu)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_delete -> {
                    ApiClient.getApiService().deleteTodo(getToDoResponse.id)
                        .enqueue(object : Callback<Any> {
                            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                                if (p1.isSuccessful) {
                                    Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT)
                                        .show()
                                    onResume()
                                }
                            }

                            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }

                R.id.menu_edit -> {
                    val intent = Intent(this, AddActivity::class.java)
                    intent.putExtra("keyTodo", getToDoResponse)
                    startActivity(intent)
                }
            }
            true
        }
        menu.show()
    }
}