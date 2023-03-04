package io.github.slaxnetwork.rank

import io.github.slaxnetwork.bukkitcore.rank.RankRegistry
import io.github.slaxnetwork.kyouko.models.rank.Rank
import io.github.slaxnetwork.kyouko.services.v1.RankService
import java.util.Collections

class RankRegistryImpl(
    private val rankService: RankService
) : RankRegistry {
    private val _ranks = mutableSetOf<Rank>()

    override val ranks: Set<Rank>
        get() = Collections.unmodifiableSet(_ranks)

    override val mappedRanks: Map<String, Rank>
        get() = ranks.associateBy { it.id }

    override suspend fun initialize() {
        rankService.getAll()
            .getOrThrow()
            .forEach(_ranks::add)
    }
}