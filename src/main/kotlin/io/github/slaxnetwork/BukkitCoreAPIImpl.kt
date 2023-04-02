package io.github.slaxnetwork

import io.github.slaxnetwork.bukkitcore.BukkitCoreAPI
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.kyouko.services.v1.ServerService
import net.kyori.adventure.text.minimessage.MiniMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.util.*

class BukkitCoreAPIImpl : BukkitCoreAPI, KoinComponent {
    override val profileRegistry: ProfileRegistry = get()

    override lateinit var instanceId: String
        private set

    override val profiles: Map<UUID, Profile>
        get() = profileRegistry.profiles

    override suspend fun registerServer(ip: String, port: Int, type: String): Result<String> {
        return ServerService.registerInstance(ip, port, type)
            .onSuccess { instanceId = it }
    }

    override suspend fun unregisterServer(): Result<Unit> {
        return ServerService.unregisterInstance(instanceId)
    }

    override fun getBaseMiniMessageBuilder(): MiniMessage.Builder {
        return SlaxMiniMessageBuilderImpl()
            .getBaseMiniMessageBuilder()
    }
}