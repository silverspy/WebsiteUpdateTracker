package fr.silverspy.wut

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import fr.silverspy.wut.R
import fr.silverspy.wut.WebsiteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class WebsiteMonitorService : Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var websiteRepository: WebsiteRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        websiteRepository = WebsiteRepository(applicationContext)

        createNotificationChannel()

        GlobalScope.launch {
            while (true) {
                checkWebsites()
                delay(5 * 60 * 1000L) // 5 minutes
            }
        }

        return START_STICKY
    }

    private fun checkWebsites() {
        val websites = websiteRepository.getAllWebsites()
        websites.forEach { website ->
            val lastModified = getLastModified(website.url)
            if (lastModified > website.lastModified) {
                sendNotification(website)
                val updatedWebsite = website.copy(lastModified = lastModified)
                websiteRepository.updateWebsite(updatedWebsite)
            }
        }
    }




    private fun getLastModified(urlString: String): Long {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        try {
            return connection.getHeaderFieldDate("Last-Modified", 0)
        } finally {
            connection.disconnect()
        }
    }


    private fun sendNotification(website: Website) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Website Updated")
            .setContentText("${website.name} has been updated.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(website.id, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Website Monitor", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "website_monitor_channel"
    }
}
