package com.majestic.condenserwand.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.majestic.condenserwand.CondensableItem;
import com.majestic.condenserwand.CondenserWand;
import com.majestic.condenserwand.ConfigMgr;
import com.majestic.condenserwand.PlayerTracker;
import com.majestic.condenserwand.util.InventoryManipulator;
import com.majestic.condenserwand.util.exceptions.NoPlayerPermException;

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
	
	// sorts chests, trapped chests, or enderchest when a player clicks on it with a wand and passes all checks
	@EventHandler(ignoreCancelled=true)
	public static void onChestClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.hasBlock() && e.hasItem() && e.getItem().getType().equals(Material.getMaterial(ConfigMgr.getItemName()))) {
			if(!ConfigMgr.isItemMetaCheck() || metaCheck(e.getItem())) {
				try {
					accessCheckWithTime(p, e.getClickedBlock().getLocation());
					
					Material clickedblocktype = e.getClickedBlock().getType();
					if(clickedblocktype.equals(Material.CHEST) || clickedblocktype.equals(Material.TRAPPED_CHEST)) {
						Chest chest = (Chest) e.getClickedBlock().getState();
						Inventory inv = chest.getInventory();
						sift(inv, p);
					} else if(clickedblocktype.equals(Material.ENDER_CHEST)) {
						sift(p.getEnderChest(), p);
					} else if(ConfigMgr.isSendWrongContainerMsg()) {
						throw new NoPlayerPermException(ConfigMgr.getWrongContainerMsg());
					}
				} catch(NoPlayerPermException exception) {
					String errmsg = exception.getMessage();
					if(errmsg != null) {
						p.sendMessage(errmsg);
					}
				} finally {
					e.setCancelled(true);
				}
			}
		}
	}
	
	// does everything the above method does except with minecart chests
	@EventHandler(ignoreCancelled=true)
	public static void onMinecartClick(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType().equals(Material.getMaterial(ConfigMgr.getItemName()))) {
			if(!ConfigMgr.isItemMetaCheck() || metaCheck(e.getPlayer().getItemInHand())) {
				try {
					accessCheckWithTime(p, e.getRightClicked().getLocation());
					
					EntityType clickedentitytype = e.getRightClicked().getType();
					if(clickedentitytype.equals(EntityType.MINECART_CHEST)) {
						StorageMinecart sm = (StorageMinecart) e.getRightClicked();
						Inventory inv = sm.getInventory();
						sift(inv, p);
					} else if(ConfigMgr.isSendWrongContainerMsg()) {
						throw new NoPlayerPermException(ConfigMgr.getWrongContainerMsg());
					}
				} catch (NoPlayerPermException exception) {
					String errmsg = exception.getMessage();
					if(errmsg != null) {
						p.sendMessage(errmsg);
					}
				} finally {
					e.setCancelled(true);
				}
			}
		}
	}
	
	// returns true if item has correct meta
	private static boolean metaCheck(ItemStack is) {
		if(is.hasItemMeta()) {
			ItemMeta im = is.getItemMeta();
			if(im.hasDisplayName() && im.getDisplayName().equals(ConfigMgr.getItemDisplayName()) && im.hasLore() && im.getLore().containsAll(ConfigMgr.getLore())) {
				return true;
			}
		}
		return false;
	}
	
	// performs some checks with a timer cooldown
	private static void accessCheckWithTime(Player p, Location l) throws NoPlayerPermException {
		PlayerTracker pt;
		
		if(!PlayerTracker.getPlayers().containsKey(p)) {
			// creates new player tracker object if one doesn't exist
			pt = new PlayerTracker(p);
		} else {
			pt = PlayerTracker.getPlayers().get(p);
		}
		
		if(ConfigMgr.isWandDelay()) {
			if(pt.getLastTime() > System.currentTimeMillis() - ConfigMgr.getWandDelay()) {
				throw new NoPlayerPermException();
			} else {
				pt.setLastTime();
			}
		}
		
		accessCheck(p, l);
		
	}
	
	// performs some checks
	private static void accessCheck(Player p, Location l) throws NoPlayerPermException {
		// anti-grief plugin integration checks
		try {
			if(!CondenserWand.getInstaceWorldGuard().canBuild(p, l)) {
				throw new NoPlayerPermException();
			}
		} catch(NoClassDefFoundError exception) {
			// just ignore and carry on if WorldGuard isn't loaded
		}
		
		
		if(!p.hasPermission("condenserwand.use")) {
			throw new NoPlayerPermException(ConfigMgr.getDenyMsg());
		}
	}
	
	private static void sift(Inventory inv, Player p) {
		// closes anyone's inventory who is viewing the container
		for(HumanEntity h : inv.getViewers()) {
			h.closeInventory();
		}
		
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
		
		// sorts container if preset is true, enabled in config, and the player has permission
		if(ConfigMgr.isSort() && PlayerTracker.getPlayers().get(p).getSort() && p.hasPermission("condenserwand.use.sort")) {
			inv.setContents(InventoryManipulator.sortAll(inv.getContents()));
		}
		
		// sends player condensed message as defined in config or if nothing condensed sends the nothing condensable message
		if(totalcount>0) {
			p.sendMessage(ConfigMgr.getCondenseMsg().replace("%NUM%", Integer.toString(totalcount)));
		} else if(!nospace) {
			p.sendMessage(ConfigMgr.getNothingCondensable());
		}
	}
}
