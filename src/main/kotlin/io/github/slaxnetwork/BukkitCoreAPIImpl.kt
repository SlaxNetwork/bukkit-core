package io.github.slaxnetwork

import io.github.slaxnetwork.api.dto.profile.Profile
import io.github.slaxnetwork.profile.ProfileRegistry
import java.util.*

class BukkitCoreAPIImpl(
    private val profileRegistry: ProfileRegistry
): BukkitCoreAPI {

    override fun getProfile(uuid: UUID): Profile? {
        return profileRegistry.mappedProfiles[uuid]
    }
}