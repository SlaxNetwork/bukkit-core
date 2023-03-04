package io.github.slaxnetwork.bukkitcore.utils

import io.github.slaxnetwork.kyouko.models.profile.Profile
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

operator fun ProfileMap.get(player: Player) =
    this[player.uniqueId]

operator fun ProfileMap.get(sender: CommandSender): Profile? =
    if(sender is Player) this[sender.uniqueId] else null

private typealias ProfileMap = Map<UUID, Profile>