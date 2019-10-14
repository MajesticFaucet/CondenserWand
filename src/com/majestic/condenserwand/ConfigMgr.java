package com.majestic.condenserwand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public final class ConfigMgr {
	private final CondenserWand instance;
	private String version, itemname, itemdisplayname, denymsg, wrongcontainermsg, condensemsg, nothingcondensable, nofreespace, recvmsg;
	private List<String> lore;
	private boolean sort, wanddelay, sendwrongcontainermsg, itemmetacheck, cooleffects, shiny;
	private long delay;
	
	ConfigMgr(final CondenserWand instance) {
		this.instance = instance;
		reload();
	}
	
	public void reload() {
		instance.saveDefaultConfig();
		instance.reloadConfig();
		final FileConfiguration c = instance.getConfig();
		version = c.getString("version");
		itemname = c.getString("item-name");
		recvmsg = ChatColor.translateAlternateColorCodes('&', c.getString("receieve-msg"));
		itemdisplayname = ChatColor.translateAlternateColorCodes('&', c.getString("item-meta.display-name"));
		denymsg = ChatColor.translateAlternateColorCodes('&', c.getString("deny-message"));
		wrongcontainermsg = ChatColor.translateAlternateColorCodes('&', c.getString("wrong-container-message"));
		condensemsg = ChatColor.translateAlternateColorCodes('&', c.getString("condense-msg"));
		nothingcondensable = ChatColor.translateAlternateColorCodes('&', c.getString("nothing-condensable"));
		nofreespace = ChatColor.translateAlternateColorCodes('&', c.getString("nofreespace"));
		final List<String> lore_tmp = c.getStringList("item-meta.lore");
		lore = new ArrayList<String>(lore_tmp.size());
		for(final String lstr : lore_tmp) {
			lore.add(ChatColor.translateAlternateColorCodes('&', lstr));
		}
		sort = c.getBoolean("sort");
		wanddelay = c.getBoolean("wand-delay");
		cooleffects = c.getBoolean("cooleffects");
		shiny = c.getBoolean("item-meta.shiny");
		sendwrongcontainermsg = c.getBoolean("send-wrong-container-message");
		itemmetacheck = c.getBoolean("enable-item-meta-check");
		delay = c.getLong("delay");
	}

	public String getVersion() {
		return version;
	}

	public String getItemName() {
		return itemname;
	}

	public String getItemDisplayName() {
		return itemdisplayname;
	}

	public String getDenyMsg() {
		return denymsg;
	}

	public String getWrongContainerMsg() {
		return wrongcontainermsg;
	}

	public String getNothingCondensable() {
		return nothingcondensable;
	}

	public String getNoFreeSpace() {
		return nofreespace;
	}
	
	public String getCondenseMsg() {
		return condensemsg;
	}

	public String getRecvMsg() {
		return recvmsg;
	}

	public List<String> getLore() {
		return lore;
	}

	public boolean isSort() {
		return sort;
	}

	public boolean isCoolEffects() {
		return cooleffects;
	}

	public boolean isWandDelay() {
		return wanddelay;
	}

	public boolean isSendWrongContainerMsg() {
		return sendwrongcontainermsg;
	}
	
	public boolean isItemMetaCheck() {
		return itemmetacheck;
	}
	
	public boolean isShiny() {
		return shiny;
	}

	public long getWandDelay() {
		return delay;
	}
}
