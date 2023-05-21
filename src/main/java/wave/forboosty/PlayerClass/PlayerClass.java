package wave.forboosty.PlayerClass;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import wave.forboosty.PlayerClass.impl.Assasin;
import wave.forboosty.PlayerClass.impl.Flesh;
import wave.forboosty.PlayerClass.impl.Ogr;
import wave.forboosty.PlayerClass.impl.SnowyQueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PlayerClass {

    public static List<PlayerClass> playerClasses = new ArrayList<>();

    public static void enabledPlayerClasses(){
        playerClasses.add(Assasin.assasin);
        playerClasses.add(SnowyQueen.snowyqueen);
        playerClasses.add(Ogr.ogr);
        playerClasses.add(Flesh.flesh);
    }


    private final String name;
    private final int COOLDOWN;
    private final NamespacedKey key;

    public NamespacedKey getKey() {
        return key;
    }

    public PlayerClass(String name, int COOLDOWN, NamespacedKey key) {
        this.name = name;
        this.COOLDOWN = COOLDOWN;
        this.key = key;
    }

    public int getCOOLDOWN() {
        return COOLDOWN;
    }

    public String getName() {
        return name;
    }

    public abstract boolean onUse(Player player);
    public abstract void onJoin(Player player);
    public abstract void onLeave(Player player);
}
