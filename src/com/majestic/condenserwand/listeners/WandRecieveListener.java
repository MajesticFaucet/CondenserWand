package com.majestic.condenserwand.listeners;

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
		player.sendMessage(instance.getConfigMgr().getRecvMsg());
		if(instance.getConfigMgr().isCoolEffects()) {
			player.getWorld().strikeLightningEffect(player.getLocation());
			new AnvilSounds(player).runTaskTimer(instance, 40L, 10L);
		}
	}
	
	private void giveWand(final Player player) {
		final ItemStack wand = new ItemStack(Material.getMaterial(instance.getConfigMgr().getItemName()));
		final ItemMeta im = wand.getItemMeta();
		im.setDisplayName(instance.getConfigMgr().getItemDisplayName());
		im.setLore(instance.getConfigMgr().getLore());
		if(instance.getConfigMgr().isShiny()) {
			im.addEnchant(Enchantment.LUCK, 1, true);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		wand.setItemMeta(im);
		player.getInventory().addItem(wand);
	}
	
	
	final class AnvilSounds extends BukkitRunnable {
		private final Player player;
		private int step;
		
		AnvilSounds(final Player player) {
			this.player = player;
			step = 0;
		}
		
		@Override
		public void run() {
			switch(step) {
			case 0:
				atLocation(5D, 0D);
				break;
			case 1:
				atLocation(0D, 5D);
				break;
			case 2:
				atLocation(-5D, 0D);
				break;
			case 3:
				atLocation(0D, -5D);
				WandRecieveListener.this.giveWand(player);
				this.cancel();
				return;
			}
			step++;
		}
		
		private void atLocation(final double X, final double Z) {
			player.playSound(player.getLocation().add(X, 0D, Z), Sound.ANVIL_LAND, 1F, 1F);
		}
	}
}
