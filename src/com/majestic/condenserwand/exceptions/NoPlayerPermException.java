package com.majestic.condenserwand.exceptions;

import org.bukkit.entity.Player;

// thrown when a player doesn't have permission to do something
public class NoPlayerPermException extends Exception {
	private static final long serialVersionUID = 92422437509336123L;
	private final String msg;
	private final Player p;
	
	public NoPlayerPermException() {
		this(null, null);
	}
	
	public NoPlayerPermException(final String err) {
		this(err, null);
	}
	
	public NoPlayerPermException(final String err, final Player player) {
		this.msg = err;
		this.p = player;
	}
	
	@Override
	public String getMessage() {
		return this.msg;
	}
	
	public Player getPlayer() {
		return this.p;
	}
}
