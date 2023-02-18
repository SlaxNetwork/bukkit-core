package io.github.slaxnetwork.profile

import io.github.slaxnetwork.kyouko.models.profile.Profile
import java.util.UUID

interface ProfileRegistry {
    val profiles: Set<Profile>
    val mappedProfiles: Map<UUID, Profile>

    fun add(profile: Profile): Profile

    fun remove(uuid: UUID)
}