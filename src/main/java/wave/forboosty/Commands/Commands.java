package wave.forboosty.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wave.forboosty.PlayerClass.PlayerClass;
import wave.forboosty.PlayerClass.impl.Flesh;
import wave.forboosty.PlayerClass.impl.SnowyQueen;

import java.util.Locale;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length > 1){
            String subCommand = args[0].toLowerCase();
            if(subCommand.equalsIgnoreCase("join")){
                if(args.length == 2){
                    whichClass(args[1]).onJoin(player);
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "Укажите класс!");
                    return true;
                }
            } else if(subCommand.equalsIgnoreCase("leave")){
                if(args.length == 2){
                    whichClass(args[1]).onLeave(player);;
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "Укажите класс!");
                    return true;
                }
            }
        }
        return true;
    }

    private PlayerClass whichClass(String strclass){
        for(PlayerClass p : PlayerClass.playerClasses){
            if(p.getName().equalsIgnoreCase(strclass)){
                return p;
            }
        }
        return null;
    }
}
