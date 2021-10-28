package me.GoodestEnglish.disguise.util.menu.buttons;

import lombok.AllArgsConstructor;
import me.GoodestEnglish.disguise.util.menu.Button;
import me.GoodestEnglish.disguise.util.menu.TypeCallback;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@AllArgsConstructor
public class ConfirmationButton extends Button {

	private final boolean confirm;
	private final TypeCallback<Boolean> callback;
	private final boolean closeAfterResponse;

	@Override
	public ItemStack getButtonItem(final Player player) {
		final ItemStack itemStack = new ItemStack(Material.WOOD, 1, this.confirm ? ((byte) 5) : ((byte) 14));
		final ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.setDisplayName(this.confirm ? ChatColor.GREEN + "確定!" : ChatColor.RED + "取消!");
		if (this.confirm) {
			itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "注意: 這個步驟無法還原", ChatColor.GRAY + "請謹慎地選擇!!!"));
		}
		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@Override
	public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
		if (this.confirm) {
			player.playSound(player.getLocation(), Sound.NOTE_PIANO, 20f, 0.1f);
		} else {
			player.playSound(player.getLocation(), Sound.DIG_GRAVEL, 20f, 0.1F);
		}

		if (this.closeAfterResponse) {
			player.closeInventory();
		}

		this.callback.callback(this.confirm);
	}

}