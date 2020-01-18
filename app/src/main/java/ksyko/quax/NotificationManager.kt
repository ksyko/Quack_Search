package ksyko.quax

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import ksyko.quax.Global.actionTitle
import ksyko.quax.Global.key
import ksyko.quax.Global.replyLabel

class NotificationManager(private val context: Context) {


    private val remoteInput: RemoteInput = RemoteInput.Builder(key).run {
        setLabel(replyLabel)
        build()
    }

    private val replyPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(
            context,
            0x01a,
            Intent(context, MyReceiver::class.java).putExtra("OP", "ADD"),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private val exitPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(
            context,
            0x01b,
            Intent(context, MyReceiver::class.java).putExtra("OP", "DEL"),
            PendingIntent.FLAG_CANCEL_CURRENT
        )

    private val actionAdd: NotificationCompat.Action =
        NotificationCompat.Action.Builder(
            R.drawable.ic_search,
            actionTitle, replyPendingIntent
        ).addRemoteInput(remoteInput).build()


    private val actionDel: NotificationCompat.Action =
        NotificationCompat.Action.Builder(
            R.drawable.ic_search,
            "Exit", exitPendingIntent
        ).build()


    fun makeNotification(notificationId: Int) {
        val newMessageNotification =
            NotificationCompat.Builder(context, context.applicationInfo.name)
                .addAction(actionAdd)
                .addAction(actionDel)
                .setSmallIcon(R.drawable.ic_search)
                .setOngoing(true)
                .build()
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, newMessageNotification)
        }
    }
}