package wave.forboosty.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.event.SuperHeroEvent;

import java.util.HashMap;
import java.util.Map;

public class CooldownTracker {

    private static final Map<Player, Map<PlayerClass,Long>> cooldowns = new HashMap<>();

    public static boolean isOnCooldown(Player player,PlayerClass playerClass){
        Map<PlayerClass, Long> playercooldowns = cooldowns.get(player);
        if(playercooldowns == null){
            return false;
        }
        Long cooldownEnd = playercooldowns.get(playerClass);
        if(cooldownEnd == null){
            return false;
        }
        return cooldownEnd > System.currentTimeMillis();
    }

    public static void setCooldown(Player player,PlayerClass playerClass,int seconds){
        Map<PlayerClass, Long> playercooldowns = cooldowns.computeIfAbsent(player, k -> new HashMap<>());
        playercooldowns.put(playerClass,System.currentTimeMillis() + seconds*1000L);
    }
}
