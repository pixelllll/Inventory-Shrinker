package me.interuptings.inventoryshrink;

import me.interuptings.inventoryshrink.commands.ShrinkCommand;
import me.interuptings.inventoryshrink.listeners.BlockerListener;
import me.interuptings.inventoryshrink.tasks.ShrinkIntervalTask;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventoryShrink extends JavaPlugin {

    public static ItemStack BLOCKER;

    private ShrinkIntervalTask task;
    private boolean enabled;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.enabled = false;
        BLOCKER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = BLOCKER.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7"));
        meta.addItemFlags(ItemFlag.values());
        BLOCKER.setItemMeta(meta);

        this.registerListeners();
        this.registerCommand();
    }

    /**
     * Registers the listeners for the plugin
     */
    public void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BlockerListener(), this);
    }

    public void registerCommand() {
        PluginCommand pluginCommand = getCommand("invshrink");
        Validate.notNull(pluginCommand, "Command cannot be null");

        pluginCommand.setExecutor(new ShrinkCommand(this));
    }

    /* Getters / Setters */

    /**
     * Checks if the scenario is enabled
     *
     * @return whether the scenario is enabled
     */
    public boolean isItEnabled() {
        return enabled;
    }

    /**
     * Enables the scenario
     */
    public void enable() {
        this.enabled = true;

        this.task = new ShrinkIntervalTask(this);
        this.task.runTaskTimer(this, 20L, 20L);
    }

    /**
     * Disables the scenario
     */
    public void disable() {
        this.enabled = false;

        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

    /**
     * Toggles the scenario
     */
    public void toggle() {
        if (enabled) this.enable();
        else this.disable();
    }
}
