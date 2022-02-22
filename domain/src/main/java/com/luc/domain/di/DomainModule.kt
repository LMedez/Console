package com.luc.domain.di

import com.luc.domain.email.Mailto
import com.luc.domain.usecases.GetCalderaUseCase
import com.luc.domain.usecases.GetSettingsUseCase
import com.luc.domain.usecases.SendEmailUseCase
import org.koin.dsl.module

val domainModule = module {
    single { Mailto() }
    factory { GetCalderaUseCase(domainRepository = get()) }
    factory { GetSettingsUseCase(domainRepository = get()) }
    factory { SendEmailUseCase(get()) }
}