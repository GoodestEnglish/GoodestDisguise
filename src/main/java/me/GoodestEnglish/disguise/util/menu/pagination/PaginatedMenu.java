package me.GoodestEnglish.disguise.util.menu.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.GoodestEnglish.disguise.util.menu.Button;
import me.GoodestEnglish.disguise.util.menu.Menu;
import me.GoodestEnglish.disguise.util.menu.buttons.BackButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class PaginatedMenu extends Menu {

	private final Menu backMenu;

	private final List<Integer> availableSlots = Arrays.asList(
			10,11,12,13,14,15,16,
			19,20,21,22,23,24,25,
			28,29,30,31,32,33,34
	);

	private final List<Integer> borderSlots = Arrays.asList(
			0, 1, 2, 3, 4, 5, 6, 7, 8,
			9,                      17,

			27,                     35,
			36,37,38,39   ,41,42,43,44
	);

	@Getter
    private int page = 1;

	{
		setUpdateAfterClick(false);
	}

	@Override
	public String getTitle(Player player) {
		return getPrePaginatedTitle(player) + " (" + page + "/" + getPages(player) + ")";
	}

	@Override
	public int getSize() {
		return 45;
	}

	/**
	 * Changes the page number
	 *
	 * @param player player viewing the inventory
	 * @param mod    delta to modify the page number by
	 */
	public final void modPage(Player player, int mod) {
		page += mod;
		getButtons().clear();
		openMenu(player);
	}

	/**
	 * @param player player viewing the inventory
	 */
	public final int getPages(Player player) {
		int buttonAmount = getAllPagesButtons(player).size();

		if (buttonAmount == 0) {
			return 1;
		}

		return (int) Math.ceil(buttonAmount / (double) getMaxItemsPerPage());
	}

	@Override
	public final Map<Integer, Button> getButtons(Player player) {
		int minIndex = (int) ((double) (page - 1) * getMaxItemsPerPage());
		int maxIndex = (int) ((double) (page) * getMaxItemsPerPage());

		HashMap<Integer, Button> buttons = new HashMap<>();

		int borderIncrease = 0;
		for (Map.Entry<Integer, Button> entry : getAllPagesButtons(player).entrySet()) {
			int index = entry.getKey();

			if (index >= minIndex && index < maxIndex) {
				index = index - ((getMaxItemsPerPage()) * (page - 1)) + 10;
				if (Arrays.asList(17, 24, 32).contains(index)) {
					borderIncrease += 2;
				}
				index += borderIncrease;
				buttons.put(index, entry.getValue());
			}
		}

		for (int i : borderSlots) {
			buttons.put(i, Button.placeholder(Material.STAINED_GLASS_PANE, 1,(byte) 15, ""));
		}

		buttons.put(18, new PageButton(-1,this));
		buttons.put(26, new PageButton(1,this));
		if (backMenu != null) {
			buttons.put(39, new BackButton(backMenu));
		}
		buttons.put(40, getMenuBottomButton() != null ? getMenuBottomButton() : new DisplayPageButton(this));

		return buttons;
	}

	public int getMaxItemsPerPage() {
		return 21;
	}

	/**
	 * @param player player viewing the inventory
	 *
	 * @return title of the inventory before the page number is added
	 */
	public abstract String getPrePaginatedTitle(Player player);

	/**
	 * @param player player viewing the inventory
	 *
	 * @return a map of buttons that will be paginated and spread across pages
	 */
	public abstract Map<Integer, Button> getAllPagesButtons(Player player);

	/**
	 * @return an item logo which display in the bottom of the menu
	 */
	public abstract Button getMenuBottomButton();

}
