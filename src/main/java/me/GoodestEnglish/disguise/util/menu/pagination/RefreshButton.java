package me.GoodestEnglish.disguise.util.menu.pagination;

import lombok.AllArgsConstructor;
import me.GoodestEnglish.disguise.util.ItemBuilder;
import me.GoodestEnglish.disguise.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class RefreshButton extends Button {

    private final PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        final ItemBuilder item = new ItemBuilder(Material.INK_SACK).durability(10)
                .durability(5)
                .name("&a重新加載頁面")
                .lore(" ")
                .lore("&7點擊以刷新頁面");
        return item.build();
    }

    @Override
    public void clicked(final Player player, final int i, final ClickType clickType, final int hb) {
        menu.openMenu(player);
        Button.playNeutral(player);
    }
}
