package com.amazinghorsess.network.di

import com.amazinghorsess.domain.interactor.ServerInteractor
import com.amazinghorsess.network.KtorServer

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {



    singleOf(::KtorServer) bind ServerInteractor::class

}