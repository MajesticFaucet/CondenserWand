package com.majestic.condenserwand;

// Used for tracking various per-player values.
public final class PlayerData {
	private long lasttime;
	private boolean sort;
	
	public PlayerData() {
		this.lasttime = 0L;
		this.sort = true;
	}
	
	// returns the last time a player used their wand
	public long getLastTime() {
		return this.lasttime;
	}
	
	// sets the last time a player used their wand
	public void setLastTimeToNow() {
		this.lasttime = System.currentTimeMillis();
	}
	
	public void setLastTime(final long time) {
		if(time<lasttime) throw new IllegalArgumentException("Time must be after previous time.");
		this.lasttime = time;
	}
	
	// returns player sort preset
	public boolean getSort() {
		return this.sort;
	}
	
	// sets player sort preset
	public void setSort(final boolean value) {
		this.sort = value;
	}
}
