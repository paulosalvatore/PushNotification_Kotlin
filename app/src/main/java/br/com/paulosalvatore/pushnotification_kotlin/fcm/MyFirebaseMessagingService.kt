package br.com.paulosalvatore.pushnotification_kotlin.fcm

import android.util.Log
import br.com.paulosalvatore.pushnotification_kotlin.notification.NotificationCreation
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

	private val sTAG = "FMService"

	override fun onNewToken(token: String?) {
		Log.e("NEW_TOKEN", token)

		FirebaseMessaging.getInstance().subscribeToTopic("MAIN")
	}

	override fun onMessageReceived(remoteMessage: RemoteMessage) {
		val notification = remoteMessage.notification

		Log.d(sTAG, "FCM Message ID: ${remoteMessage.messageId}")
		Log.d(sTAG, "FCM Data Message: ${remoteMessage.data}")
		Log.d(sTAG, "FCM Notification Message: $notification")

		if (notification != null) {
			val title = notification.title ?: ""
			val body = notification.body ?: ""
			val dados = remoteMessage.data

			Log.d(sTAG, "FCM Notification Title: $title")
			Log.d(sTAG, "FCM Notification Body: $body")
			Log.d(sTAG, "FCM Notification Data: $dados")

			// Criar a notificação
			NotificationCreation.create(this, title, body)
		}
	}
}
