package io.github.slaxnetwork

import io.github.slaxnetwork.bukkitcore.BukkitCoreAPI
import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.kyouko.services.v1.ServerService
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.*

class BukkitCoreAPIImpl(
    override val profileRegistry: ProfileRegistry,
    private val iconRegistry: IconRegistry,
    private val languageProvider: LanguageProvider
): BukkitCoreAPI {
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
        return SlaxMiniMessageBuilderImpl(iconRegistry, languageProvider)
            .getBaseMiniMessageBuilder()
    }
}