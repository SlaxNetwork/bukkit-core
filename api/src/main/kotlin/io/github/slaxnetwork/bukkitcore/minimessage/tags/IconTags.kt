package io.github.slaxnetwork.bukkitcore.minimessage.tags

import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue

class IconTags(
    private val iconRegistry: IconRegistry
) {
    /**
     * Converts an icon tag of <icon:(TAG_ID)> into its corresponding unicode character to be
     * handled by the resource pack.
     */
    fun iconTag(args: ArgumentQueue, ctx: Context): Tag {
        val iconId = args.popOr("The <icon> tag requires an id to be passed.")
            .value()

        return Tag.selfClosingInserting(
            Component
                .text(iconRegistry.icons[iconId] ?: UNICODE_WARNING_SYMBOL)
        )
    }

    companion object {
        // tmp
        private const val UNICODE_WARNING_SYMBOL = '당'
    }
}