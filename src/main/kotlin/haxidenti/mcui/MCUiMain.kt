package haxidenti.mcui
import com.google.gson.Gson
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class MCUiMain : JavaPlugin(), Listener {

    override fun onEnable() {
        Bukkit.getServer().pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun randomEvent(event: AsyncPlayerChatEvent) {
        if (!event.message.startsWith(".")) return;
        event.isCancelled = true
        // Event action for testing

        Bukkit.getScheduler().scheduleSyncDelayedTask(this) {
            val items = Material.values().map { mat -> CoreUiItem(mat) { event.player.sendMessage(mat.name) } }
            PagedCoreUI(this, "Some test", items, 0, event.player)
        }
    }
}
