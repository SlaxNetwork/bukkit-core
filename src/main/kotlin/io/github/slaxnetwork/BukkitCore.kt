package io.github.slaxnetwork

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import com.github.shynixn.mccoroutine.bukkit.setSuspendingExecutor
import io.github.slaxnetwork.bukkitcore.BukkitCoreAPI
import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.bukkitcore.rank.RankRegistry
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.commands.player.LanguageCommand
import io.github.slaxnetwork.icon.IconRegistryImpl
import io.github.slaxnetwork.language.LanguageProviderImpl
import io.github.slaxnetwork.listeners.AsyncPlayerChatListener
import io.github.slaxnetwork.listeners.PlayerLoginListener
import io.github.slaxnetwork.listeners.PlayerQuitListener
import io.github.slaxnetwork.profile.ProfileRegistryImpl
import io.github.slaxnetwork.rank.RankRegistryImpl
import io.github.slaxnetwork.scoreboard.ScoreboardManagerImpl
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

class BukkitCore : SuspendingJavaPlugin() {

    private lateinit var rankRegistry: RankRegistry

    private lateinit var iconRegistry: IconRegistry

    private lateinit var languageProvider: LanguageProvider

    override suspend fun onLoadAsync() {
    }

    override suspend fun onEnableAsync() {

        rankRegistry = RankRegistryImpl()
        iconRegistry = IconRegistryImpl(File(server.worldContainer, "icons/icons.json"))

        try {
            rankRegistry.initialize()
            iconRegistry.initialize()
        } catch(ex: Exception) {
            ex.printStackTrace()

            server.shutdown()
            return
        }

        languageProvider = LanguageProviderImpl()
        languageProvider.register()

        val koin = initializeKoin()
        Bukkit.getServicesManager().register(
            KoinApplication::class.java,
            koin,
            this,
            ServicePriority.Normal
        )

        mm = SlaxMiniMessageBuilderImpl()
            .createInstance()

        getCommand("language")?.setSuspendingExecutor(LanguageCommand())

        setOf(
            AsyncPlayerChatListener(),
            PlayerLoginListener(),
            PlayerQuitListener()
        ).forEach { server.pluginManager.registerSuspendingEvents(it, this) }
    }

    override suspend fun onDisableAsync() {
        GlobalContext.stopKoin()
    }

    private fun initializeKoin(): KoinApplication {
        val dataModule = module {
            single { ProfileRegistryImpl() } bind ProfileRegistry::class
            single { languageProvider }
            single { iconRegistry }
            single { rankRegistry }
        }

        val managerModule = module {
            single { ScoreboardManagerImpl(server.scheduler) } bind ScoreboardManager::class
        }

        val appModule = module {
            includes(dataModule, managerModule)

            singleOf(::BukkitCoreAPIImpl) { bind<BukkitCoreAPI>() }
        }

        return startKoin {
            modules(appModule)
        }
    }
}

/**
 * Public [MiniMessage] instance.
 */
lateinit var mm: MiniMessage
    private set