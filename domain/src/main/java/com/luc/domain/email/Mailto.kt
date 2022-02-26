package com.luc.domain.email

import android.util.Log
import com.luc.common.NetworkStatus
import com.luc.domain.email.Config.EMAIL_RECEIVED
import com.luc.domain.email.Config.EMAIL_SENDER
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class Mailto {
    //configure SMTP server
    private val props: Properties = Properties().also {
        it["mail.smtp.host"] = "smtp.gmail.com"
        it["mail.smtp.socketFactory.port"] = "465"
        it["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        it["mail.smtp.auth"] = "true"
        it["mail.smtp.port"] = "465"
    }

    //Creating a session
    private val session = Session.getDefaultInstance(props, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(EMAIL_SENDER, Config.PASSWORD)
        }
    })

    fun sendEmail(message: String): NetworkStatus<String> = try {
        MimeMessage(session).let { mime ->
            mime.setFrom(InternetAddress(EMAIL_SENDER))
            //Adding receiver
            mime.addRecipient(Message.RecipientType.TO, InternetAddress(EMAIL_RECEIVED))
            //Adding subject
            mime.subject = "Lista de repuesto Ariston"
            //Adding message
            mime.setText(message)
            //send mail
            Transport.send(mime)
            NetworkStatus.Success("Success")

        }

    } catch (e: MessagingException) {
        NetworkStatus.Error(e, e.message ?: "no message")
    }
}