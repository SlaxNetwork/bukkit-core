package io.github.slaxnetwork.koin

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication

object LibraryKoinContext {
    lateinit var koinApplication: KoinApplication
        private set

    fun initialize(module: Module) {
        koinApplication = koinApplication {
            modules(module)
        }
    }
}