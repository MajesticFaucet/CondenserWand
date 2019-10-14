package com.majestic.condenserwand.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.majestic.condenserwand.CondenserWand;
import com.majestic.condenserwand.PlayerData;
import com.majestic.condenserwand.events.WandReceiveEvent;
import com.majestic.condenserwand.util.ChatUtil;

// Executes plugin commands
public class CondenserCommand implements CommandExecutor {
	private static final String[] help = {
			ChatColor.BLUE + "---CondenserWand---",
			ChatColor.AQUA + "/condenserwand help" + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + "shows this message.",
			ChatColor.AQUA + "/condenserwand sort [on|off/true|false]" + ChatColor.DARK_GRAY + ": "  + ChatColor.GOLD + "toggles/sets container sorting.",
			ChatColor.AQUA + "/condenserwand give [(optional) player]" + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + "gives a player a wand.",
			ChatColor.AQUA + "/condenserwand info" + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + "shows plugin information.",
			ChatColor.AQUA + "/condenserwand version" + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + "shows version information.",
			ChatColor.AQUA + "/condenserwand reload" + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + "reloads the plugin.",
	};
	private final CondenserWand instance;
	private boolean metanag;
	
	public CondenserCommand(final CondenserWand instance) {
		this.instance = instance;
		metanag = false;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, String[] args) {
		if(args.length == 0) {
			args = new String[1];
			args[0] = "help";
		}
		
		boolean isplayer;
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
			isplayer = true;
		} else {
			isplayer = false;
		}
		
		switch(args[0]) {
		case "sort":
			boolean state = false;
			if(!isplayer) {
				sender.sendMessage(ChatColor.DARK_RED + "Only players can do this!");
				break;
			}
			if(!sender.hasPermission("condenserwand.use")) {
				sender.sendMessage(ChatColor.DARK_RED + instance.getConfigMgr().getDenyMsg());
				break;
			}
			if(!sender.hasPermission("condenserwand.use.sort")) {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use the sort function!");
				break;
			}
			if (!instance.getConfigMgr().isSort()) {
				sender.sendMessage(ChatColor.DARK_RED + "Sorting is disabled by the server administrator.");
				break;
			}
			final PlayerData playerdata = instance.getPlayerData(player);
			if(args.length == 2) {
				switch(args[1]) {
				case "on":
					state = true;
					break;
				case "off":
					state = false;
					break;
				case "true":
					state = true;
					break;
				case "false":
					state = false;
					break;
				case "default":
					sender.sendMessage(ChatColor.DARK_RED + "Error! Unknown value \"" + args[1] + "\".");
					return true;
				}
			} else {
				state = !playerdata.getSort();
			}
			playerdata.setSort(state);
			sender.sendMessage(ChatColor.AQUA + "Changed sorting preset to " + ChatUtil.stateColor(state) + Boolean.toString(state));
			break;
		case "version":
			sender.sendMessage(printVersion());
			break;
		case "info":
			sender.sendMessage(printVersion());
			break;
		case "help":
			sender.sendMessage(help);
			break;
		case "reload":
			if(sender.hasPermission("condenserwand.admin")) {
				instance.reload();
				sender.sendMessage("CondenserWand reloaded!");
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform this command!");
			}
			break;
		case "give":
			if(!sender.hasPermission("condenserwand.admin")) {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform this command!");
				break;
			}
			Player receiver = player;
			if(args.length == 2) {
				if(instance.getServer().getPlayer(args[1]) != null) {
					receiver = instance.getServer().getPlayer(args[1]);
					sender.sendMessage(ChatColor.AQUA + "Given a Condenser Wand to " + ChatColor.LIGHT_PURPLE + args[1]);
				} else {
					sender.sendMessage(ChatColor.RED + "Please specify a valid player!");
					break;
				}
			} else if(!isplayer) {
				sender.sendMessage(ChatColor.RED + "Only players can give themselves wands! Please specify a player name.");
				break;
			}
			
			if(!instance.getConfigMgr().isItemMetaCheck() && !metanag) {
				sender.sendMessage(ChatColor.RED + "WARNING: Item meta check is not enabled in the config, players can use normal un-named versions of this item as a wand!");
				metanag = true;
			}
			
			instance.getServer().getPluginManager().callEvent(new WandReceiveEvent(receiver));
			break;
		default:
			sender.sendMessage(ChatColor.RED + "Error, unknown argument. Do /condenserwand help for info on usage!");
			break;
		}
		return true;
	}
	
	private String[] printVersion() {
		return new String[] {
				"CondenserWand by MajesticFaucet.",
				"Running on version: " + instance.getDescription().getVersion(),
		};
	}
}
