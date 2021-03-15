package me.interuptings.inventoryshrink.listeners;

import me.interuptings.inventoryshrink.InventoryShrink;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created on 3/15/2021
 *
 * @author Dylan
 */
public class BlockerListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (event.getItem() == null) return;
        if (!event.getItem().isSimilar(InventoryShrink.BLOCKER)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().isSimilar(InventoryShrink.BLOCKER)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void on(InventoryMoveItemEvent event) {
        if (!event.getItem().isSimilar(InventoryShrink.BLOCKER)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void on(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (!event.getItemDrop().getItemStack().isSimilar(InventoryShrink.BLOCKER)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void on(PlayerDeathEvent event) {
        event.getDrops().removeIf(item -> {
            if (item == null) return false;

            return item.isSimilar(InventoryShrink.BLOCKER);
        });
    }

}
