package theangel256.nexusinventories.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class PaginatedGui extends BaseGui {

    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedGui(String title, int rows) {
        super(title, rows);
    }

    public abstract List<GuiItem> getPageItems();

    @Override
    public void initializeItems() {
        inventory.clear();
        List<GuiItem> allItems = getPageItems();

        if (allItems != null && !allItems.isEmpty()) {
            for (int i = 0; i < maxItemsPerPage; i++) {
                index = maxItemsPerPage * page + i;
                if (index >= allItems.size())
                    break;
                if (allItems.get(index) != null) {
                    setItem(i, allItems.get(index));
                }
            }
        }

        addNavigationButtons();
    }

    private void addNavigationButtons() {
        if (page > 0) {
            ItemStack prev = new ItemStack(Material.ARROW);
            ItemMeta meta = prev.getItemMeta();
            meta.setDisplayName("Previous Page");
            prev.setItemMeta(meta);

            setItem(rows * 9 - 9, new GuiItem(prev, event -> {
                page--;
                initializeItems();
            }));
        }

        if (index + 1 < getPageItems().size()) {
            ItemStack next = new ItemStack(Material.ARROW);
            ItemMeta meta = next.getItemMeta();
            meta.setDisplayName("Next Page");
            next.setItemMeta(meta);

            setItem(rows * 9 - 1, new GuiItem(next, event -> {
                page++;
                initializeItems();
            }));
        }
    }
}
