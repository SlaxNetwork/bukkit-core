package io.github.slaxnetwork.bukkitcore.koin

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Used to provide context on exposed Bukkit Core modules such as
 * [io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry] to be used in Koin.
 */
data class BukkitCoreKoinContext(
    val profileRegistryModule: Module,
    val bukkitCoreModule: Module,
    val scoreboardManagerModule: Module
) {
    fun toContextModule(): Module {
        return module {
            includes(profileRegistryModule, bukkitCoreModule, scoreboardManagerModule)
        }
    }
}