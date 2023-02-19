package io.github.slaxnetwork

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.github.slaxnetwork.icon.IconRegistry
import io.github.slaxnetwork.icon.IconRegistryImpl
import io.github.slaxnetwork.icon.createIconTagResolver
import io.github.slaxnetwork.kyouko.KyoukoAPI
import io.github.slaxnetwork.listeners.AsyncPlayerChatListener
import io.github.slaxnetwork.listeners.PlayerLoginListener
import io.github.slaxnetwork.profile.ProfileRegistryImpl
import io.github.slaxnetwork.profile.ProfileRegistry
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.plugin.ServicePriority
import javax.swing.text.html.parser.TagElement

class BukkitCore : SuspendingJavaPlugin() {
    lateinit var kyouko: KyoukoAPI
        private set

    lateinit var profileRegistry: ProfileRegistry
        private set

    lateinit var iconRegistry: IconRegistry
        private set

    override suspend fun onLoadAsync() {
        kyouko = KyoukoAPI(System.getenv("API_SECRET") ?: "KYOUKO")
    }

    override suspend fun onEnableAsync() {
        profileRegistry = ProfileRegistryImpl()
        iconRegistry = IconRegistryImpl(kyouko.icons)

        runCatching { iconRegistry.initialize() }
            .onFailure {
                logger.severe("Unable to fetch icons, ${it.message}")
                server.shutdown()
                return
            }

        mm = SlaxMiniMessageBuilder(iconRegistry)
            .createInstance()

        server.servicesManager.register(
            BukkitCoreAPI::class.java,
            BukkitCoreAPIImpl(profileRegistry),
            this,
            ServicePriority.Normal
        )

        setOf(
            AsyncPlayerChatListener(profileRegistry),
            PlayerLoginListener(profileRegistry, kyouko)
        ).forEach { server.pluginManager.registerSuspendingEvents(it, this) }
    }

    fun listen(args: ArgumentQueue, ctx: Context): Tag {
        val id = args.popOr("requires thing")
            .value()

        return Tag.selfClosingInserting(Component.text(
            iconRegistry.mappedIcons[id] ?: 'a'
        ))
    }

    override suspend fun onDisableAsync() {
    }
}

/**
 * Public [MiniMessage] instance.
 */
lateinit var mm: MiniMessage
    private set