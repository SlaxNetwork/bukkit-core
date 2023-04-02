package io.github.slaxnetwork.bukkitcore.minimessage.tags

import io.github.slaxnetwork.kyouko.models.rank.Rank
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object RankTags {
    /**
     * Replace <rank_icon> with the rank icon.
     * @param rank Rank to get icon for.
     */
    fun icon(rank: Rank): TagResolver {
        return Placeholder.parsed(
            "rank_icon",
            "<icon:${rank.prefixId}>"
        )
    }
}