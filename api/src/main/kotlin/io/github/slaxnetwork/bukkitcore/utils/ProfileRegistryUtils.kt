package io.github.slaxnetwork.bukkitcore.utils

import io.github.slaxnetwork.kyouko.models.profile.Profile
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

operator fun Map<UUID, Profile>.get(player: Player) =
    this[player.uniqueId]

operator fun Map<UUID, Profile>.get(sender: CommandSender): Profile? =
    if(sender is Player) this[sender.uniqueId] else null