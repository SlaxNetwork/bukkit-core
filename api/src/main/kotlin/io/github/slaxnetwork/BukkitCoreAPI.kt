package io.github.slaxnetwork

import io.github.slaxnetwork.kyouko.models.profile.Profile
import java.util.UUID

interface BukkitCoreAPI {
    val instanceId: String

    /**
     * Get a player's profile.
     * @param uuid Player UUID.
     * @return [Profile] of connected player, null if none is found.
     */
    fun getProfile(uuid: UUID): Profile?

    suspend fun registerServer(ip: String, port: Int, type: String): Result<String>

    suspend fun unregisterServer(): Result<Unit>
}