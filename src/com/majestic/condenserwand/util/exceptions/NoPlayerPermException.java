package com.majestic.condenserwand.util.exceptions;

import org.bukkit.entity.Player;

// thrown when a player doesn't have permission to do something
public class NoPlayerPermException extends Exception {
	private static final long serialVersionUID = 92422437509336123L;
	private String msg;
	private Player p;
	
	public NoPlayerPermException() {
		this(null, null);
	}
	
	public NoPlayerPermException(String err) {
		this(err, null);
	}
	
	public NoPlayerPermException(String err, Player player) {
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
