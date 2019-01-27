package com.majestic.condenserwand;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class ConfigMgr {
	private static String version, itemname, itemdisplayname, denymsg, wrongcontainermsg, condensemsg, nothingcondensable, nofreespace, recvmsg;
	private static List<String> lore;
	private static boolean sort, wanddelay, sendwrongcontainermsg, itemmetacheck, cooleffects, shiny;
	private static long delay;
	
	public static void reloadConfig() {
		CondenserWand instance = CondenserWand.getInstance();
		instance.saveDefaultConfig();
		instance.reloadConfig();
		FileConfiguration c = instance.getConfig();
		version = c.getString("version");
		itemname = c.getString("item-name");
		recvmsg = ChatColor.translateAlternateColorCodes('&', c.getString("receieve-msg"));
		itemdisplayname = ChatColor.translateAlternateColorCodes('&', c.getString("item-meta.display-name"));
		denymsg = ChatColor.translateAlternateColorCodes('&', c.getString("deny-message"));
		wrongcontainermsg = ChatColor.translateAlternateColorCodes('&', c.getString("wrong-container-message"));
		condensemsg = ChatColor.translateAlternateColorCodes('&', c.getString("condense-msg"));
		nothingcondensable = ChatColor.translateAlternateColorCodes('&', c.getString("nothing-condensable"));
		nofreespace = ChatColor.translateAlternateColorCodes('&', c.getString("nofreespace"));
		lore = CondenserWand.translateColorCodesList(c.getStringList("item-meta.lore"));
		sort = c.getBoolean("sort");
		wanddelay = c.getBoolean("wand-delay");
		cooleffects = c.getBoolean("cooleffects");
		shiny = c.getBoolean("item-meta.shiny");
		sendwrongcontainermsg = c.getBoolean("send-wrong-container-message");
		itemmetacheck = c.getBoolean("enable-item-meta-check");
		delay = c.getLong("delay");
	}

	public static String getVersion() {
		return version;
	}

	public static String getItemName() {
		return itemname;
	}

	public static String getItemDisplayName() {
		return itemdisplayname;
	}

	public static String getDenyMsg() {
		return denymsg;
	}

	public static String getWrongContainerMsg() {
		return wrongcontainermsg;
	}

	public static String getNothingCondensable() {
		return nothingcondensable;
	}

	public static String getNoFreeSpace() {
		return nofreespace;
	}
	
	public static String getCondenseMsg() {
		return condensemsg;
	}

	public static String getRecvMsg() {
		return recvmsg;
	}

	public static List<String> getLore() {
		return lore;
	}

	public static boolean isSort() {
		return sort;
	}

	public static boolean isCoolEffects() {
		return cooleffects;
	}

	public static boolean isWandDelay() {
		return wanddelay;
	}

	public static boolean isSendWrongContainerMsg() {
		return sendwrongcontainermsg;
	}
	
	public static boolean isItemMetaCheck() {
		return itemmetacheck;
	}
	
	public static boolean isShiny() {
		return shiny;
	}

	public static long getWandDelay() {
		return delay;
	}
	
}
