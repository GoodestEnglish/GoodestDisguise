package me.GoodestEnglish.disguise.util.menu.pagination;

import lombok.AllArgsConstructor;
import me.GoodestEnglish.disguise.util.ItemBuilder;
import me.GoodestEnglish.disguise.util.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@AllArgsConstructor
public class JumpToPageButton extends Button {

	private final int page;
	private final PaginatedMenu menu;
	private final boolean current;

	@Override
	public ItemStack getButtonItem(final Player player) {
		final ItemBuilder item = new ItemBuilder(this.current ? Material.ENCHANTED_BOOK : Material.BOOK);
		item.amount(this.page);
        item.durability(this.current ? 5 : 0);

        item.name(ChatColor.YELLOW + "第 " +  this.page + " 頁");

		if (this.current) {
            item.lore(Arrays.asList(
					"",
					ChatColor.GREEN + "目前所在的頁面"
					));
		}

		return item.build();
	}

	@Override
	public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
		this.menu.modPage(player, this.page - this.menu.getPage());
		Button.playNeutral(player);
	}

}
