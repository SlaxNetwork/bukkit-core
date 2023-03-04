package io.github.slaxnetwork.bukkitcore

import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.ServicesManager
import java.util.UUID

interface BukkitCoreAPI {
    val instanceId: String

    val profileRegistry: ProfileRegistry

    /**
     * Every connected player [Profile].
     */
    val profiles: Map<UUID, Profile>

    suspend fun registerServer(ip: String, port: Int, type: String): Result<String>

    suspend fun unregisterServer(): Result<Unit>

    fun getBaseMiniMessageBuilder(): MiniMessage.Builder

    companion object {
        fun get(sm: ServicesManager): BukkitCoreAPI? {
            if(sm.isProvidedFor(BukkitCoreAPI::class.java)) {
                return sm.getRegistration(BukkitCoreAPI::class.java)
                    ?.provider
            }

            return null
        }
    }
}