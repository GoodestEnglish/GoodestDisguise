package me.GoodestEnglish.disguise.util.menu;

import me.GoodestEnglish.disguise.util.CC;
import me.GoodestEnglish.disguise.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

	public static Button placeholder(final Material material, int amount, final byte data, String title, String... lore) {
		return (new Button() {
			public ItemStack getButtonItem(Player player) {
				ItemBuilder item = new ItemBuilder(material);
				item.amount(amount);
				item.durability(data);
				item.name(CC.translate(title));
				item.lore(CC.translate(lore));
				return item.build();
			}
		});
	}

	public static Button placeholder(final ItemStack itemStack) {
		return (new Button() {
			public ItemStack getButtonItem(Player player) {
				ItemBuilder item = new ItemBuilder(itemStack.getType());
				item.amount(itemStack.getAmount());
				item.durability(itemStack.getDurability());
				item.name(CC.translate(itemStack.getItemMeta().getDisplayName()));
				item.lore(CC.translate(itemStack.getItemMeta().getLore()));
				return item.build();
			}
		});
	}

	public static void playFail(Player player) {
		player.playSound(player.getLocation(), Sound.DIG_GRASS, 20F, 0.1F);

	}

	public static void playSuccess(Player player) {
		player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20F, 15F);
	}

	public static void playNeutral(Player player) {
		player.playSound(player.getLocation(), Sound.DIG_GRASS, 20F, 1F);
	}

	public abstract ItemStack getButtonItem(Player player);

	public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
	}

	public boolean shouldCancel(Player player, int slot, ClickType clickType) {
		return (true);
	}

	public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
		return (false);
	}

}