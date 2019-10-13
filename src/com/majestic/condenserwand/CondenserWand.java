package com.majestic.condenserwand;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.majestic.condenserwand.listeners.CondenseListener;
import com.majestic.condenserwand.listeners.CondenserCommand;
import com.majestic.condenserwand.listeners.ContainerClick;
import com.majestic.condenserwand.listeners.WandRecieveListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public final class CondenserWand extends JavaPlugin {
	
	public static final Set<CondensableItem> C_ITEMS;
	private ConfigMgr cmgr;
	private HashMap<UUID, PlayerData> pmap;
	
	static {
		final HashSet<CondensableItem> tmp_items = new HashSet<>();
		tmp_items.add(new CondensableItem(new ItemStack(Material.COAL), new ItemStack(Material.COAL_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.REDSTONE), new ItemStack(Material.REDSTONE_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.IRON_INGOT), new ItemStack(Material.IRON_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.GOLD_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.DIAMOND), new ItemStack(Material.DIAMOND_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.SLIME_BALL), new ItemStack(Material.SLIME_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.SNOW_BALL), new ItemStack(Material.SNOW_BLOCK), 4));
		tmp_items.add(new CondensableItem(new ItemStack(Material.EMERALD), new ItemStack(Material.EMERALD_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.INK_SACK, 0, (short)4), new ItemStack(Material.LAPIS_BLOCK), 9));
		tmp_items.add(new CondensableItem(new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.GOLD_INGOT), 9));
		C_ITEMS = Collections.unmodifiableSet(tmp_items);
	}
	
	
	@Override
	public void onEnable() {
		pmap = new HashMap<>();
		cmgr = new ConfigMgr(this);
		getLogger().log(Level.INFO, "Loaded CondenserWand by MajesticFacuet.");
		final PluginManager pl = getServer().getPluginManager();
		pl.registerEvents(new ContainerClick(this), this);
		pl.registerEvents(new CondenseListener(this), this);
		pl.registerEvents(new WandRecieveListener(this), this);
		getCommand("condenserwand").setExecutor(new CondenserCommand(this));
	}
	
	@Override
	public void onDisable() {
		cmgr = null;
		pmap = null;
	}
	
	public void reload() {
		try {
			cmgr.reload();
		} catch(final Exception e) {
			this.getLogger().log(Level.WARNING, "CondenserWand encountered an error while reloading!", e);
		}
		this.getLogger().log(Level.INFO, "CondenserWand reloaded!");
	}
	
	public WorldGuardPlugin getWorldGuardInstance() {
		final Plugin worldguard = this.getServer().getPluginManager().getPlugin("WorldGuard");
		return worldguard != null && worldguard instanceof WorldGuardPlugin 
				? (WorldGuardPlugin) worldguard : null;
	}
	
	public ConfigMgr getConfigMgr() {
		return cmgr;
	}
	
	public HashMap<UUID, PlayerData> getPMAP() {
		return pmap;
	}
	
	public PlayerData getPlayerData(final Player player) {
		return getPlayerData(player.getUniqueId());
	}
	
	public PlayerData getPlayerData(final UUID uuid) {
		return pmap.computeIfAbsent(uuid, (k) -> new PlayerData());
	}
}
