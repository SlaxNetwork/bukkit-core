package io.github.slaxnetwork.commands.player

import com.github.shynixn.mccoroutine.bukkit.SuspendingCommandExecutor
import io.github.slaxnetwork.kyouko.services.v1.ProfileService
import io.github.slaxnetwork.language.LanguageProvider
import io.github.slaxnetwork.minimessage.tags.ProfileTags
import io.github.slaxnetwork.mm
import io.github.slaxnetwork.profile.ProfileRegistry
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LanguageCommand(
    private val profileRegistry: ProfileRegistry,
    private val languageProvider: LanguageProvider,
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

        val profile = profileRegistry.getFromPlayer(sender)
            ?: return true

        if(args.isEmpty()) {
            sender.sendMessage(mm.deserialize(
                "<icon:symbol_error> <text>",
                ProfileTags.translateText("commands.language.usage", profile)
            ))

            return true
        }

        val languageId = args.first()
        if(!languageProvider.languages.contains(languageId)) {
            sender.sendMessage(mm.deserialize(
                "<icon:symbol_error> <text>",
                ProfileTags.translateText("commands.language.invalid_language", profile)
            ))
            return true
        }

        // TODO: 2/26/2023 maybe don't update it on the db every single time the command is ran.
        // Cache new language and update on quit.
        profileService.updateLanguage(sender.uniqueId, languageId)
            .onSuccess {
                sender.sendMessage(mm.deserialize(
                    "<icon:symbol_success> <text>",
                    ProfileTags.translateText("commands.language.success", profile),
                    Placeholder.unparsed("language_id", languageId)
                ))
            }
            .onFailure {
                sender.sendMessage(mm.deserialize(
                    "<icon:symbol_error> <text>",
                    ProfileTags.translateText("commands.language.error", profile),
                    Placeholder.unparsed("error_text", it.message ?: "No message specified.")
                ))
            }

        return true
    }
}