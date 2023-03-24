package io.github.slaxnetwork.bukkitcore.utilities.command

import io.github.slaxnetwork.bukkitcore.minimessage.tags.LanguageTags
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private const val INVALID_PERMISSIONS_UNSERIALIZED_MESSAGE = "<icon:symbol_warning> <text>"
private const val INVALID_PERMISSIONS_KEY = ""

fun CommandSender.checkPermission(
    node: String,
    profileRegistry: ProfileRegistry,
    mm: MiniMessage = MiniMessage.miniMessage()
): Boolean {
    if(hasPermission(node)) {
        return true
    }

    if(this is Player) {
        val profile = profileRegistry.findByUUID(uniqueId)
            ?: return false

        this.sendMessage(mm.deserialize(
            INVALID_PERMISSIONS_UNSERIALIZED_MESSAGE,
            LanguageTags.translateText(INVALID_PERMISSIONS_KEY, profile)
        ))
    }

    return false
}