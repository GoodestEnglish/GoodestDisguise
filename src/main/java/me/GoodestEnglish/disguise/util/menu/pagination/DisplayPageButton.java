package me.GoodestEnglish.disguise.util.menu.pagination;

import lombok.RequiredArgsConstructor;
import me.GoodestEnglish.disguise.util.CC;
import me.GoodestEnglish.disguise.util.ItemBuilder;
import me.GoodestEnglish.disguise.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class DisplayPageButton extends Button {
    private final PaginatedMenu menu;
    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.NETHER_STAR);
        item.name(CC.PRIMARY + menu.getTitle(player));
        item.addLoreLine(CC.SECONDARY + " 目前頁數&7: " + CC.PRIMARY + menu.getPage());
        item.addLoreLine(CC.SECONDARY + " 最大頁數&7: " + CC.PRIMARY + menu.getPages(player));

        return item.build();
    }
}
