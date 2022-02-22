package com.luc.domain.usecases

import com.luc.common.NetworkStatus
import com.luc.common.model.Repuesto
import com.luc.domain.email.Mailto

class SendEmailUseCase(private val mailto: Mailto) {
    fun sendEmail(repuesto: List<Repuesto>): NetworkStatus<String> {
        val message = StringBuilder()
        repuesto.forEach {
            message.append(it.toEmailString())
        }
        return mailto.sendEmail(message.toString())
    }
}