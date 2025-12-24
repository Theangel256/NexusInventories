package theangel256.nexusinventories;

import org.bukkit.plugin.java.JavaPlugin;

import theangel256.nexusinventories.core.GuiManager;
import theangel256.nexusinventories.demo.TestCommand;
import theangel256.nexusinventories.utils.UpdateChecker;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        UpdateChecker checker = new UpdateChecker(this, 68770);
        try {
            if (checker.checkForUpdates(this)) {
                getLogger().info("§6A new update is available! Please update to the latest version.");
            }
        } catch (Exception e) {
            getLogger().warning("§4Could not check for updates!");
        }
        GuiManager.getInstance().init(this);
        getCommand("nexusinventories").setExecutor(new TestCommand());
    }
}
