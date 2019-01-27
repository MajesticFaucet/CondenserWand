package com.majestic.condenserwand;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

// Used for tracking various per-player values.
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
	
	// Objects stored in a static hashmap for easy player lookups.
	public static Map<Player, PlayerTracker> getPlayers() {
		return players;
	}
	
	// returns the last time a player used their wand
	public long getLastTime() {
		return this.lasttime;
	}
	
	// sets the last time a player used their wand
	public void setLastTime() {
		this.lasttime = System.currentTimeMillis();
	}
	
	// returns player sort preset
	public boolean getSort() {
		return this.sort;
	}
	
	// sets player sort preset
	public void setSort(boolean value) {
		this.sort = value;
	}
	
	// returns the player associated with this PlayerTracker object
	public Player getPlayer() {
		return this.p;
	}
}
