package io.github.slaxnetwork.bukkitcore.koin

import org.koin.core.module.Module

/**
 * Used to provide context on exposed Bukkit Core modules such as
 * [io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry] to be used in Koin.
 */
data class BukkitCoreKoinContext(
    val exposedModules: Module
)
