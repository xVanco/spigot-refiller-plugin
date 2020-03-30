package github.xvanco.refiller;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class RefillerListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack itemInHand = e.getItemInHand();
        if (itemInHand.getType().isBlock() && itemInHand.getAmount() <= 1) {
            Player p = e.getPlayer();
            switchItemInHand(p, itemInHand);
        }
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent e) {
        final ItemStack brokenItem = e.getBrokenItem();
        final Player p = e.getPlayer();
        switchItemInHand(p, brokenItem);
    }

    private void switchItemInHand(Player p, ItemStack im){
        if (p.getInventory().contains(im.getType())) {
            final ItemStack[] contents = p.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null && im.getType() == contents[i].getType() && im.getData() == contents[i].getData() && p.getInventory().getHeldItemSlot() != i) {
                    p.getInventory().setItemInMainHand(contents[i]);
                    p.getInventory().setItem(i, null);
                    break;
                }
            }
        }
    }
}
