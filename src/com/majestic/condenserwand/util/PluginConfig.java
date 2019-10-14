package com.majestic.condenserwand.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

/**
 * Provides extra functionality to a plugin's ConfigurationSection.
 */
public class PluginConfig implements ConfigurationSection {
	private final ConfigurationSection bconf;
	private final char altColorChar;
	
	/**
	 * Constructs a new read-through plugin config instance.
	 * 
	 * @param baseconfig - base configuration file for this instance.
	 * @param altColorChar - color char to use when using color-translating methods.
	 */
	public PluginConfig(final ConfigurationSection baseconfig, final char altColorChar) {
		this.bconf = baseconfig;
		this.altColorChar = altColorChar;
	}
	
	@Override
	public Set<String> getKeys(final boolean deep) {
		return bconf.getKeys(deep);
	}
	
	@Override
	public Map<String, Object> getValues(final boolean deep) {
		return bconf.getValues(deep);
	}
	
	@Override
	public boolean contains(final String path) {
		return bconf.contains(path);
	}
	
	@Override
	public boolean isSet(final String path) {
		return bconf.isSet(path);
	}
	
	@Override
	public String getCurrentPath() {
		return bconf.getCurrentPath();
	}
	
	@Override
	public String getName() {
		return bconf.getName();
	}
	
	@Override
	public Configuration getRoot() {
		return bconf.getRoot();
	}
	
	@Override
	public ConfigurationSection getParent() {
		return bconf.getParent();
	}
	
	@Override
	public Object get(final String path) {
		return bconf.get(path);
	}
	
	@Override
	public Object get(final String path, final Object def) {
		return bconf.get(path, def);
	}
	
	@Override
	public void set(final String path, final Object value) {
		bconf.set(path, value);
	}
	
	@Override
	public ConfigurationSection createSection(final String path) {
		return bconf.createSection(path);
	}
	
	@Override
	public ConfigurationSection createSection(final String path, final Map<?, ?> map) {
		return bconf.createSection(path, map);
	}
	
	@Override
	public String getString(final String path) {
		return bconf.getString(path);
	}
	
	@Override
	public String getString(final String path, final String def) {
		return bconf.getString(path, def);
	}
	
	/**
	 * Gets the color-code-translated String from the config.
	 * 
	 * @param path - the path in the config.
	 * 
	 * @returns - the color-code-translated String.
	 */
	public String getString_C(final String path) {
		return ChatColor.translateAlternateColorCodes(altColorChar, bconf.getString(path));
	}
	
	/**
	 * Gets the color-code-translated String from the config.
	 * 
	 * WARNING: this does not color-translate the second argument.
	 * 
	 * @param path - the path in the config.
	 * @param def - the already color-translated string.
	 * 
	 * @returns - the color-code-translated String.
	 * 
	 * @see ConfigurationSection#getString(String, String)
	 */
	public String getString_C(final String path, final String def) {
		return ChatColor.translateAlternateColorCodes(altColorChar, bconf.getString(path, def));
	}
	
	@Override
	public boolean isString(final String path) {
		return bconf.isString(path);
	}
	
	@Override
	public int getInt(final String path) {
		return bconf.getInt(path);
	}
	
	@Override
	public int getInt(final String path, final int def) {
		return bconf.getInt(path, def);
	}
	
	@Override
	public boolean isInt(final String path) {
		return bconf.isInt(path);
	}
	
	@Override
	public boolean getBoolean(final String path) {
		return bconf.getBoolean(path);
	}
	
	@Override
	public boolean getBoolean(final String path, final boolean def) {
		return bconf.getBoolean(path, def);
	}
	
	@Override
	public boolean isBoolean(final String path) {
		return bconf.isBoolean(path);
	}
	
	@Override
	public double getDouble(final String path) {
		return bconf.getDouble(path);
	}
	
	@Override
	public double getDouble(final String path, final double def) {
		return bconf.getDouble(path, def);
	}
	
	@Override
	public boolean isDouble(final String path) {
		return bconf.isDouble(path);
	}
	
	@Override
	public long getLong(final String path) {
		return bconf.getLong(path);
	}
	
