package wave.forboosty.PlayerClass.impl;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.SuperHeroes;
import wave.forboosty.util.CooldownTracker;

public class SnowyQueen extends PlayerClass {

    private final int radius = 10;
    public final static SnowyQueen snowyqueen = new SnowyQueen();

    public SnowyQueen() {
        super("SnowyQueen", 25, NamespacedKey.fromString("snowyqueen"));
    }

    @Override
    public boolean onUse(Player player) {
        new BukkitRunnable(){
            int ctr = 0;
            @Override
            public void run() {
                for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                    if (p != player) {
                        if (p.getWorld().getWorldType() == WorldType.NORMAL) {
                            if (player.getLocation().distance(p.getLocation()) < radius) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 2));
                                p.damage(2);

                                for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI / 8) {
                                    double x = p.getLocation().getX() + Math.cos(angle) * 1.5;
                                    double y = p.getLocation().getY() + 2;
                                    double z = p.getLocation().getZ() + Math.sin(angle) * 1.5;

                                    p.getLocation().getWorld().spawnParticle(Particle.CRIT_MAGIC, x, y, z, 15, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                }
                if(ctr == 5){
                    cancel();
                } else {
                    ctr += 1;
                }
            }
        }.runTaskTimer(SuperHeroes.getInstance(),0L,20L);
        CooldownTracker.setCooldown(player,snowyqueen,snowyqueen.getCOOLDOWN());
        return true;
    }

    @Override
    public void onJoin(Player player) {
        if(player.getPersistentDataContainer().has(snowyqueen.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "У вас и так этот класс!");
        } else {
            player.sendMessage(ChatColor.RED + "Вы успешно присоединились к " + snowyqueen.getName());
            player.getPersistentDataContainer().set(snowyqueen.getKey(), PersistentDataType.STRING,"HeroClass");
        }
    }

    @Override
    public void onLeave(Player player) {
        if(player.getPersistentDataContainer().has(snowyqueen.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "Вы успешно покинули этот класс");
            player.getPersistentDataContainer().remove(snowyqueen.getKey());
        } else {
            player.sendMessage(ChatColor.RED + "Вы не состоите в этом классе");
        }
    }
}
