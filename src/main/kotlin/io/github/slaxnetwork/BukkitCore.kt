package io.github.slaxnetwork

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.github.shynixn.mccoroutine.bukkit.setSuspendingExecutor
import io.github.slaxnetwork.commands.player.LanguageCommand
import io.github.slaxnetwork.icon.IconRegistry
import io.github.slaxnetwork.icon.IconRegistryImpl
import io.github.slaxnetwork.kyouko.KyoukoAPI
import io.github.slaxnetwork.language.LanguageProvider
import io.github.slaxnetwork.language.LanguageProviderImpl
import io.github.slaxnetwork.listeners.AsyncPlayerChatListener
import io.github.slaxnetwork.listeners.PlayerLoginListener
import io.github.slaxnetwork.listeners.PlayerQuitListener
import io.github.slaxnetwork.profile.ProfileRegistryImpl
import io.github.slaxnetwork.profile.ProfileRegistry
import io.github.slaxnetwork.rank.RankRegistry
import io.github.slaxnetwork.rank.RankRegistryImpl
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.ServicePriority

class BukkitCore : SuspendingJavaPlugin() {
    lateinit var kyouko: KyoukoAPI
        private set

    lateinit var profileRegistry: ProfileRegistry
        private set

    lateinit var rankRegistry: RankRegistry
        private set

    lateinit var iconRegistry: IconRegistry
        private set

    lateinit var languageProvider: LanguageProvider
        private set

    override suspend fun onLoadAsync() {
        kyouko = KyoukoAPI(System.getenv("API_SECRET") ?: "KYOUKO")
    }

    override suspend fun onEnableAsync() {
        rankRegistry = RankRegistryImpl(kyouko.ranks)
        profileRegistry = ProfileRegistryImpl()
        iconRegistry = IconRegistryImpl(kyouko.icons)

        try {
            rankRegistry.initialize()
            iconRegistry.initialize()
        } catch(ex: Exception) {
            logger.severe("Unable to initialize a service.")
            ex.printStackTrace()
            server.shutdown()
            return
        }

        languageProvider = LanguageProviderImpl()
        languageProvider.register()

        mm = SlaxMiniMessageBuilderImpl(iconRegistry, languageProvider)
            .createInstance()

        server.servicesManager.register(
            BukkitCoreAPI::class.java,
            BukkitCoreAPIImpl(profileRegistry, iconRegistry, languageProvider),
            this,
            ServicePriority.Normal
        )

        getCommand("language")?.setSuspendingExecutor(LanguageCommand(profileRegistry, languageProvider, kyouko.profiles))

        setOf(
            AsyncPlayerChatListener(profileRegistry),
            PlayerLoginListener(profileRegistry, kyouko.profiles),
            PlayerQuitListener(profileRegistry)
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