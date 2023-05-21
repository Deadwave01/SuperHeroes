package wave.forboosty.PlayerClass.impl;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.SuperHeroes;
import wave.forboosty.util.CooldownTracker;

public class Ogr extends PlayerClass {

    public static final Ogr ogr = new Ogr();

    public Ogr() {
        super("Ogr", 25, NamespacedKey.fromString("ogr"));
    }

    @Override
    public boolean onUse(Player player) {
        if(CooldownTracker.isOnCooldown(player,ogr)){
            player.sendMessage(ChatColor.RED + "Ваша способность находится на перезарядке!");
            return true;
        }
        int lucky = (int) (Math.random()*1+4);
        Location location = player.getLocation();
        float yaw = location.getYaw();
        int startAngle = (int) (yaw+45);
        int endAngle = (int) (yaw+135);
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player target : Bukkit.getServer().getOnlinePlayers()) {
                    if (target != player) {
                        if (target.getWorld().getWorldType() == WorldType.NORMAL) {
                            Location victimloc = target.getLocation();
                            double dx = victimloc.getX() - location.getX();
                            double dy = victimloc.getY() - location.getY();
                            double dz = victimloc.getZ() - location.getZ();
                            double angle = Math.atan2(dz, dx) * 180 / Math.PI;
                            if (angle >= startAngle && angle <= endAngle && victimloc.distance(location) < 6) {
                                float originalSpeed = target.getWalkSpeed();
                                double originalKB = target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue();
                                target.setWalkSpeed(0);
                                target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
                                target.getPersistentDataContainer().set(NamespacedKey.fromString("stun"),PersistentDataType.STRING,"Ability");
                                Bukkit.getScheduler().runTaskLater(SuperHeroes.getInstance(), () -> {
                                    target.getPersistentDataContainer().remove(NamespacedKey.fromString("stun"));
                                    target.setWalkSpeed(originalSpeed);
                                    target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(originalKB);
                                }, 20L * lucky);
                            }
                        }
                    }
                }
                cancel();
            }
        }.runTaskTimer(SuperHeroes.getInstance(),0L,1L);
        CooldownTracker.setCooldown(player,ogr,ogr.getCOOLDOWN());
        return true;
    }

    @Override
    public void onJoin(Player player) {
        if(player.getPersistentDataContainer().has(ogr.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "У вас и так этот класс!");
        } else {
            player.sendMessage(ChatColor.RED + "Вы успешно присоединились к " + ogr.getName());
            player.getPersistentDataContainer().set(ogr.getKey(), PersistentDataType.STRING,"HeroClass");
        }
    }

    @Override
    public void onLeave(Player player) {
        if(player.getPersistentDataContainer().has(ogr.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "Вы успешно покинули этот класс");
            player.getPersistentDataContainer().remove(ogr.getKey());
        } else {
            player.sendMessage(ChatColor.RED + "Вы не состоите в этом классе");
        }
    }
}
