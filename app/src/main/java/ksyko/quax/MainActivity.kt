package ksyko.quax

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {
    private val key = "QUACK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeNotification()
    }

    private fun makeNotification() {
        val replyLabel = "Quack?"
        val remoteInput: RemoteInput = RemoteInput.Builder(key).run {
            setLabel(replyLabel)
            build()
        }
        val replyPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                applicationContext,
                0xB34D,
                Intent(this, MyReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                R.drawable.ic_launcher_background,
                "Search!", replyPendingIntent
            )
                .addRemoteInput(remoteInput)
                .build()

        val newMessageNotification = NotificationCompat.Builder(this, applicationInfo.name)
            .addAction(action)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(121, newMessageNotification)
        }
    }


}
