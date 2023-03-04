package io.github.slaxnetwork.bukkitcore.profile

import io.github.slaxnetwork.kyouko.models.profile.Profile
import org.bukkit.entity.Player
import java.util.UUID

interface ProfileRegistry {
    val profiles: Map<UUID, Profile>

    fun add(profile: Profile): Profile

    fun remove(uuid: UUID)
}