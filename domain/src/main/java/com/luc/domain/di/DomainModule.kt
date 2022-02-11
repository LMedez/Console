package com.luc.domain.di

import com.luc.domain.usecases.GetCalderaUseCase
import org.koin.dsl.module

val domainModule = module {
    factory <GetCalderaUseCase> { GetCalderaUseCase(domainRepository = get()) }
}