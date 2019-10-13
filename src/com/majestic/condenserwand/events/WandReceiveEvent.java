package com.majestic.condenserwand.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class WandReceiveEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Player receiver;
	
	public WandReceiveEvent(final Player receiver) {
		this.receiver = receiver;
	}
	
	public Player getReceiver() {
		return receiver;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
