package io.github.slaxnetwork

import io.github.slaxnetwork.icon.IconRegistry
import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.kyouko.services.v1.ServerService
import io.github.slaxnetwork.language.LanguageProvider
import io.github.slaxnetwork.profile.ProfileRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.*

class BukkitCoreAPIImpl(
    override val profileRegistry: ProfileRegistry,
    private val iconRegistry: IconRegistry,
    private val languageProvider: LanguageProvider,
    private val serverService: ServerService
): BukkitCoreAPI {
    override lateinit var instanceId: String
        private set

    override val profiles: Map<UUID, Profile>
        get() = profileRegistry.mappedProfiles

    override suspend fun registerServer(ip: String, port: Int, type: String): Result<String> {
        return serverService.registerInstance(ip, port, type)
            .onSuccess { instanceId = it }
    }

    override suspend fun unregisterServer(): Result<Unit> {
        return serverService.unregisterInstance(instanceId)
    }

    override fun getBaseMiniMessageBuilder(): MiniMessage.Builder {
        return SlaxMiniMessageBuilderImpl(iconRegistry, languageProvider)
            .getBaseMiniMessageBuilder()
    }
}