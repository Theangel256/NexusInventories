package theangel256.nexusinventories.core;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseGui implements InventoryHolder {

    protected Inventory inventory;
    protected Map<Integer, GuiItem> items = new HashMap<>();
    protected String title;
    protected int rows;

    public BaseGui(String title, int rows) {
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
    }

    public abstract void initializeItems();

    public void setItem(int slot, GuiItem item) {
        items.put(slot, item);
        inventory.setItem(slot, item.getItemStack());
    }

    public void handleClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != inventory)
            return;

        GuiItem item = items.get(event.getSlot());
        if (item != null && item.getAction() != null) {
            item.getAction().accept(event);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void open(org.bukkit.entity.Player player) {
        initializeItems();
        GuiManager.getInstance().openGui(player, this);
    }
}
