package io.github.slaxnetwork.bukkitcore.scoreboard

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

data class BoardComponent(
    var text: String = "<EMPTY LINE>",
    var resolvers: TagResolver = TagResolver.empty()
)