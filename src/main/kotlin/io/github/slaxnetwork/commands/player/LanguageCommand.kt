package io.github.slaxnetwork.commands.player

import com.github.shynixn.mccoroutine.bukkit.SuspendingCommandExecutor
import io.github.slaxnetwork.kyouko.services.v1.ProfileService
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.bukkitcore.minimessage.tags.LanguageTags
import io.github.slaxnetwork.mm
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.bukkitcore.utils.get
import io.github.slaxnetwork.koin.LibraryKoinComponent
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class LanguageCommand : SuspendingCommandExecutor, LibraryKoinComponent {
    private val profileRegistry: ProfileRegistry = get()
    private val languageProvider: LanguageProvider = get()

    override suspend fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if(sender !is Player) {
            return true
        }

        val profile = profileRegistry.profiles[sender]
            ?: return true

        if(args.isEmpty()) {
            sender.sendMessage(mm.deserialize(
                "<icon:symbol_error> <text>",
                LanguageTags.translateText("commands.language.usage", profile)
            ))

            return true
        }

        val languageId = args.first()
        if(!languageProvider.languages.contains(languageId)) {
            sender.sendMessage(mm.deserialize(
                "<icon:symbol_error> <text>",
                LanguageTags.translateText("commands.language.invalid_language", profile)
            ))
            return true
        }

        // TODO: 2/26/2023 maybe don't update it on the db every single time the command is ran.
        // Cache new language and update on quit.
        ProfileService.updateLanguage(sender.uniqueId, languageId)
            .onSuccess {
                profile.settings.language = languageId

                sender.sendMessage(mm.deserialize(
                    "<icon:symbol_success> <text>",
                    LanguageTags.translateText("commands.language.success", profile),
                    Placeholder.unparsed("language_id", languageId)
                ))
            }
            .onFailure {
                sender.sendMessage(mm.deserialize(
                    "<icon:symbol_error> <text>",
                    LanguageTags.translateText("commands.language.error", profile),
                    Placeholder.unparsed("error_text", it.message ?: "No message specified.")
                ))
            }

        return true
    }
}