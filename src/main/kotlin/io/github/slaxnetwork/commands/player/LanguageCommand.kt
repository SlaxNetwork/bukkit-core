package io.github.slaxnetwork.commands.player

import com.github.shynixn.mccoroutine.bukkit.SuspendingCommandExecutor
import io.github.slaxnetwork.kyouko.services.v1.ProfileService
import io.github.slaxnetwork.minimessage.tags.ProfileTags
import io.github.slaxnetwork.mm
import io.github.slaxnetwork.profile.ProfileRegistry
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LanguageCommand(
    private val profileRegistry: ProfileRegistry,
    private val profileService: ProfileService
) : SuspendingCommandExecutor {
    override suspend fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if(sender !is Player) {
            return true
        }

        val profile = profileRegistry.mappedProfiles[sender.uniqueId]
            ?: return true

        sender.sendMessage(mm.deserialize(
            "<text>",
            ProfileTags.translateText("server.connect", profile),
            ProfileTags.coloredName(sender.name, profile)
        ))

        return true
    }
}