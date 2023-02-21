package io.github.slaxnetwork.listeners

import io.github.slaxnetwork.kyouko.models.profile.Profile
import io.github.slaxnetwork.kyouko.services.v1.ProfileService
import io.github.slaxnetwork.mm
import io.github.slaxnetwork.profile.ProfileRegistry
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerLoginEvent
import java.util.UUID

class PlayerLoginListener(
    private val profileRegistry: ProfileRegistry,
    private val profileService: ProfileService
) : Listener {
    private val pendingConnections = mutableMapOf<UUID, Profile>()

    @EventHandler
    suspend fun onPlayerPreLogin(ev: AsyncPlayerPreLoginEvent) = runBlocking {
        val profile = profileService.findByUUID(ev.uniqueId)
            .getOrNull()

        if(profile == null) {
            ev.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                mm.deserialize("<red>Profile could not be created.")
            )
            return@runBlocking
        }

        pendingConnections[ev.uniqueId] = profile
    }

    @EventHandler
    fun onPlayerLogin(ev: PlayerLoginEvent) {
        val profile = pendingConnections[ev.player.uniqueId]

        if(profile == null) {
            ev.disallow(
                PlayerLoginEvent.Result.KICK_OTHER,
                mm.deserialize("<red>Unable to fetch profile at login stage.")
            )
            return
        }

        profileRegistry.add(profile)

        pendingConnections.remove(ev.player.uniqueId)
    }
}