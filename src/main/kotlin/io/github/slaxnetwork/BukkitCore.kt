package io.github.slaxnetwork

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.icon.IconRegistryImpl
import com.github.shynixn.mccoroutine.bukkit.setSuspendingExecutor
import io.github.slaxnetwork.bukkitcore.BukkitCoreAPI
import io.github.slaxnetwork.bukkitcore.koin.BukkitCoreKoinContext
import io.github.slaxnetwork.commands.player.LanguageCommand
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.language.LanguageProviderImpl
import io.github.slaxnetwork.listeners.AsyncPlayerChatListener
import io.github.slaxnetwork.listeners.PlayerLoginListener
import io.github.slaxnetwork.listeners.PlayerQuitListener
import io.github.slaxnetwork.profile.ProfileRegistryImpl
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.bukkitcore.rank.RankRegistry
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.koin.LibraryKoinContext
import io.github.slaxnetwork.rank.RankRegistryImpl
import io.github.slaxnetwork.scoreboard.ScoreboardManagerImpl
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.koin.core.context.GlobalContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.io.File

class BukkitCore : SuspendingJavaPlugin() {
    lateinit var profileRegistry: ProfileRegistry
        private set

    lateinit var rankRegistry: RankRegistry
        private set

    lateinit var iconRegistry: IconRegistry
        private set

    lateinit var languageProvider: LanguageProvider
        private set

    lateinit var scoreboardManager: ScoreboardManager
        private set

    override suspend fun onLoadAsync() {
    }

    override suspend fun onEnableAsync() {
        rankRegistry = RankRegistryImpl()
        profileRegistry = ProfileRegistryImpl()
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

        scoreboardManager = ScoreboardManagerImpl(server.scheduler)

        mm = SlaxMiniMessageBuilderImpl()
            .createInstance()

        initializeKoin()

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

    private fun initializeKoin() {
        val dataModule = module {
            single { languageProvider }
            single { iconRegistry }
            single { rankRegistry }
        }

        val exposedModules = module {
            singleOf<ProfileRegistry>(::ProfileRegistryImpl)
            single<ScoreboardManager> { ScoreboardManagerImpl(server.scheduler) }

            singleOf<BukkitCoreAPI>(::BukkitCoreAPIImpl)
        }

        val appModule = module {
            includes(dataModule, exposedModules)
        }

        // expose needed modules.
        Bukkit.getServicesManager().register(
            BukkitCoreKoinContext::class.java,
            BukkitCoreKoinContext(exposedModules),
            this,
            ServicePriority.Normal
        )

        LibraryKoinContext.initialize(appModule)
    }
}

/**
 * Public [MiniMessage] instance.
 */
lateinit var mm: MiniMessage
    private set