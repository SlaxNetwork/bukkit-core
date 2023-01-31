package io.github.slaxnetwork.profile

import io.github.slaxnetwork.api.dto.profile.Profile
import io.github.slaxnetwork.rank.RankRegistry
import java.util.*

class ProfileRegistryImpl(
    private val rankRegistry: RankRegistry
) : ProfileRegistry {
    private val _profiles = mutableSetOf<ProfileContext>()

    override val profiles: Set<ProfileContext>
        get() = Collections.unmodifiableSet(_profiles)

    override val mappedProfiles: Map<UUID, ProfileContext>
        get() = profiles.associateBy { it.uuid }

    override fun add(profile: Profile): ProfileContext {
        val rank = rankRegistry.mappedRanks.getOrDefault(
            profile.rankId,
            rankRegistry.mappedRanks["default"]
                ?: throw NullPointerException("profile rank ${profile.rankId} not found and default not found.")
        )

        val ctx = ProfileContext(
            profile,
            rank
        )

        _profiles.add(ctx)
        return ctx
    }

    override fun remove(uuid: UUID) {
        _profiles.removeIf {
            it.uuid == uuid
        }
    }
}