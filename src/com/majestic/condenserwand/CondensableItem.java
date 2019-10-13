package com.majestic.condenserwand;

import org.bukkit.inventory.ItemStack;

// Used to map each blockables' ratios for conversions between regular and blocked forms.
public class CondensableItem {
	private ItemStack base;
	private ItemStack block;
	private int ratio;
	
	/**
	 * @param base - base item.
	 * @param block - blocked form of the item.
	 * @param ratio - ratio of base to blocked form.
	 */
	public CondensableItem(ItemStack base, ItemStack block, int ratio) {
		this.base = base;
		this.block = block;
		this.ratio = ratio;
	}
	
	/**
	 * @return the base item.
	 */
	public ItemStack getBase() {
		return base;
	}
	
	/**
	 * @return the blocked form of the item.
	 */
	public ItemStack getBlock() {
		return block;
	}
	
	/**
	 * @return the ratio of the base to blocked form.
	 */
	public int getRatio() {
		return ratio;
	}
	
}
