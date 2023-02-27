package io.github.slaxnetwork.profile

import io.github.slaxnetwork.kyouko.models.profile.Profile
import java.util.*

class ProfileRegistryImpl : ProfileRegistry {
    private val _profiles = mutableSetOf<Profile>()

    override val profiles: Set<Profile>
        get() = Collections.unmodifiableSet(_profiles)

    override val mappedProfiles: Map<UUID, Profile>
        get() = profiles.associateBy { it.uuid }

    override fun add(profile: Profile): Profile {
        _profiles.add(profile)
        return profile
    }

    override fun remove(uuid: UUID) {
        _profiles.removeIf {
            it.uuid == uuid
        }
    }

    override fun getFromUUID(uuid: UUID): Profile? {
        return mappedProfiles[uuid]
    }
}