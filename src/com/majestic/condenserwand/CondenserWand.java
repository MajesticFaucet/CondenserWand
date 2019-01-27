package com.majestic.condenserwand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.majestic.condenserwand.listeners.CondenserCommand;
import com.majestic.condenserwand.listeners.ContainerClick;

public final class CondenserWand extends JavaPlugin {
	private static CondenserWand pl;
	private static Server s;
	private static PluginManager pm;
	private static ConsoleCommandSender cs;
	
	@Override
	public void onEnable() {
		pl = this;
		s = this.getServer();
		pm = s.getPluginManager();
		cs = this.getServer().getConsoleSender();
		reload();
		cs.sendMessage("Custom plugins by MajesticFacuet.");
		pm.registerEvents(new ContainerClick(), this);
		this.getCommand("condenserwand").setExecutor(new CondenserCommand());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static CondenserWand getInstance() {
		return pl;
	}
	
	public static void reload() {
		ConfigMgr.reloadConfig();
		if(!ConfigMgr.getVersion().equals("1.0.0")) {
			cs.sendMessage("WARNING: Config version doesn't match the current plugin version! Backup your plugin config and delete the one in the plugin directory, then copy all desired values over. Ignoring this can cause potential crashes or glitches in the future.");
		}
	}
	
	public static List<String> translateColorCodesList(List<String> strings) {
		List<String> fin = new ArrayList<String>();
		for(String string : strings) {
			fin.add(ChatColor.translateAlternateColorCodes('&', string));
		}
		return fin;
	}
	
	public static void giveWand(Player p, boolean cooleffects) {
		if(cooleffects) {
			p.getWorld().strikeLightningEffect(p.getLocation());
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						p.playSound(p.getLocation().add(5, 0, 0), Sound.ANVIL_LAND, 1F, 1F);
						Thread.sleep(500L);
						p.playSound(p.getLocation().add(0, 0, 5), Sound.ANVIL_LAND, 1F, 1F);
						Thread.sleep(500L);
						p.playSound(p.getLocation().add(-5, 0, 0), Sound.ANVIL_LAND, 1F, 1F);
						Thread.sleep(500L);
						p.playSound(p.getLocation().add(0, 0, -5), Sound.ANVIL_LAND, 1F, 1F);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.runTaskLaterAsynchronously(pl, 40L);
		}
		p.sendMessage(ConfigMgr.getRecvMsg());
		ItemStack wand = new ItemStack(Material.getMaterial(ConfigMgr.getItemName()));
		ItemMeta im = wand.getItemMeta();
		im.setDisplayName(ConfigMgr.getItemDisplayName());
		im.setLore(ConfigMgr.getLore());
		if(ConfigMgr.isShiny()) {
			im.addEnchant(Enchantment.LUCK, 1, true);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		wand.setItemMeta(im);
		p.getInventory().addItem(wand);
	}
	
	public static ChatColor stateColor(boolean b) {
		if(b) {
			return ChatColor.GREEN;
		} else {
			return ChatColor.RED;
		}
	}
	
	public static boolean isValidPlayer(String ps) {
		for(Player p : s.getOnlinePlayers()) {
			if(p.getName().equals(ps)) {
				return true;
			}
		}
		return false;
	}
	
	public static Player getPlayer(String ps) {
		for(Player p : s.getOnlinePlayers()) {
			if(p.getName().equals(ps)) {
				return p;
			}
		}
		return null;
	}
}
