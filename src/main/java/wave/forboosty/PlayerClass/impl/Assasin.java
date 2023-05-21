package wave.forboosty.PlayerClass.impl;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.SuperHeroes;
import wave.forboosty.util.CooldownTracker;

public class Assasin extends PlayerClass {

    public final static Assasin assasin = new Assasin();

    public Assasin() {
        super("Assasin", 25, NamespacedKey.fromString("assasin"));
    }

    @Override
    public boolean onUse(Player player) {
        if(CooldownTracker.isOnCooldown(player,assasin)){
            player.sendMessage(ChatColor.RED + "Ваша способность находится на перезарядке!");
            return true;
        }
        Vector direction = player.getEyeLocation().getDirection();
        Snowball projectile = player.launchProjectile(Snowball.class);
        projectile.setVisibleByDefault(false);
        projectile.setSilent(true);
        projectile.setShooter(player);
        projectile.setVelocity(direction.multiply(4));
        projectile.setMetadata("deathkilling",new FixedMetadataValue(SuperHeroes.getInstance(),"Ability"));
        projectile.getPersistentDataContainer().set(NamespacedKey.fromString("assasinbility"),PersistentDataType.STRING,player.getName());
        CooldownTracker.setCooldown(player,assasin,assasin.getCOOLDOWN());
        return true;
    }

    public void abilityOnTarget(Player player,Player target,int distance){
        Location targetLocation =  target.getLocation();
        Vector targetDirection = targetLocation.getDirection();
        Location behindLocationTarget;
        World world = player.getWorld();
        double dx = targetLocation.getX() - (targetDirection .getX() * distance);
        double dy = targetLocation.getY();
        double dz = targetLocation.getZ() - (targetDirection .getZ() * distance);
        behindLocationTarget = new Location(world,dx,dy,dz);
        player.teleport(behindLocationTarget);
        spawnParticles(Particle.CRIT,100,target,2);
        target.damage(0.0001);
        target.playSound(targetLocation,Sound.BLOCK_ANVIL_PLACE,2F,2F);
        target.setHealth(target.getHealth()/2);
    }

    private void spawnParticles(Particle particleType,int particleCount,Player target,int radius){
        Location targetLocation =  target.getLocation();
        Vector targetDirection = targetLocation.getDirection();
        double angleStep = Math.PI / (particleCount - 1);
        for (int i = 0; i < particleCount / 2; i++) {
            double angle = i * angleStep;
            double x = targetLocation.getX() + (radius * Math.cos(angle) * targetDirection.getX());
            double y = targetLocation.getY() + (radius * Math.sin(angle)) + target.getEyeHeight();
            double z = targetLocation.getZ() + (radius * Math.cos(angle) * targetDirection.getZ());
            targetLocation.getWorld().spawnParticle(particleType, x, y, z, 1);
        }
    }

    @Override
    public void onJoin(Player player) {
        if(player.getPersistentDataContainer().has(assasin.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "У вас и так этот класс!");
        } else {
            player.sendMessage(ChatColor.RED + "Вы успешно присоединились к " + assasin.getName());
            player.getPersistentDataContainer().set(assasin.getKey(), PersistentDataType.STRING,"HeroClass");
        }
    }

    @Override
    public void onLeave(Player player) {
        if(player.getPersistentDataContainer().has(assasin.getKey(),PersistentDataType.STRING)){
            player.sendMessage(ChatColor.RED + "Вы успешно покинули этот класс");
            player.getPersistentDataContainer().remove(assasin.getKey());
        } else {
            player.sendMessage(ChatColor.RED + "Вы не состоите в этом классе");
        }
    }

}
