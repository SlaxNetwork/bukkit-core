package io.github.slaxnetwork.profile

import io.github.slaxnetwork.kyouko.models.profile.Profile
import org.bukkit.entity.Player
import java.util.UUID

interface ProfileRegistry {
    val profiles: Set<Profile>
    val mappedProfiles: Map<UUID, Profile>

    fun add(profile: Profile): Profile

    fun remove(uuid: UUID)

    fun getFromPlayer(player: Player): Profile? {
        return getFromUUID(player.uniqueId)
    }

    fun getFromUUID(uuid: UUID): Profile?
}