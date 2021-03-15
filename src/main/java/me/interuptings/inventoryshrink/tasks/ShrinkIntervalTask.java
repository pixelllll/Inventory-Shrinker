package me.interuptings.inventoryshrink.tasks;

import me.interuptings.inventoryshrink.InventoryShrink;
import me.interuptings.inventoryshrink.TimeUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created on 3/15/2021
 *
 * @author Dylan
 */
public class ShrinkIntervalTask extends BukkitRunnable {

    protected static final int[] SLOT_ORDER = new int[] {
            17, 16, 15, 14, 13, 12, 11, 10, 9,
            26, 25, 24, 23, 22, 21, 20, 19, 18,
            35, 34, 33, 32, 31, 30, 29, 28, 27,
            8, 7, 6, 5, 4, 3, 2, 1, 0 };

    private final InventoryShrink plugin;

    private final int configInterval;
    private int countdown;

    public ShrinkIntervalTask(InventoryShrink plugin) {
        this.plugin = plugin;

        this.configInterval = plugin.getConfig().getInt("shrink-interval");
        this.countdown = configInterval;
    }

    @Override
    public void run() {
        this.countdown--;
        if (!plugin.isItEnabled()) {
            this.cancel();
            return;
        }

        String message = ChatColor.translateAlternateColorCodes('&', "&dAn inventory slot will be removed in &6" + TimeUtil.secondsToString(countdown));
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
        if (this.countdown != 0) return;

        for (Player player : plugin.getServer().getOnlinePlayers()) {

            ItemStack[] contents = player.getInventory().getContents().clone();
            for (int i : SLOT_ORDER) {
                ItemStack content = contents[i];
                if (content != null && content.isSimilar(InventoryShrink.BLOCKER)) continue;

                contents[i] = InventoryShrink.BLOCKER;
                break;
            }
            player.getInventory().setContents(contents);
        }

        this.countdown = configInterval;
        plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c[!] &7Your inventory has now shrunk."));
    }
}
