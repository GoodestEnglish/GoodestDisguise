package me.GoodestEnglish.disguise.util.menu;

import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.util.menu.pagination.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ButtonListener implements Listener {

	private GoodestDisguise plugin = GoodestDisguise.INSTANCE;

	@EventHandler(priority = EventPriority.MONITOR)
	public void onButtonPress(final InventoryClickEvent event) {

		final Player player = (Player) event.getWhoClicked();

		final Menu openMenu = Menu.currentlyOpenedMenus.get(player.getUniqueId());

		if (openMenu != null) {

			if (event.getSlot() != event.getRawSlot()) {
				if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(true);
				}
				return;
			}

			if (openMenu.getButtons().containsKey(event.getSlot())) {

				final Button button = openMenu.getButtons().get(event.getSlot());
				final boolean cancel = button.shouldCancel(player, event.getSlot(), event.getClick());

				if (!cancel &&
						(event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(true);

					if (event.getCurrentItem() != null) {
						player.getInventory().addItem(event.getCurrentItem());
					}
				} else {
					event.setCancelled(cancel);
				}

				button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());

				if (Menu.currentlyOpenedMenus.containsKey(player.getUniqueId())) {
					final Menu newMenu = Menu.currentlyOpenedMenus.get(player.getUniqueId());

					if (newMenu == openMenu) {
						final boolean buttonUpdate = button.shouldUpdate(player, event.getSlot(), event.getClick());

						if ((newMenu.isUpdateAfterClick() && buttonUpdate) || buttonUpdate) {
							openMenu.setClosedByMenu(true);
							newMenu.openMenu(player);
						}
					}
				} else if (button.shouldUpdate(player, event.getSlot(), event.getClick())) {
					openMenu.setClosedByMenu(true);
					openMenu.openMenu(player);
				}

				if (event.isCancelled()) {
					Bukkit.getScheduler().runTaskLater(plugin, player::updateInventory, 1L);
				}
			} else {
				if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClose(final InventoryCloseEvent event) {
		final Player player = (Player) event.getPlayer();
		final Menu openMenu = Menu.currentlyOpenedMenus.get(player.getUniqueId());

		if (openMenu != null) {
			openMenu.onClose(player);

			Menu.currentlyOpenedMenus.remove(player.getUniqueId());

			if (openMenu instanceof PaginatedMenu)
				return;
		}

		player.setMetadata("scanglitch", new FixedMetadataValue(plugin, true));
	}


}