package me.GoodestEnglish.disguise.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements Listener {

	private ItemStack is;

	public ItemBuilder(Material mat) {
		is = new ItemStack(mat);
	}

	public ItemBuilder(ItemStack is) {
		this.is = is;
	}

	public ItemBuilder amount(int amount) {
		is.setAmount(amount);
		return this;
	}

	public ItemBuilder name(String name) {
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		is.setItemMeta(meta);
		return this;
	}

	public ItemBuilder lore(String name) {
		ItemMeta meta = is.getItemMeta();
		List<String> lore = meta.getLore();

		if (lore == null) {
			lore = new ArrayList<>();
		}

		lore.add(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);

		is.setItemMeta(meta);

		return this;
	}

	public ItemBuilder lore(String... lore) {
		List<String> toSet = new ArrayList<>();
		ItemMeta meta = is.getItemMeta();

		for (String string : lore) {
			toSet.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		meta.setLore(toSet);
		is.setItemMeta(meta);

		return this;
	}

	public ItemBuilder lore(List<String> lore) {
		List<String> toSet = new ArrayList<>();
		ItemMeta meta = is.getItemMeta();

		for (String string : lore) {
			toSet.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		meta.setLore(toSet);
		is.setItemMeta(meta);

		return this;
	}

	public ItemBuilder addLoreLine(String line) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (im.hasLore())
			lore = new ArrayList<>(im.getLore());
		lore.add(CC.translate(line));
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder setLoreLine(String line, int pos) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		lore.set(pos, line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public List<String> getLoreLine() {
		ItemMeta im = is.getItemMeta();

		return new ArrayList<>(im.getLore());
	}

	public ItemBuilder durability(int durability) {
		is.setDurability((short) durability);
		return this;
	}

	public ItemBuilder enchantment(Enchantment enchantment, int level) {
		is.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	public ItemBuilder enchantment(Enchantment enchantment) {
		is.addUnsafeEnchantment(enchantment, 1);
		return this;
	}

	public ItemBuilder type(Material material) {
		is.setType(material);
		return this;
	}

	public ItemBuilder clearLore() {
		ItemMeta meta = is.getItemMeta();

		meta.setLore(new ArrayList<>());
		is.setItemMeta(meta);

		return this;
	}

	public ItemBuilder setLeatherArmorColor(Color color) {
		try {
			LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
			im.setColor(color);
			is.setItemMeta(im);
		} catch (ClassCastException expected) {
		}
		return this;
	}

	public ItemBuilder clearEnchantments() {
		for (Enchantment e : is.getEnchantments().keySet()) {
			is.removeEnchantment(e);
		}

		return this;
	}

	public ItemBuilder hideItemFlags() {
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		im.addItemFlags(ItemFlag.HIDE_DESTROYS);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addItemFlag(ItemFlag itemFlag) {
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(itemFlag);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder removeItemFlag(ItemFlag itemFlag) {
		ItemMeta im = is.getItemMeta();
		if (im.hasItemFlag(itemFlag)) {
			im.removeItemFlags(itemFlag);
		}
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addGlow() {
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta im = is.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilder addPotionEffect(PotionEffectType type, int duration, int level) {
		PotionMeta meta = (PotionMeta) is.getItemMeta();
		meta.addCustomEffect(new PotionEffect(type, duration, level), true);
		is.setItemMeta(meta);
		return this;
	}

	public ItemBuilder unbreakable() {
		ItemMeta im = is.getItemMeta();
		im.spigot().setUnbreakable(true);
		is.setItemMeta(im);
		return this;
	}

	public ItemStack build() {
		return is;
	}

	public static ItemStack SPLASH_POTION_INSTANT_HEAL_2() {
		return new ItemBuilder(Material.valueOf("POTION"))
				.durability(16421)
				.amount(1)
				.build();
	}

}