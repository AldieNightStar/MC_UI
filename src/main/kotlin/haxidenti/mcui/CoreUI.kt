package haxidenti.mcui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.util.logging.Level
import kotlin.math.log

class PagedCoreUI(
    val plugin: Plugin,
    val title: String,
    val items: List<CoreUiItem>,
    val pageNumber: Int,
    val player: Player
) {
    init {
        if (pageNumber < 0) throw IllegalStateException("Page number can't be less than zero")
        val maxPage = items.size / 45
        val generatedItems = items.drop(45 * pageNumber).take(45)
        CoreUI(plugin, "$title [$pageNumber/$maxPage]", generatedItems + listOf(
            CoreUiItem(Material.ARROW, "<<<") {
                PagedCoreUI(plugin, title, items, if (pageNumber == 0) 0 else pageNumber - 1, player)
            },
            CoreUiItem(Material.ARROW, ">>>") {
                PagedCoreUI(
                    plugin,
                    title,
                    items,
                    if (pageNumber == maxPage) maxPage else pageNumber + 1,
                    player
                )
            }
        ), player)
    }
}

class CoreUI(
    val plugin: Plugin,
    val title: String,
    val items: List<CoreUiItem>,
    val player: Player
) : Listener {

    private val inv = Bukkit.createInventory(null, 54, title)

    init {
        // Init inventory
        var slot = 0
        items.take(54)
            .map { it.itemStack }
            .forEach { inv.setItem(slot++, it) }
        // Register events
        Bukkit.getServer().pluginManager.registerEvents(this, plugin)
        // Open for the player
        player.openInventory(inv)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.inventory != inv) return
        event.isCancelled = true
        if (event.clickedInventory != inv) return
        val action = items.getOrNull(event.slot)?.itemAction ?: return
        action(event.click)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.inventory != inv) return
        HandlerList.unregisterAll(this)
    }
}

typealias CoreUiAction = (click: ClickType) -> Unit

class CoreUiItem(
    val itemView: Material,
    val itemName: String,
    val itemCount: Int,
    val itemLore: List<String>,
    val itemAction: CoreUiAction
) {
    constructor(item: Material, name: String, count: Int, action: CoreUiAction) : this(
        item,
        name,
        count,
        listOf(),
        action
    )

    constructor(item: Material, name: String, action: CoreUiAction) : this(item, name, 1, listOf(), action)
    constructor(item: Material, action: CoreUiAction) : this(item, item.name, 1, listOf(), action)
    constructor(name: String, action: CoreUiAction) : this(Material.PAPER, name, 1, listOf(), action)


    val itemStack
        get() = ItemStack(itemView, itemCount).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(itemName)
                lore = itemLore
            }
        }
}