package io.github.slaxnetwork

import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.language.LanguageProvider
import io.github.slaxnetwork.profile.ProfileRegistry
import java.util.*

class BukkitCoreAPIImpl(
    private val profileRegistry: ProfileRegistry,
    private val languageProvider: LanguageProvider
): BukkitCoreAPI {
    override fun getProfile(uuid: UUID): Profile? {
        return profileRegistry.mappedProfiles[uuid]
    }
}