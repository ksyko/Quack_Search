package ksyko.quax

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import java.net.URLEncoder


class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.getStringExtra("OP") == "ADD") {
            val query = getMessageText(intent).toString()
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            if (query.startsWith("!")) Banger.bang(context, query)
            else Banger.bang(context, encodedQuery)
        } else {
            with(NotificationManagerCompat.from(context)) {
                cancelAll()
            }
        }
    }


    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence("QUACK")
    }


}