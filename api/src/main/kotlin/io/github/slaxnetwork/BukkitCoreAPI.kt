package io.github.slaxnetwork

import io.github.slaxnetwork.api.dto.Profile
import java.util.UUID

interface BukkitCoreAPI {
    /**
     * Get a player's profile.
     * @param uuid Player UUID.
     * @return [Profile] of connected player, null if none is found.
     */
    fun getProfile(uuid: UUID): Profile?
}