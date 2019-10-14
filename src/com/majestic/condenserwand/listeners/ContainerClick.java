package com.majestic.condenserwand.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.majestic.condenserwand.CondenserWand;
import com.majestic.condenserwand.ConfigMgr;
import com.majestic.condenserwand.PlayerData;
import com.majestic.condenserwand.events.CondenseEvent;
import com.majestic.condenserwand.exceptions.NoPlayerPermException;
import com.majestic.condenserwand.util.MiscUtil;

public final class ContainerClick implements Listener {
	private final CondenserWand instance;
	private final ConfigMgr configmgr;
	
	public ContainerClick(final CondenserWand instance) {
		this.instance = instance;
		configmgr = instance.getConfigMgr();
	}
	
	/*
	 * Event handlers
	 */
	
	// sorts chests, trapped chests, or enderchest when a player clicks on it with a wand and passes all checks
	@EventHandler(ignoreCancelled=true)
	public void onChestClick(final PlayerInteractEvent e) {
		final BlockState block = e.getClickedBlock().getState();
		final Player player = e.getPlayer();
		if(itemCheck(player)) {
			if(block instanceof Chest) {
				onEvents(player, block.getLocation(), ((Chest) block).getInventory());
			} else if(block.getType().equals(Material.ENDER_CHEST)) {
				onEvents(player, null, player.getEnderChest());
			} else if(configmgr.isSendWrongContainerMsg()) {
				player.sendMessage(configmgr.getWrongContainerMsg());
			}
			e.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onEntityClick(final PlayerInteractEntityEvent e) {
		final Player player = e.getPlayer();
		if(itemCheck(player)) {
			final Entity entity = e.getRightClicked();
			if(entity.getType().equals(EntityType.MINECART_CHEST)) {
				onEvents(player, entity.getLocation(), ((StorageMinecart)entity).getInventory());
			} else if(configmgr.isSendWrongContainerMsg()) {
				player.sendMessage(configmgr.getWrongContainerMsg());
			}
			e.setCancelled(true);
		}
	}
	
	private void onEvents(final Player player, final Location location, final Inventory inventory) {
		try {
			final PlayerData playerdata = instance.getPlayerData(player);
			if(timeCheck(player, playerdata)) return;
			accessCheck(player, location);
			instance.getServer().getPluginManager().callEvent(new CondenseEvent(player, playerdata, inventory));
		} catch(final NoPlayerPermException exception) {
			final String errmsg = exception.getMessage();
			if(errmsg != null) player.sendMessage(errmsg);
		}
	}
	
	/*
	 *  First checks to be performed.
	 */
	
	private boolean itemCheck(final Player p) {
		return p.getItemInHand() != null 
				&& p.getItemInHand().getType().equals(Material.getMaterial(configmgr.getItemName())) 
				&& (!configmgr.isItemMetaCheck() || metaCheck(p.getItemInHand()));
	}
	
	private boolean metaCheck(final ItemStack is) {
		if(!is.hasItemMeta()) return false;
		final ItemMeta im = is.getItemMeta();
		return im.hasDisplayName() 
				&& im.getDisplayName().equals(configmgr.getItemDisplayName()) 
				&& im.hasLore() 
				&& MiscUtil.sequentialComparison(im.getLore(),configmgr.getLore());
	}
	
	/*
	 * Secondary checks to be performed in #onEvents.
	 */
	
	private boolean timeCheck(final Player player, final PlayerData playerdata) {
		if(configmgr.isWandDelay()) {
			if(playerdata.getLastTime() > System.currentTimeMillis() - configmgr.getWandDelay()) {
				return false;
			} else {
				playerdata.setLastTimeToNow();
			}
		}
		return true;
	}
	
	private void accessCheck(final Player player, final Location location) throws NoPlayerPermException {
		if(!player.hasPermission("condenserwand.use")) throw new NoPlayerPermException(configmgr.getDenyMsg());
		
		if(location != null) {
			try {
				// TODO: add custom message for anti-grief deny.
				if(!instance.getWorldGuardInstance().canBuild(player, location)) throw new NoPlayerPermException();
			} catch(final NoClassDefFoundError exception) {
				// just ignore and carry on if WorldGuard isn't loaded
			}			
		}
	}
}
