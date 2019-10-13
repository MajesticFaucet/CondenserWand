package com.majestic.condenserwand.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.majestic.condenserwand.CondenserWand;
import com.majestic.condenserwand.events.WandReceiveEvent;

public final class WandRecieveListener implements Listener {
	private final CondenserWand instance;
	
	public WandRecieveListener(final CondenserWand instance) {
		this.instance = instance;
	}
	
	@EventHandler()
	public void onWandReceive(final WandReceiveEvent e) {
		final Player player = e.getReceiver();
		if(instance.getConfigMgr().isCoolEffects()) {
			final Location location = player.getLocation();
			player.getWorld().strikeLightningEffect(location);
			new BukkitRunnable() {
				@Override
				public void run() {
					try {
						player.playSound(location.add(5D, 0D, 0D), Sound.ANVIL_LAND, 1F, 1F);
						Thread.sleep(500L);
						player.playSound(location.add(0D, 0D, 5D), Sound.ANVIL_LAND, 1F, 1F);
						Thread.sleep(500L);
						player.playSound(location.add(-5D, 0D, 0D), Sound.ANVIL_LAND, 1F, 1F);
						Thread.sleep(500L);
						player.playSound(location.add(0D, 0D, -5D), Sound.ANVIL_LAND, 1F, 1F);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.runTaskLaterAsynchronously(instance, 40L);
		}
		player.sendMessage(instance.getConfigMgr().getRecvMsg());
		ItemStack wand = new ItemStack(Material.getMaterial(instance.getConfigMgr().getItemName()));
		ItemMeta im = wand.getItemMeta();
		im.setDisplayName(instance.getConfigMgr().getItemDisplayName());
		im.setLore(instance.getConfigMgr().getLore());
		if(instance.getConfigMgr().isShiny()) {
			im.addEnchant(Enchantment.LUCK, 1, true);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		wand.setItemMeta(im);
		player.getInventory().addItem(wand);
	}
}
