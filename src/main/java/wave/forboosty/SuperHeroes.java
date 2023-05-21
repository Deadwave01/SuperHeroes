package wave.forboosty;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import wave.forboosty.Commands.Commands;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.event.SuperHeroEvent;

public final class SuperHeroes extends JavaPlugin {

    private static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new SuperHeroEvent(),instance);
        getCommand("heroclass").setExecutor(new Commands());
        PlayerClass.enabledPlayerClasses();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
