# MC UI
### by HaxiDenti
[![](https://jitpack.io/v/AldieNightStar/MC_UI.svg)](https://jitpack.io/#AldieNightStar/MC_UI)

* Plugin name (for dependency): `mc_ui`

# Dependencies
* `kotlin-stdlib`

# Usage
```kotlin
// Open Paged UI for player
PagedCoreUI(plugin, "My Menu", menuItems, 0, player)

// Open NON Paged UI for player
CoreUI(plugin, "My Menu", menuItems, player)

// MenuItem(s) looks like this
//   lore is a list of strings. Can be empty
CoreUiItem(Material.DIAMOND_SWORD, "Attack", 1, lore) {
    // it - it's a ClickType
    //   we can check it with:  if (it == ClickType.LEFT) { ... }
    // Do some action on this one
}
```
