package nl.twinkelmc.core

import net.luckperms.api.LuckPerms
import nl.twinkelmc.core.areaselector.AreaSelecting
import nl.twinkelmc.core.areaselector.ToolEvent
import nl.twinkelmc.core.commands.CommandHandler
import nl.twinkelmc.core.events.ChatEvent
import nl.twinkelmc.core.events.ConnectionEvent
import nl.twinkelmc.core.events.ExplosionEvent
import nl.twinkelmc.core.events.PlayerHealth
import nl.twinkelmc.core.modules.ClassInstance
import nl.twinkelmc.core.modules.PlayerModule
import nl.twinkelmc.core.modules.TeamManager
import nl.twinkelmc.core.utils.LPPrefix
import nl.twinkelmc.core.utils.MessageFormatter
import nl.twinkelmc.core.utils.PlayerFinder
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {

    companion object {
        lateinit var instance: Main
    }

    val classInstance = ClassInstance()
    val playerModule = PlayerModule()
    val playerFinder = PlayerFinder()
    val messageFormatter = MessageFormatter()
    val prefix = LPPrefix()
    val teamManager = TeamManager()
    val areaSelecting = AreaSelecting()
    var api: LuckPerms? = null

    override fun onEnable() {

        instance = this

        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)
        if (provider != null)
            api = provider.provider

        playerModule.register(Bukkit.getOnlinePlayers())
        teamManager.resetAllTeams()
        //messageFormatter.read()

        registerEvents()
        registerCommands()

        logger.info("TMC-Core enabled.")

    }

    override fun onDisable() {

        logger.info("TMC-Core disabled.")

    }

    private fun registerEvents() {

        Bukkit.getPluginManager().registerEvents(ConnectionEvent(), this)
        Bukkit.getPluginManager().registerEvents(ChatEvent(), this)
        Bukkit.getPluginManager().registerEvents(PlayerHealth(), this)
        Bukkit.getPluginManager().registerEvents(ExplosionEvent(), this)
        Bukkit.getPluginManager().registerEvents(ToolEvent(), this)

    }

    private fun registerCommands() {
        for (command in this.description.commands) {
            val c = this.getCommand(command.key)
            if (c != null && c.isRegistered) {
                c.setExecutor(CommandHandler())
            }
        }
    }

}