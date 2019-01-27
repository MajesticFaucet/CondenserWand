package com.majestic.condenserwand;

import org.bukkit.inventory.ItemStack;

public class CondensableItem {
	private ItemStack base;
	private ItemStack block;
	private int ratio;
	
	public CondensableItem(ItemStack base, ItemStack block, int ratio) {
		this.base = base;
		this.block = block;
		this.ratio = ratio;
	}

	public ItemStack getBase() {
		return base;
	}

	public ItemStack getBlock() {
		return block;
	}

	public int getRatio() {
		return ratio;
	}
	
}
