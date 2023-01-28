package haxidenti.mcui

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class MCUiMain : JavaPlugin(), Listener {

//    override fun onEnable() {
//        Bukkit.getServer().pluginManager.registerEvents(this, this)
//    }
//
//    @EventHandler
//    fun randomEvent(event: AsyncPlayerChatEvent) {
//        if (!event.message.startsWith(".")) return;
//        event.isCancelled = true
//        // Event action for testing
//
//        Bukkit.getScheduler().scheduleSyncDelayedTask(this) {
//            val items = Material.values().map { mat -> CoreUiItem(mat) { event.player.sendMessage(mat.name); event.player.closeInventory() } }
//            PagedCoreUI(this, "Some test", items, 0, event.player)
//        }
//    }
}
