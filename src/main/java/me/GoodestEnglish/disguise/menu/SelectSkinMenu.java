package me.GoodestEnglish.disguise.menu;

import lombok.RequiredArgsConstructor;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.cache.RankCache;
import me.GoodestEnglish.disguise.cache.SkinCache;
import me.GoodestEnglish.disguise.util.CC;
import me.GoodestEnglish.disguise.util.ItemBuilder;
import me.GoodestEnglish.disguise.util.menu.Button;
import me.GoodestEnglish.disguise.util.menu.Menu;
import me.GoodestEnglish.disguise.util.menu.pagination.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectSkinMenu extends PaginatedMenu {
    private final String disguiseUsername;
    private final RankCache rankCache;
    public SelectSkinMenu(Menu backMenu, String disguiseUsername, RankCache rankCache) {
        super(backMenu);
        this.disguiseUsername = disguiseUsername;
        this.rankCache = rankCache;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return CC.PRIMARY + "請選擇你想暱稱成為的皮膚...";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        for (SkinCache skinCache : SkinCache.getSkinCaches()) {
            String texture = skinCache.getValue();
            ItemStack head = new ItemBuilder(Material.SKULL_ITEM)
                    .durability(3)
                    .build();
            UUID hashAsId = new UUID(texture.hashCode(), texture.hashCode());
            Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + texture + "\"}]}}}");

            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getButtonItem(Player player) {
                    return new ItemBuilder(head)
                            .name("&e&l" + skinCache.getName())
                            .lore(skinCache.getLore())
                            .addLoreLine("")
                            .addLoreLine("&e&n點擊更改暱稱皮膚")
                            .build();
                }

                @Override
                public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                    player.closeInventory();
                    GoodestDisguise.INSTANCE.getDisguiseManager().disguise(player, disguiseUsername, rankCache, skinCache, false);
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
