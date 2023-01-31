package io.github.slaxnetwork.profile

import io.github.slaxnetwork.api.dto.profile.Profile
import io.github.slaxnetwork.api.dto.profile.ProfilePreferences
import io.github.slaxnetwork.api.dto.rank.Rank
import java.util.UUID

data class ProfileContext(
    val uuid: UUID,
    val preferences: ProfilePreferences,
    val rank: Rank
) {
    constructor(profile: Profile, rank: Rank) :
            this(
                profile.id,
                profile.preferences,
                rank
            )
}