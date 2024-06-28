package dev.iskandar.restapi

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dev.iskandar.restapi.databinding.ActivityAddBinding
import dev.iskandar.restapi.models.GetTodoResponse
import dev.iskandar.restapi.models.PostRequestTodo
import dev.iskandar.restapi.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private lateinit var getTodoResponse: GetTodoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = Color.parseColor("#008DFF")

        try {
            getTodoResponse = intent.getSerializableExtra("keyTodo") as GetTodoResponse
            editTodo()
        } catch (e: Exception) {
            postTodo()
        }

    }

    private fun postTodo() {
        binding.apply {
            buttonSave.setOnClickListener {
                val postRequestTodo = PostRequestTodo(
                    false,
                    editTextBatafsil.text.toString(),
                    editTextOxirgiMuddat.text.toString(),
                    editTextSarlavha.text.toString(),
                    editTextZarurlik.text.toString()
                )
                ApiClient.getApiService().addToDo(postRequestTodo)
                    .enqueue(object : Callback<GetTodoResponse> {
                        override fun onResponse(
                            p0: Call<GetTodoResponse>,
                            p1: Response<GetTodoResponse>
                        ) {
                            if (p1.isSuccessful) {
                                Toast.makeText(this@AddActivity, "Saved", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@AddActivity, p1.message(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onFailure(p0: Call<GetTodoResponse>, p1: Throwable) {
                            Toast.makeText(this@AddActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

    private fun editTodo() {
        binding.apply {
            editTextSarlavha.setText(getTodoResponse.sarlavha)
            editTextBatafsil.setText(getTodoResponse.batafsil)
            editTextOxirgiMuddat.setText(getTodoResponse.oxirgi_muddat)
            editTextZarurlik.setText(getTodoResponse.zarurlik)
            mySwitch.visibility = View.VISIBLE
            mySwitch.isChecked = getTodoResponse.bajarildi
            buttonSave.setOnClickListener {
                ApiClient.getApiService().updateTodo(
                    getTodoResponse.id,
                    PostRequestTodo(
                        mySwitch.isChecked,
                        editTextBatafsil.text.toString(),
                        editTextOxirgiMuddat.text.toString(),
                        editTextSarlavha.text.toString(),
                        editTextZarurlik.text.toString()
                    )
                ).enqueue(object : Callback<GetTodoResponse> {
                    override fun onResponse(
                        p0: Call<GetTodoResponse>,
                        p1: Response<GetTodoResponse>
                    ) {
                        if (p1.isSuccessful) {
                            Toast.makeText(this@AddActivity, "Edited", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@AddActivity, p1.message(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(p0: Call<GetTodoResponse>, p1: Throwable) {
                        Toast.makeText(this@AddActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}