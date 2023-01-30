package io.github.slaxnetwork.registries

import io.github.slaxnetwork.api.dto.Profile
import java.util.UUID

interface ProfileRegistry {
    val profiles: Set<Profile>
    val mappedProfiles: Map<UUID, Profile>

    fun add(profile: Profile): Profile

    fun remove(uuid: UUID)
}