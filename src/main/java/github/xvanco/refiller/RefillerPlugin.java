package github.xvanco.refiller;

import org.bukkit.plugin.java.JavaPlugin;

public class RefillerPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new RefillerListener(), this);
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }
}
