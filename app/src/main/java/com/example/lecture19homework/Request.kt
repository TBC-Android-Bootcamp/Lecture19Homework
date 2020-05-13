package com.example.lecture19homework

import android.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

object Request {
    const val LOGIN = "login"
    const val REGISTRATION = "register"

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private var service = retrofit.create(ApiService::class.java)

    interface ApiService {
        @GET("{path}")
        fun getRequest(@Path("path") path: String): Call<String>

        @POST("{path}")
        @FormUrlEncoded
        fun postRequest(
            @Path("path") path: String,
            @FieldMap parameters: Map<String, String>
        ): Call<String>
    }

    fun getRequest(path: String, callback: CustomCallback) {
        val call = service.getRequest(path)
        call.enqueue(onCallback(callback))
    }

    fun postRequest(
        path: String,
        parameters: MutableMap<String, String>,
        callback: CustomCallback
    ) {
        val call = service.postRequest(path, parameters)
        call.enqueue((onCallback(callback)))

    }

    private fun onCallback(callback: CustomCallback) = object : Callback<String> {
        override fun onFailure(call: Call<String>, t: Throwable) {
            Log.d("onFailure", "${t.message}")
            callback.onFailure(t.message.toString())
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                callback.onSuccess(response.body().toString(), "${response.body()}")
                Log.d("onResponse", "${response.body()}")
            } else {
                val json = JSONObject(response.errorBody()!!.string())
                if (json.has("error")) {
                    Log.d("error", json.getString("error"))
                }
                callback.onError(response.body().toString(), json.getString("error"))

            }

        }

    }
}