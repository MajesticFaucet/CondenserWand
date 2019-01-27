package com.majestic.condenserwand;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class PlayerTracker {
	private Player p;
	private static Map<Player, PlayerTracker> players = new HashMap<Player, PlayerTracker>();
	private long lasttime;
	private boolean sort;
	
	public PlayerTracker(Player p) {
		this.p = p;
		this.sort = true;
		this.lasttime = 0L;
		players.put(this.p, this);
	}
	
	public static Map<Player, PlayerTracker> getPlayers() {
		return players;
	}
	
	public long getLastTime() {
		return this.lasttime;
	}
	
	public void setLastTime() {
		this.lasttime = System.currentTimeMillis();
	}
	
	public boolean getSort() {
		return this.sort;
	}
	
	public void setSort(boolean value) {
		this.sort = value;
	}
	
	public Player getPlayer() {
		return this.p;
	}
}
