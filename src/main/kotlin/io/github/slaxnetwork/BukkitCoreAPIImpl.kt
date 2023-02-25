package io.github.slaxnetwork

import io.github.slaxnetwork.icon.IconRegistry
import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.language.LanguageProvider
import io.github.slaxnetwork.profile.ProfileRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.*

class BukkitCoreAPIImpl(
    private val profileRegistry: ProfileRegistry,
    private val iconRegistry: IconRegistry,
    private val languageProvider: LanguageProvider
): BukkitCoreAPI {
    override fun getProfile(uuid: UUID): Profile? {
        return profileRegistry.mappedProfiles[uuid]
    }

    override fun getBaseMiniMessageBuilder(): MiniMessage.Builder {
        return SlaxMiniMessageBuilderImpl(iconRegistry, languageProvider)
            .getBaseMiniMessageBuilder()
    }
}