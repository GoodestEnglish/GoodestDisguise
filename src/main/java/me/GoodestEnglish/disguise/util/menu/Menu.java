package me.GoodestEnglish.disguise.util.menu;

import lombok.Getter;
import lombok.Setter;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.util.CC;
import me.GoodestEnglish.disguise.util.menu.task.MenuUpdateTask;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public abstract class Menu {

	public static void init() {
		//Menu
		Bukkit.getScheduler().runTaskTimerAsynchronously(GoodestDisguise.INSTANCE, new MenuUpdateTask(), 20L, 20L);
		GoodestDisguise.INSTANCE.getServer().getPluginManager().registerEvents(new ButtonListener(), GoodestDisguise.INSTANCE);
	}

	public static Map<UUID, Menu> currentlyOpenedMenus = new HashMap<>();
	@Getter
	private Map<Integer, Button> buttons = new HashMap<>();
	private boolean autoUpdate = this.isAutoUpdate();
	private boolean updateAfterClick = true;
	private boolean closedByMenu = false;
	private boolean placeholder = false;
	private Button placeholderButton = Button.placeholder(Material.STAINED_GLASS_PANE, 1, (byte) 15, " ", " ");

	private ItemStack createItemStack(final Player player, final Button button) {
		return button.getButtonItem(player);
	}

	public void openMenu(final Player player) {
		this.openMenu(player, false);
	}

	public void openMenu(final Player player, boolean update) {
		this.buttons = this.getButtons(player);

		final Menu previousMenu = Menu.currentlyOpenedMenus.get(player.getUniqueId());
		Inventory inventory = null;
		final int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
		String title = CC.translate(this.getTitle(player));

		if (title.length() > 32) {
			title = title.substring(0, 32);
		}

		if (player.getOpenInventory() != null) {
			if (previousMenu == null) {
				player.closeInventory();
			} else {
				final int previousSize = player.getOpenInventory().getTopInventory().getSize();

				if (previousSize == size) {
					inventory = player.getOpenInventory().getTopInventory();
					updateTitle(player, title);
					update = true;
				} else {
					previousMenu.setClosedByMenu(true);
					player.closeInventory();
				}
			}
		}

		/*if (player.getOpenInventory() != null) {
			if (previousMenu == null) {
				player.closeInventory();
			} else {
				final int previousSize = player.getOpenInventory().getTopInventory().getSize();

				if (previousSize == size && player.getOpenInventory().getTopInventory().getTitle().equals(title)) {
					inventory = player.getOpenInventory().getTopInventory();
					update = true;
				} else {
					previousMenu.setClosedByMenu(true);
					player.closeInventory();
				}
			}
		}*/

		if (inventory == null) {
			inventory = Bukkit.createInventory(player, size, title);
		}

		inventory.setContents(new ItemStack[inventory.getSize()]);

		currentlyOpenedMenus.put(player.getUniqueId(), this);

		for (final Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
			inventory.setItem(buttonEntry.getKey(), createItemStack(player, buttonEntry.getValue()));
		}

		if (this.isPlaceholder()) {
			for (int index = 0; index < size; index++) {
				if (this.buttons.get(index) == null) {
					this.buttons.put(index, this.placeholderButton);
					inventory.setItem(index, this.placeholderButton.getButtonItem(player));
				}
			}
		}

		if (update) {
			player.updateInventory();
		} else {
			player.openInventory(inventory);
		}

		this.onOpen(player);
		this.setClosedByMenu(false);
	}

	public int size(final Map<Integer, Button> buttons) {
		int highest = 0;

		for (final int buttonValue : buttons.keySet()) {
			if (buttonValue > highest) {
				highest = buttonValue;
			}
		}

		return (int) (Math.ceil((highest + 1) / 9D) * 9D);
	}

	public int getSlot(final int x, final int y) {
		return ((9 * y) + x);
	}

	public int getSize() {
		return -1;
	}

	public boolean isAutoUpdate() {
	    return false;
    }

	public abstract String getTitle(Player player);

	public abstract Map<Integer, Button> getButtons(Player player);

	public void onOpen(final Player player) {
	}

	public void onClose(final Player player) {
	}

	public void updateTitle(Player p, String title) {
		EntityPlayer ep = ((CraftPlayer)p).getHandle();
		PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(ep.activeContainer.windowId, "minecraft:chest", new ChatMessage(title), p.getOpenInventory().getTopInventory().getSize());
		ep.playerConnection.sendPacket(packet);
		ep.updateInventory(ep.activeContainer);
	}

}