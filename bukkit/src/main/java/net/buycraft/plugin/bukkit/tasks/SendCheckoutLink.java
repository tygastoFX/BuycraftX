package net.buycraft.plugin.bukkit.tasks;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.buycraft.plugin.bukkit.BuycraftPlugin;
import net.buycraft.plugin.client.ApiException;
import net.buycraft.plugin.data.responses.CheckoutUrlResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;

@RequiredArgsConstructor
public class SendCheckoutLink implements Runnable {
    @NonNull
    private final BuycraftPlugin plugin;
    @NonNull
    private final int pkgId;
    @NonNull
    private final Player player;

    @Override
    public void run() {
        CheckoutUrlResponse response;
        try {
            response = plugin.getApiClient().getCheckoutUri(player.getName(), pkgId);
        } catch (IOException | ApiException e) {
            player.sendMessage(ChatColor.RED + plugin.getI18n().get("cant_check_out"));
            return;
        }

        player.sendMessage(ChatColor.STRIKETHROUGH + "                                            ");
        player.sendMessage(ChatColor.GREEN + plugin.getI18n().get("to_buy_this_package"));
        player.sendMessage(ChatColor.BLUE + ChatColor.UNDERLINE.toString() + response.getUrl());
        player.sendMessage(ChatColor.STRIKETHROUGH + "                                            ");
    }
}
