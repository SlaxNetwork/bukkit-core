package io.github.slaxnetwork.bukkitcore.icon

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

fun createIconTagResolver(iconRegistry: IconRegistry): TagResolver {
    val tagResolver = TagResolver.builder()

    iconRegistry.icons.forEach { (k, v) ->
        tagResolver.tag(
            "icon_${k}",
            Tag.selfClosingInserting(Component.text(v))
        )
    }

    return tagResolver.build()
}