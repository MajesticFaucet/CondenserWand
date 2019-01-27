package com.majestic.condenserwand.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.majestic.condenserwand.CondensableItem;
import com.majestic.condenserwand.ConfigMgr;
import com.majestic.condenserwand.InventoryManipulator;
import com.majestic.condenserwand.PlayerTracker;

public final class ContainerClick implements Listener {
	
	private static Set<CondensableItem> items = new HashSet<CondensableItem>();
	
	public ContainerClick() {
		items.add(new CondensableItem(new ItemStack(Material.COAL), new ItemStack(Material.COAL_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.GOLD_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.SLIME_BALL), new ItemStack(Material.SLIME_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.SNOW_BALL), new ItemStack(Material.SNOW_BLOCK), 4));
		items.add(new CondensableItem(new ItemStack(Material.EMERALD), new ItemStack(Material.EMERALD_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.INK_SACK, 0, (short)4), new ItemStack(Material.LAPIS_BLOCK), 9));
		items.add(new CondensableItem(new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.GOLD_INGOT), 9));
	}
	
	// TODO: Listen for entity clicks aswell
	
	@EventHandler
	public void onChestClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!e.isCancelled() && e.hasBlock() && e.hasItem() && e.getItem().getType().equals(Material.getMaterial(ConfigMgr.getItemName()))) {
			if(!ConfigMgr.isItemMetaCheck() || (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equals(ConfigMgr.getItemDisplayName()) && e.getItem().getItemMeta().hasLore() && e.getItem().getItemMeta().getLore().containsAll(ConfigMgr.getLore()))) {
				PlayerTracker pt;
				if(!PlayerTracker.getPlayers().containsKey(p)) {
					pt = new PlayerTracker(p);
				} else {
					pt = PlayerTracker.getPlayers().get(p);
				}
				if(!ConfigMgr.isWandDelay() || pt.getLastTime() <= System.currentTimeMillis() - ConfigMgr.getWandDelay()) {
					if(p.hasPermission("condenserwand.use")) {
						Material clickedblocktype = e.getClickedBlock().getType();
						if(clickedblocktype.equals(Material.CHEST) || clickedblocktype.equals(Material.TRAPPED_CHEST)) {
							Chest chest = (Chest) e.getClickedBlock().getState();
							Inventory inv = chest.getInventory();
							sift(inv, p);
						} else if(clickedblocktype.equals(Material.ENDER_CHEST)) {
							sift(p.getEnderChest(), p);
						} else if(ConfigMgr.isSendWrongContainerMsg()) {
							p.sendMessage(ConfigMgr.getWrongContainerMsg());
						}
					} else {
						p.sendMessage(ConfigMgr.getDenyMsg());
					}
					pt.setLastTime();
				}
				e.setCancelled(true);
			}
		}
	}
	
	private void sift(Inventory inv, Player p) {
		int totalcount=0;
		boolean nospace = false;
		for(CondensableItem ci : items) {
			ItemStack base = ci.getBase();
			if(InventoryManipulator.hasItem(inv.getContents(), base)) {
				int amount = InventoryManipulator.getItemOccupacy(inv.getContents(), base);
				if(amount/ci.getRatio()>0) {
					int room = InventoryManipulator.getRoomForItem(inv.getContents(), ci.getBlock());
					if(room != 0 || amount%ci.getRatio() == 0) {
						ItemStack nis = new ItemStack(ci.getBlock());
						inv.setContents(InventoryManipulator.removeAll(inv.getContents(), base));
						nis.setAmount(amount/ci.getRatio());
						inv.addItem(nis);
						if(amount%ci.getRatio()>0) {
							ItemStack ni = new ItemStack(ci.getBase());
							ni.setAmount(amount%ci.getRatio());
							inv.addItem(ni);
						}
						totalcount += amount-(amount%ci.getRatio());
					} else {
						nospace = true;
						p.sendMessage(ConfigMgr.getNoFreeSpace());
					}
				}
			}
		}
		
		if(ConfigMgr.isSort() && PlayerTracker.getPlayers().get(p).getSort() && p.hasPermission("condenserwand.use.sort")) {
			inv.setContents(InventoryManipulator.sortAll(inv.getContents()));
		}
		
		if(totalcount>0) {
			p.sendMessage(ConfigMgr.getCondenseMsg().replace("%NUM%", Integer.toString(totalcount)));
		} else if(!nospace) {
			p.sendMessage(ConfigMgr.getNothingCondensable());
		}
	}
}
