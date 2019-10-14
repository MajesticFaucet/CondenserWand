package com.majestic.condenserwand;

import java.util.List;

import com.majestic.condenserwand.util.PluginConfig;

public final class ConfigMgr {
	private final CondenserWand instance;
	private String itemname, itemdisplayname, denymsg, regiondenymsg, wrongcontainermsg, condensemsg, nothingcondensable, nofreespace, recvmsg;
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
		final PluginConfig c = new PluginConfig(instance.getConfig(), '&');
		itemname = c.getString("item-name");
		recvmsg = c.getString_C("receieve-msg");
		itemdisplayname = c.getString_C("item-meta.display-name");
		denymsg = c.getString_C("deny-message");
		regiondenymsg = c.getString_C("region-deny-message");
		wrongcontainermsg = c.getString_C("wrong-container-message");
		condensemsg = c.getString_C("condense-msg");
		nothingcondensable = c.getString_C("nothing-condensable");
		nofreespace = c.getString_C("nofreespace");
		lore = c.getStringList_C("item-meta.lore");
		sort = c.getBoolean("sort");
		wanddelay = c.getBoolean("wand-delay");
		cooleffects = c.getBoolean("cooleffects");
		shiny = c.getBoolean("item-meta.shiny");
		sendwrongcontainermsg = c.getBoolean("send-wrong-container-message");
		itemmetacheck = c.getBoolean("enable-item-meta-check");
		delay = c.getLong("delay");
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
	
	public String getRegionDenyMsg() {
		return regiondenymsg;
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
