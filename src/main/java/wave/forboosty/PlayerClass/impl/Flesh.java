package wave.forboosty.PlayerClass.impl;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.util.CooldownTracker;

public class Flesh extends PlayerClass {
    public final static Flesh flesh = new Flesh();
    public Flesh() {
        super("Flesh", 25, NamespacedKey.fromString("flesh"));
    }

    @Override
    public boolean onUse(Player player) {
        if(CooldownTracker.isOnCooldown(player,flesh)){
            player.sendMessage(ChatColor.RED + "Ваша способность находится на перезарядке!");
            return true;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,400,2));
        CooldownTracker.setCooldown(player,flesh,flesh.getCOOLDOWN());
        return true;
    }

    @Override
    public void onJoin(Player player) {
        if(player.getPersistentDataContainer().has(flesh.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "У вас и так этот класс!");
        } else {
            player.sendMessage(ChatColor.RED + "Вы успешно присоединились к " + flesh.getName());
            player.getPersistentDataContainer().set(flesh.getKey(), PersistentDataType.STRING,"HeroClass");
        }
    }

    @Override
    public void onLeave(Player player) {
        if(player.getPersistentDataContainer().has(flesh.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "Вы успешно покинули " + flesh.getName());
            player.getPersistentDataContainer().remove(flesh.getKey());
        } else {
            player.sendMessage(ChatColor.RED + "Вы не состоите в этом классе");
        }
    }
}
