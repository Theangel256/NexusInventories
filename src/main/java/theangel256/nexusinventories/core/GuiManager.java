package theangel256.nexusinventories.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager implements Listener {

    private static GuiManager instance;
    private final Map<UUID, BaseGui> activeGuis = new HashMap<>();

    private GuiManager() {
    }

    public static GuiManager getInstance() {
        if (instance == null) {
            instance = new GuiManager();
        }
        return instance;
    }

    public void init(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openGui(Player player, BaseGui gui) {
        activeGuis.put(player.getUniqueId(), gui);
        player.openInventory(gui.getInventory());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;

        // Check if the top inventory (the GUI) is a BaseGui
        if (event.getView().getTopInventory().getHolder() instanceof BaseGui) {
            event.setCancelled(true); // Cancel all clicks when a GUI is open

            // Only handle the click if it was in the GUI itself
            if (event.getClickedInventory() != null &&
                    event.getClickedInventory().getHolder() instanceof BaseGui) {
                BaseGui gui = (BaseGui) event.getClickedInventory().getHolder();
                gui.handleClick(event);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof BaseGui) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            activeGuis.remove(event.getPlayer().getUniqueId());
        }
    }
}
