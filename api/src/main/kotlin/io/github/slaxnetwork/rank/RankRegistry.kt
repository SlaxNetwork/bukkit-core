package io.github.slaxnetwork.rank

import io.github.slaxnetwork.api.dto.rank.Rank

interface RankRegistry {
    val ranks: Set<Rank>
    val mappedRanks: Map<String, Rank>

    suspend fun initialize()

    fun add(rank: Rank)
}