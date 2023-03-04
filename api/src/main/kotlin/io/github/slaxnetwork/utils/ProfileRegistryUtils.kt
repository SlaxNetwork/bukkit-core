package io.github.slaxnetwork.utils

import io.github.slaxnetwork.kyouko.models.profile.Profile
import org.bukkit.entity.Player
import java.util.*

operator fun Map<UUID, Profile>.get(player: Player) =
    this[player.uniqueId]