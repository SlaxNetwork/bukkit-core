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
import io.github.slaxnetwork.koin.LibraryKoinComponent
import io.github.slaxnetwork.koin.LibraryKoinContext
import io.github.slaxnetwork.rank.RankRegistryImpl
import io.github.slaxnetwork.scoreboard.ScoreboardManagerImpl
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import java.io.File

class BukkitCore : SuspendingJavaPlugin(), LibraryKoinComponent {
    override suspend fun onLoadAsync() {
        LibraryKoinContext.initialize(this, server.scheduler)
    }

    override suspend fun onEnableAsync() {
        val rankRegistry by inject<RankRegistry>()
        val iconRegistry: IconRegistry by inject {
            parametersOf(File(server.worldContainer, "icons/icons.json"))
        }
        val languageProvider by inject<LanguageProvider>()

        try {
            rankRegistry.initialize()
            iconRegistry.initialize()

            languageProvider.register()
        } catch(ex: Exception) {
            ex.printStackTrace()

            server.shutdown()
            return
        }

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
        LibraryKoinContext.koinApplication.close()
    }
}

/**
 * Public [MiniMessage] instance.
 */
lateinit var mm: MiniMessage
    private set