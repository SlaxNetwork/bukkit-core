package io.github.slaxnetwork.profile

import io.github.slaxnetwork.api.dto.Profile
import io.github.slaxnetwork.registries.ProfileRegistry
import java.util.*

class ProfileRegistryImpl : ProfileRegistry {
    private val _profiles = mutableSetOf<Profile>()

    override val profiles: Set<Profile>
        get() = Collections.unmodifiableSet(_profiles)

    override val mappedProfiles: Map<UUID, Profile>
        get() = profiles.associateBy { it.id }

    override fun add(profile: Profile): Profile {
        _profiles.add(profile)
        return profile
    }

    override fun remove(uuid: UUID) {
        _profiles.removeIf {
            it.id == uuid
        }
    }
}