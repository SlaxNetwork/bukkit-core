package io.github.slaxnetwork.profile

import io.github.slaxnetwork.api.dto.profile.Profile
import java.util.UUID

interface ProfileRegistry {
    val profiles: Set<ProfileContext>
    val mappedProfiles: Map<UUID, ProfileContext>

    fun add(profile: Profile): ProfileContext

    fun remove(uuid: UUID)
}