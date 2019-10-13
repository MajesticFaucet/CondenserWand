package com.majestic.condenserwand.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

public final class InventoryUtil {
	
	private InventoryUtil() {}
	
	// consolidates itemstacks
	public static List<ItemStack> consolidate(ItemStack[] iss) {
		List<ItemStack> fin = new ArrayList<ItemStack>();
		for(ItemStack is : iss) {
			if(is != null) {
				fin.add(is);
			}
		}
		return fin;
	}
	
	// sorts all items in stacked form.
	public static ItemStack[] sortAll(ItemStack[] iss) {
		ItemStack[] fin = new ItemStack[iss.length];
		int relpos = 0;
		for(ItemStack m : getItemSets(iss)) {
			for(ItemStack sort : getStacked(iss, m)) {
				fin[relpos] = sort;
				relpos++;
			}
		}
		return fin;
	}
	
	
	// returns itemstacks only if similar to input in stacked form.
	public static ItemStack[] getStacked(ItemStack[] iss, ItemStack m) {
		int occupacy = getItemOccupacy(iss, m);
		int maxstacksize = m.getMaxStackSize();
		ItemStack[] fin = new ItemStack[(int) Math.ceil((float)occupacy/(float)maxstacksize)];
		int left=occupacy;
		for(int a=0; a<fin.length; a++) {
			if(left>maxstacksize) {
				ItemStack nis = new ItemStack(m);
				nis.setAmount(maxstacksize);
				fin[a] = nis;
				left -= maxstacksize;
			} else {
				ItemStack nis = new ItemStack(m);
				nis.setAmount(left);
				fin[a] = nis;
			}
		}
		return fin;
	}
	
	// will only return one itemstack of each type each with an amount of 1 (useful for non-amount specific comparison).
	public static Set<ItemStack> getItemSets(ItemStack[] iss) {
		Set<ItemStack> items = new HashSet<ItemStack>();
		for(ItemStack is : iss) {
			if(is != null) {
				ItemStack nis = new ItemStack(is);
				nis.setAmount(1);
				if(!setContainsItem(items, nis)) {
					items.add(nis);
				}
			}
		}
		return items;
	}
	
	// returns true if itemset hashset contains similar item.
	public static boolean setContainsItem(Set<ItemStack> set, ItemStack m) {
		for(ItemStack is : set) {
			if(is.isSimilar(m)) {
				return true;
			}
		}
		return false;
	}
	
	// returns true if there are empty slots.
	public static boolean hasEmptySlots(ItemStack[] iss) {
		for(ItemStack is : iss) {
			if(is == null) {
				return true;
			}
		}
		return false;
	}
	
	// returns amounts of empty slots.
	public static int getEmptySlots(ItemStack[] iss) {
		int num=0;
		for(ItemStack is : iss) {
			if(is == null) {
				num++;
			}
		}
		return num;
	}
	
	
	// returns the total amounts of all similar itemstacks
	public static int getItemOccupacy(ItemStack[] iss, ItemStack m) {
		int num=0;
		for(ItemStack is : iss) {
			if(is != null && is.isSimilar(m)) {
				num += is.getAmount();
			}
		}
		return num;
	}
	
	// returns true if there is space in a container for an itemstack.
	public static boolean hasRoomForItem(ItemStack[] iss, ItemStack m) {
		for(ItemStack is : iss) {
			if(is == null || (is.isSimilar(m) && is.getAmount()<m.getMaxStackSize())) {
				return true;
			}
		}
		return false;
	}
	
	// returns total room for itemstack in itemstack array based on max stack size.
	public static int getRoomForItem(ItemStack[] iss, ItemStack m) {
		int room=0;
		for(ItemStack is : iss) {
			if(is == null) {
				room += m.getMaxStackSize();
			} else if (is.isSimilar(m) && is.getAmount()<m.getMaxStackSize()) {
				room += m.getMaxStackSize()-is.getAmount();
			}
		}
		return room;
	}
	
	// returns true if array has a similar itemstack.
	public static boolean hasItem(ItemStack[] iss, ItemStack m) {
		if(setContainsItem(getItemSets(iss), m)) return true;
		return false;
	}
	
	// only returns itemstacks that are similar
	public static ItemStack[] getOnly(ItemStack[] iss, ItemStack m) {
		ItemStack[] fin = new ItemStack[iss.length];
		for(int a=0; a<iss.length; a++) {
			if(iss[a] != null && iss[a].isSimilar(m)) {
				fin[a] = iss[a];
			}
		}
		return fin;
	}
	
	// only returns itemstacks that are similar in a consolidated format
	public static List<ItemStack> getOnlyConsolidated(ItemStack[] iss, ItemStack m) {
		List<ItemStack> fin = new ArrayList<ItemStack>();
		for(ItemStack is : iss) {
			if(is != null && is.isSimilar(m)) {
				fin.add(is);
			}
		}
		return fin;
	}
	
	// removes all similar itemstacks from array.
	public static ItemStack[] removeAll(ItemStack[] iss, ItemStack m) {
		ItemStack[] fin = new ItemStack[iss.length];
		for(int a=0; a<iss.length; a++) {
			if(iss[a] != null && !iss[a].isSimilar(m)) {
				fin[a] = iss[a];
			}
		}
		return fin;
	}
}
