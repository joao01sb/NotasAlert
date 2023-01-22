package com.joao01sb.tarefas.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.joao01sb.tarefas.R

const val NOTIFICATION_ID = 1
const val CHANNEL = "principal"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val notification = NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.ic_action_task)
                .setContentTitle("Tarefa")
                .setContentText("A data da sua tarefa está concluida")
                .build()


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

}