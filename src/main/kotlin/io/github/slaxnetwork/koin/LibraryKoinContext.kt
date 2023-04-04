package io.github.slaxnetwork.koin

import io.github.slaxnetwork.BukkitCoreAPIImpl
import io.github.slaxnetwork.bukkitcore.BukkitCoreAPI
import io.github.slaxnetwork.bukkitcore.icon.IconRegistry
import io.github.slaxnetwork.bukkitcore.koin.BukkitCoreKoinContext
import io.github.slaxnetwork.bukkitcore.language.LanguageProvider
import io.github.slaxnetwork.bukkitcore.profile.ProfileRegistry
import io.github.slaxnetwork.bukkitcore.rank.RankRegistry
import io.github.slaxnetwork.bukkitcore.scoreboard.ScoreboardManager
import io.github.slaxnetwork.icon.IconRegistryImpl
import io.github.slaxnetwork.language.LanguageProviderImpl
import io.github.slaxnetwork.profile.ProfileRegistryImpl
import io.github.slaxnetwork.rank.RankRegistryImpl
import io.github.slaxnetwork.scoreboard.ScoreboardManagerImpl
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import java.io.File

object LibraryKoinContext {
    lateinit var koinApplication: KoinApplication
        private set

    fun initialize(plugin: JavaPlugin, scheduler: BukkitScheduler) {
        val appModule = initializeLibraryModules(plugin, scheduler)

        koinApplication = koinApplication {
            modules(appModule)

            allowOverride(false)
        }
    }

    private fun initializeLibraryModules(plugin: JavaPlugin, scheduler: BukkitScheduler): Module {
        val profileRegistryModule = module {
            singleOf<ProfileRegistry>(::ProfileRegistryImpl)
        }

        val bukkitCoreModule = module {
            singleOf<BukkitCoreAPI>(::BukkitCoreAPIImpl)
        }

        val scoreBoardManagerModule = module {
            single<ScoreboardManager> { ScoreboardManagerImpl(scheduler) }
        }

        provideBukkitCoreKoinContext(
            plugin,
            profileRegistryModule,
            bukkitCoreModule,
            scoreBoardManagerModule
        )

        val dataModule = module {
            singleOf<LanguageProvider>(::LanguageProviderImpl)
            single<IconRegistry> { (iconsFile: File) -> IconRegistryImpl(iconsFile) }
            singleOf<RankRegistry>(::RankRegistryImpl)
        }

        val libraryAppModule = module {
            includes(
                profileRegistryModule,
                scoreBoardManagerModule,
                dataModule
            )
        }

        return libraryAppModule
    }

    /**
     * Provide module context to accessing plugins that request it.
     */
    private fun provideBukkitCoreKoinContext(
        plugin: JavaPlugin,

        profileRegistryModule: Module,
        bukkitCoreModule: Module,
        scoreboardManagerModule: Module
    ) {
        Bukkit.getServicesManager().register(
            BukkitCoreKoinContext::class.java,
            BukkitCoreKoinContext(profileRegistryModule, bukkitCoreModule, scoreboardManagerModule),
            plugin,
            ServicePriority.Normal
        )
    }
}