	@Override
	public long getLong(final String path, final long def) {
		return bconf.getLong(path, def);
	}
	
	@Override
	public boolean isLong(final String path) {
		return bconf.isLong(path);
	}
	
	@Override
	public List<?> getList(final String path) {
		return bconf.getList(path);
	}
	
	@Override
	public List<?> getList(final String path, final List<?> def) {
		return bconf.getList(path, def);
	}
	
	@Override
	public boolean isList(final String path) {
		return bconf.isList(path);
	}
	
	@Override
	public List<String> getStringList(final String path) {
		return bconf.getStringList(path);
	}
	
	/**
	 * Gets a color-code-translated String list.
	 * 
	 * @param path - the path in the config.
	 * 
	 * @returns - the color-code-translated String list.
	 * 
	 * @see ConfigurationSection#getStringList(String)
	 */
	public List<String> getStringList_C(final String path) {
		final List<String> result = bconf.getStringList(path);
		final List<String> fin = new ArrayList<>(result.size());
		for(final String string : result) {
			fin.add(ChatColor.translateAlternateColorCodes(altColorChar, string));
		}
		return fin;
	}
	
	@Override
	public List<Integer> getIntegerList(final String path) {
		return bconf.getIntegerList(path);
	}
	
	@Override
	public List<Boolean> getBooleanList(final String path) {
		return bconf.getBooleanList(path);
	}
	
	@Override
	public List<Double> getDoubleList(final String path) {
		return bconf.getDoubleList(path);
	}
	
	@Override
	public List<Float> getFloatList(final String path) {
		return bconf.getFloatList(path);
	}
	
	@Override
	public List<Long> getLongList(final String path) {
		return bconf.getLongList(path);
	}
	
	@Override
	public List<Byte> getByteList(final String path) {
		return bconf.getByteList(path);
	}
	
	@Override
	public List<Character> getCharacterList(final String path) {
		return bconf.getCharacterList(path);
	}
	
	@Override
	public List<Short> getShortList(final String path) {
		return bconf.getShortList(path);
	}
	
	@Override
	public List<Map<?, ?>> getMapList(final String path) {
		return bconf.getMapList(path);
	}
	
	@Override
	public Vector getVector(final String path) {
		return bconf.getVector(path);
	}
	
	@Override
	public Vector getVector(final String path, final Vector def) {
		return bconf.getVector(path, def);
	}
	
	@Override
	public boolean isVector(final String path) {
		return bconf.isVector(path);
	}
	
	@Override
	public OfflinePlayer getOfflinePlayer(final String path) {
		return bconf.getOfflinePlayer(path);
	}
	
	@Override
	public OfflinePlayer getOfflinePlayer(final String path, final OfflinePlayer def) {
		return bconf.getOfflinePlayer(path, def);
	}
	
	@Override
	public boolean isOfflinePlayer(final String path) {
		return bconf.isOfflinePlayer(path);
	}
	
	@Override
	public ItemStack getItemStack(final String path) {
		return bconf.getItemStack(path);
	}
	
	@Override
	public ItemStack getItemStack(final String path, final ItemStack def) {
		return bconf.getItemStack(path, def);
	}
	
	@Override
	public boolean isItemStack(final String path) {
		return bconf.isItemStack(path);
	}
	
	@Override
	public Color getColor(final String path) {
		return bconf.getColor(path);
	}
	
	@Override
	public Color getColor(final String path, final Color def) {
		return bconf.getColor(path, def);
	}
	
	@Override
	public boolean isColor(final String path) {
		return bconf.isColor(path);
	}
	
	@Override
	public ConfigurationSection getConfigurationSection(final String path) {
		return bconf.getConfigurationSection(path);
	}
	
	@Override
	public boolean isConfigurationSection(final String path) {
		return bconf.isConfigurationSection(path);
	}
	
	@Override
	public ConfigurationSection getDefaultSection() {
		return bconf.getDefaultSection();
	}
	
	@Override
	public void addDefault(final String path, final Object value) {
		bconf.addDefault(path, value);
	}
}
