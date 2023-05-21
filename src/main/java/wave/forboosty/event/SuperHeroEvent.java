package wave.forboosty.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.PlayerClass.impl.Assasin;
import wave.forboosty.PlayerClass.impl.Flesh;
import wave.forboosty.PlayerClass.impl.Ogr;
import wave.forboosty.PlayerClass.impl.SnowyQueen;

public class SuperHeroEvent implements Listener {

    @EventHandler
    public void useAbility(PlayerSwapHandItemsEvent e){
        e.setCancelled(true);
        Player player = e.getPlayer();
        PersistentDataContainer pDC = player.getPersistentDataContainer();
        PersistentDataType pDT = PersistentDataType.STRING;
        if(pDC.has(NamespacedKey.fromString("flesh"),pDT)){
            Flesh.flesh.onUse(player);
        } if(pDC.has(NamespacedKey.fromString("snowyqueen"),pDT)){
            SnowyQueen.snowyqueen.onUse(player);
        } if(pDC.has(NamespacedKey.fromString("ogr"),pDT)){
            Ogr.ogr.onUse(player);
        } if(pDC.has(NamespacedKey.fromString("assasin"),pDT)){
            Assasin.assasin.onUse(player);
        }
    }

    @EventHandler
    public void whenPlayerInStunDisableJump(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if(player.getPersistentDataContainer().has(NamespacedKey.fromString("stun"),PersistentDataType.STRING)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void whenProjectileHitEntity(ProjectileHitEvent e){
        if(e.getEntity().hasMetadata("deathkilling") && e.getHitEntity() instanceof Player){
            Assasin.assasin.abilityOnTarget(Bukkit.getPlayer(e.getEntity().getPersistentDataContainer().get(NamespacedKey.fromString("assasinbility"),PersistentDataType.STRING)), (Player) e.getHitEntity(), 2);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof Snowball) {
            event.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void whenPlayerInStunDisableAttacking(EntityDamageByEntityEvent e){
        if(e.getDamager().getPersistentDataContainer().has(NamespacedKey.fromString("stun"),PersistentDataType.STRING)){
            e.setCancelled(true);
        }
    }
}
