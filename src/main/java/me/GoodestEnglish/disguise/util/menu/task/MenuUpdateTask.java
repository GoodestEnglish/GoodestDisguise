package me.GoodestEnglish.disguise.util.menu.task;

import me.GoodestEnglish.disguise.util.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class MenuUpdateTask implements Runnable {

	@Override
	public void run() {
		for (Map.Entry<UUID, Menu> menuMap : Menu.currentlyOpenedMenus.entrySet()) {
			UUID uuid = menuMap.getKey();
			Menu menu = menuMap.getValue();

			if (uuid == null || menu == null) {
				Menu.currentlyOpenedMenus.remove(uuid, menu);
				continue;
			}

			final Player player = Bukkit.getPlayer(uuid);
			if (player == null) return;
			if (menu.isAutoUpdate()) {
				menu.openMenu(player, true);
			}
		}
		/*Menu.currentlyOpenedMenus.forEach((uuid, menu) -> {
			final Player player = Bukkit.getPlayer(uuid);
			if (player == null) return;
			if (menu.isAutoUpdate()) {
				menu.openMenu(player, true);
			}
		});*/
	}

}
