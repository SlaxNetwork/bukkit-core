package io.github.slaxnetwork.koin

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

interface LibraryKoinComponent : KoinComponent {
    override fun getKoin(): Koin {
        return LibraryKoinContext.koinApplication.koin
    }
}