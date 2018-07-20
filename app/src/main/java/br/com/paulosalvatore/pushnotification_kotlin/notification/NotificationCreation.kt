package br.com.paulosalvatore.pushnotification_kotlin.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import br.com.paulosalvatore.pushnotification_kotlin.R
import br.com.paulosalvatore.pushnotification_kotlin.view.MainActivity

class NotificationCreation {
	companion object {
		private var notificationManager: NotificationManager? = null

		const val sNOTIFY_ID = 1000
		private val sVIBRATION = longArrayOf(300, 400, 500, 400, 300)

		// Channel Information
		private const val sCHANNEL_ID = "MovileNext_1"
		private const val sCHANNEL_NAME = "MovileNext - Push Channel 1"
		private const val sCHANNEL_DESCRIPTION = "MovileNext - Push Channel - Used for main notifications"

		fun create(context: Context, title: String, body: String) {
			if (notificationManager == null)
				notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				var channel = notificationManager?.getNotificationChannel(sCHANNEL_ID)

				if (channel == null) {
					val importance = NotificationManager.IMPORTANCE_HIGH

					channel = NotificationChannel(sCHANNEL_ID, sCHANNEL_NAME, importance)
					channel.description = sCHANNEL_DESCRIPTION
					channel.enableVibration(true)
					channel.enableLights(true)
					channel.vibrationPattern = sVIBRATION

					notificationManager?.createNotificationChannel(channel)
				}
			}

			val intent = Intent(context, MainActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

			val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

			val builder =
					NotificationCompat.Builder(context, sCHANNEL_ID)
							.setContentTitle(title)
							.setSmallIcon(R.drawable.ic_notification)
							.setContentText(body)
							.setDefaults(Notification.DEFAULT_ALL)
							.setAutoCancel(true)
							.setContentIntent(pendingIntent)
							.setTicker(title)
							.setVibrate(sVIBRATION)
							.setOnlyAlertOnce(true)
							.setStyle(NotificationCompat
									.BigTextStyle()
									.bigText(body))

			val notificationApp = builder.build()
			notificationManager?.notify(sNOTIFY_ID, notificationApp)
		}
	}
}
