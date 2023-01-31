package io.github.slaxnetwork.rank

import io.github.slaxnetwork.api.dto.rank.Rank
import io.github.slaxnetwork.api.wrapper.RankAPI
import java.util.*

class RankRegistryImpl : RankRegistry {
    private val _ranks = mutableSetOf<Rank>()

    override val ranks: Set<Rank>
        get() = Collections.unmodifiableSet(_ranks)

    override val mappedRanks: Map<String, Rank>
        get() = ranks.associateBy { it.id }

    override suspend fun initialize() {
        RankAPI.getAll()
            .getOrThrow()
            .forEach(this::add)
    }

    override fun add(rank: Rank) {
        _ranks.add(rank)
    }
}