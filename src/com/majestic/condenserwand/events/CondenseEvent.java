package com.majestic.condenserwand.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

import com.majestic.condenserwand.PlayerData;

public final class CondenseEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private final PlayerData data;
	private final Inventory inventory;
	
	public CondenseEvent(final Player player, final PlayerData data, final Inventory inventory) {
		this.player = player;
		this.data = data;
		this.inventory = inventory;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public PlayerData getPlayerData() {
		return data;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
