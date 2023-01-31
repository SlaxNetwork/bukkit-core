package io.github.slaxnetwork.profile

import io.github.slaxnetwork.api.dto.profile.Profile
import java.util.UUID

interface ProfileRegistry {
    val profiles: Set<Profile>
    val mappedProfiles: Map<UUID, Profile>

    fun add(profile: Profile): Profile

    fun remove(uuid: UUID)
}