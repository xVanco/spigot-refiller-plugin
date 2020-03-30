package github.xvanco.refiller;

import org.bukkit.plugin.java.JavaPlugin;

public class RefillerPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new RefillerListener(), this);
        getLogger().info("Refiller enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Refiller disabled");
    }
}
