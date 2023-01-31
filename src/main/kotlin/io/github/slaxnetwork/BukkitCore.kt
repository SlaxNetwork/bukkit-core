package io.github.slaxnetwork

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.github.slaxnetwork.listeners.PlayerLoginListener
import io.github.slaxnetwork.profile.ProfileRegistryImpl
import io.github.slaxnetwork.profile.ProfileRegistry
import io.github.slaxnetwork.rank.RankRegistry
import io.github.slaxnetwork.rank.RankRegistryImpl
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.ServicePriority

class BukkitCore : SuspendingJavaPlugin() {
    lateinit var profileRegistry: ProfileRegistry
        private set

    lateinit var rankRegistry: RankRegistry
        private set

    override suspend fun onLoadAsync() {
        mm = MiniMessage.miniMessage()
    }

    override suspend fun onEnableAsync() {
        rankRegistry = RankRegistryImpl()
        try {
            rankRegistry.initialize()
        } catch(ex: Exception) {
            logger.severe("Unable to fetch ranks.")
            ex.printStackTrace()

            server.shutdown()
            return
        }

        profileRegistry = ProfileRegistryImpl()

        server.servicesManager.register(
            BukkitCoreAPI::class.java,
            BukkitCoreAPIImpl(profileRegistry),
            this,
            ServicePriority.Normal
        )

        setOf(
            PlayerLoginListener(this)
        ).forEach { server.pluginManager.registerSuspendingEvents(it, this) }
    }

    override suspend fun onDisableAsync() {
    }
}

/**
 * Public [MiniMessage] instance.
 */
lateinit var mm: MiniMessage
    private set