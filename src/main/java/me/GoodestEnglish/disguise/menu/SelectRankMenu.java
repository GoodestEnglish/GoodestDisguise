package me.GoodestEnglish.disguise.menu;

import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.cache.RankCache;
import me.GoodestEnglish.disguise.util.BasicConfigFile;
import me.GoodestEnglish.disguise.util.CC;
import me.GoodestEnglish.disguise.util.ItemBuilder;
import me.GoodestEnglish.disguise.util.menu.Button;
import me.GoodestEnglish.disguise.util.menu.Menu;
import me.GoodestEnglish.disguise.util.menu.pagination.PaginatedMenu;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SelectRankMenu extends PaginatedMenu {
    private final String disguiseUsername;
    public SelectRankMenu(String disguiseUsername) {
        super(null);
        this.disguiseUsername = disguiseUsername;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.PRIMARY + "請選擇你想暱稱成為的職階...";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        for (RankCache cache : RankCache.getRankCaches()) {
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                            .setLeatherArmorColor(Color.fromRGB(Integer.parseInt(cache.getColor(), 16)))
                            .name("&c" + cache.getName())
                            .lore(
                                    "",
                                    "&7暱稱職階只是作為顯示用途",
                                    "&7並不會更改遊戲體驗",
                                    "",
                                    "&7聊天預覽:",
                                    GoodestDisguise.INSTANCE.getConfigFile().getString("CHAT_FORMAT")
                                            .replaceAll("<rank>", cache.getDisplay())
                                            .replaceAll("<name>", player.getName())
                                            .replaceAll("<message>", "這是聊天預覽, 可以讓你預覽使用這個職階之後的聊天格式"),
                                    "",
                                    "&e&n點擊更改暱稱職階"
                            )
                            .build();
                }
                @Override
                public void clicked(final Player player, final int slot, final ClickType clickType, final int hotbarButton) {
                    new SelectSkinMenu(SelectRankMenu.this, disguiseUsername, cache).openMenu(player);
                }
            });
        }

        return buttons;
    }

    @Override
    public Button getMenuBottomButton() {
        return new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.BOOK)
                        .name("&6&l暱稱有什麼用途?")
                        .lore(
                                "&e暱稱能夠隱藏自己的身份",
                                "&e讓其他玩家難以察覺你的真正身份",
                                "&e遊玩體驗更佳"
                        )
                        .build();
            }
        };
    }
}
