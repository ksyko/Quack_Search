package ksyko.quax

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ksyko.quax.service.RetrofitFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URLDecoder

object Banger {
    fun bang(context: Context, query: String, notify: Boolean = false) {
        val decodedQuery = URLDecoder.decode(query, "UTF-8")
        val client = RetrofitFactory.makeOkHttpClient()
        val request: Request = Request.Builder()
            .url("https://api.duckduckgo.com/?q=$query&no_redirect=0&t=ksyko.quax")
            .build()
        val notificationManager = NotificationManager(context)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (notify)
                        notificationManager.makeNotification(
                            121
                        )
                }
            }

            override fun onResponse(call: Call, response: Response) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (response.isSuccessful) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(response.request.url.toString())
                        ContextCompat.startActivity(context, intent, null)
                        if (notify)
                            notificationManager.makeNotification(121)
                    }
                }
            }

        })

    }
}