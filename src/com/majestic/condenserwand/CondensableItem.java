package com.majestic.condenserwand;

import org.bukkit.inventory.ItemStack;

// Used to map each blockables' ratios for conversions between regular and blocked forms.
public class CondensableItem {
	private ItemStack base;
	private ItemStack block;
	private int ratio;
	
	public CondensableItem(ItemStack base, ItemStack block, int ratio) {
		// base item
		this.base = base;
		// blocked form of item
		this.block = block;
		// ratio of base block to block form.
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
