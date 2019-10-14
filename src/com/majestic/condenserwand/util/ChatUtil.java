package com.majestic.condenserwand.util;

import org.bukkit.ChatColor;

public final class ChatUtil {
	private ChatUtil() {}
	
	public static ChatColor stateColor(final boolean b) {
		if(b) {
			return ChatColor.GREEN;
		} else {
			return ChatColor.RED;
		}
	}
}
