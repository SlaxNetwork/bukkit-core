package io.github.slaxnetwork

import io.github.slaxnetwork.kyouko.models.profile.Profile
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.ServicesManager
import java.util.UUID

interface BukkitCoreAPI {
    val instanceId: String

    /**
     * Every connected player [Profile].
     */
    val profiles: Map<UUID, Profile>

    suspend fun registerServer(ip: String, port: Int, type: String): Result<String>

    suspend fun unregisterServer(): Result<Unit>

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