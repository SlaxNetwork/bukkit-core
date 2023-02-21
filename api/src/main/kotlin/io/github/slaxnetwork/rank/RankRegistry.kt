package io.github.slaxnetwork.rank

import io.github.slaxnetwork.kyouko.models.rank.Rank

interface RankRegistry {
    val ranks: Set<Rank>
    val mappedRanks: Map<String, Rank>

    suspend fun initialize()
}