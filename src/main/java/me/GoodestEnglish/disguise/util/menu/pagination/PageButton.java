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
public class PageButton extends Button {

	private final int mod;
	private final PaginatedMenu menu;

	@Override
	public ItemStack getButtonItem(final Player player) {
		final ItemBuilder item = new ItemBuilder(Material.HOPPER);
		if (this.hasNext(player)) {
            item.name(this.mod > 0 ? ChatColor.GREEN + "下一頁" : ChatColor.RED + "上一頁");
            item.lore(Arrays.asList("", ChatColor.YELLOW + "右鍵點擊", ChatColor.YELLOW + "跳到該頁面", ""));
		} else {
            item.name(ChatColor.GRAY + (this.mod > 0 ? ChatColor.GRAY + "最後一頁" : ChatColor.GRAY + "第一頁"));
            item.lore(Arrays.asList("", ChatColor.YELLOW + "右鍵點擊", ChatColor.YELLOW + "選擇你想要的頁面", ""));
		}

		return item.build();
	}

	@Override
	public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
		if (clickType == ClickType.RIGHT) {
			new ViewAllPagesMenu(this.menu).openMenu(player);
			playNeutral(player);
		} else {
			if (hasNext(player)) {
				this.menu.modPage(player, this.mod);
				Button.playNeutral(player);
			} else {
				Button.playFail(player);
			}
		}
	}

	private boolean hasNext(final Player player) {
		final int pg = this.menu.getPage() + this.mod;
		return pg > 0 && this.menu.getPages(player) >= pg;
	}

}
