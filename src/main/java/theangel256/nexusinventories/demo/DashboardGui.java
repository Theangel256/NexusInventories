package theangel256.nexusinventories.demo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import theangel256.nexusinventories.core.BaseGui;
import theangel256.nexusinventories.core.GuiItem;

import java.util.Arrays;

public class DashboardGui extends BaseGui {

    public DashboardGui() {
        super("Dashboard", 3);
    }

    @Override
    public void initializeItems() {
        ItemStack head = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(ChatColor.GREEN + "My Stats");
        headMeta.setLore(Arrays.asList(ChatColor.GRAY + "Click to view stats"));
        head.setItemMeta(headMeta);

        setItem(11, new GuiItem(head, event -> {
            Player player = (Player) event.getWhoClicked();
            player.sendMessage(ChatColor.GREEN + "Your Health: " + player.getHealth());
            player.sendMessage(ChatColor.GREEN + "Your Level: " + player.getLevel());
        }));

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName(ChatColor.RED + "Close");
        barrier.setItemMeta(barrierMeta);

        setItem(15, new GuiItem(barrier, event -> {
            event.getWhoClicked().closeInventory();
        }));

        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);

        for (int i = 0; i < rows * 9; i++) {
            if (!items.containsKey(i)) {
                setItem(i, new GuiItem(glass));
            }
        }
    }
}
