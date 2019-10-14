package com.majestic.condenserwand.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.majestic.condenserwand.CondensableItem;
import com.majestic.condenserwand.CondenserWand;
import com.majestic.condenserwand.ConfigMgr;
import com.majestic.condenserwand.PlayerData;
import com.majestic.condenserwand.events.CondenseEvent;
import com.majestic.condenserwand.util.InventoryUtil;

public final class CondenseListener implements Listener {
	private final ConfigMgr configmgr;
	
	public CondenseListener(final CondenserWand instance) {
		this(instance.getConfigMgr());
	}
	
	public CondenseListener(final ConfigMgr configmanager) {
		configmgr = configmanager;
	}
	
	@EventHandler()
	public void onCondense(final CondenseEvent e) {
		final Player player = e.getPlayer();
		final PlayerData playerdata = e.getPlayerData();
		final Inventory inventory = e.getInventory();
		// closes anyone's inventory who is viewing the container
		inventory.getViewers().forEach(HumanEntity::closeInventory);
		
		int totalcount=0;
		boolean nospace = false;
		for(final CondensableItem ci : CondenserWand.C_ITEMS) {
			final ItemStack base = ci.getBase();
			if(InventoryUtil.hasItem(inventory.getContents(), base)) {
				final int amount = InventoryUtil.getItemOccupacy(inventory.getContents(), base);
				if(amount/ci.getRatio()>0) {
					int room = InventoryUtil.getRoomForItem(inventory.getContents(), ci.getBlock());
					if(room != 0 || amount%ci.getRatio() == 0) {
						final ItemStack nis = new ItemStack(ci.getBlock());
						inventory.setContents(InventoryUtil.removeAll(inventory.getContents(), base));
						nis.setAmount(amount/ci.getRatio());
						inventory.addItem(nis);
						if(amount%ci.getRatio()>0) {
							ItemStack ni = new ItemStack(ci.getBase());
							ni.setAmount(amount%ci.getRatio());
							inventory.addItem(ni);
						}
						totalcount += amount-(amount%ci.getRatio());
					} else {
						nospace = true;
						player.sendMessage(configmgr.getNoFreeSpace());
					}
				}
			}
		}
		
		if(configmgr.isSort() && playerdata.getSort() && player.hasPermission("condenserwand.use.sort")) {
			inventory.setContents(InventoryUtil.sortAll(inventory.getContents()));
		}
		
		if(totalcount>0) {
			player.sendMessage(configmgr.getCondenseMsg().replace("%NUM%", Integer.toString(totalcount)));
		} else if(!nospace) {
			player.sendMessage(configmgr.getNothingCondensable());
		}
	}
}
