package ksyko.quax

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ksyko.quax.service.RetrofitFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException


class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val query = getMessageText(intent).toString()
        if (query.startsWith("!")) bang(context, query)
        else setDefinitionResult(context, query)
    }

    private fun setNotification(context: Context, request: String, body: String) {
        val repliedNotification =
            NotificationCompat.Builder(context, context.applicationInfo.name)
                .setContentTitle("Definition")
                .setContentTitle(request)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(body)
                )
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)
                .build()

        NotificationManagerCompat.from(context).apply {
            notify(121, repliedNotification)
        }

    }

    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence("QUACK")
    }

    private fun setDefinitionResult(context: Context, query: String) {
        val service = RetrofitFactory.makeDefinitionService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getDefinition(query, "en")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        var allDefinitions = ""
                        val nounDefinitions = response.body()?.get(0)?.meaning?.noun
                        val verbDefinitions = response.body()?.get(0)?.meaning?.intransitiveVerb
                        val exclDefinitions = response.body()?.get(0)?.meaning?.exclamation

                        nounDefinitions?.forEach { allDefinitions += "Noun:\n ${it.definition} \n" }
                        verbDefinitions?.forEach { allDefinitions += "Verb:\n ${it.definition} \n" }
                        exclDefinitions?.forEach { allDefinitions += "Exclamation:\n ${it.definition} \n" }

                        val phonetic = response.body()?.get(0)?.phonetic
                        setNotification(context, "$query [$phonetic]", allDefinitions)
                            .toString()

                    } else {
                        setNotification(context, query, "Error 1").toString()
                    }
                } catch (e: HttpException) {
                    setNotification(context, query, "Error 2").toString()

                } catch (e: Throwable) {
                    setNotification(context, query, "Error 3").toString()
                }
            }
        }
    }

    private fun bang(context: Context, query: String) {
        val client = RetrofitFactory.makeOkHttpClient()
        val request: Request = Request.Builder()
            .url("https://api.duckduckgo.com/?q=$query&format=json&pretty=1&no_redirect=0")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                CoroutineScope(Dispatchers.IO).launch {
                    setNotification(context, query, "Error").toString()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (response.isSuccessful) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(response.request.url.toString())
                        startActivity(context, intent, null)
                        setNotification(context, query, "Bang!").toString()
                    }
                }
            }

        })

    }
}