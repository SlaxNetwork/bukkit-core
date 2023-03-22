package io.github.slaxnetwork.profile

import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.kyouko.models.profile.Profile
import java.util.*

class ProfileRegistryImpl : ProfileRegistry {
    private val _profiles = mutableSetOf<Profile>()

    override val profiles: Map<UUID, Profile>
        get() = _profiles.associateBy { it.uuid }

    override fun add(profile: Profile): Profile {
        _profiles.add(profile)
        return profile
    }

    override fun remove(uuid: UUID) {
        _profiles.removeIf {
            it.uuid == uuid
        }
    }

    override fun findByUUID(uuid: UUID): Profile? {
        return profiles[uuid]
    }
}