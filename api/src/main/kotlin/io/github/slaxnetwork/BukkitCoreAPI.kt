package io.github.slaxnetwork

import io.github.slaxnetwork.kyouko.models.profile.Profile
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.UUID

interface BukkitCoreAPI {
    /**
     * Get a player's profile.
     * @param uuid Player UUID.
     * @return [Profile] of connected player, null if none is found.
     */
    fun getProfile(uuid: UUID): Profile?

    fun getBaseMiniMessageBuilder(): MiniMessage.Builder
}