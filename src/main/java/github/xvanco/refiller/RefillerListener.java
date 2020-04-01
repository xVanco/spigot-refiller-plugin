package github.xvanco.refiller;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class RefillerListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack itemInHand = e.getItemInHand();
        if (itemInHand.getType().isBlock() && itemInHand.getAmount() <= 1) {
            Player p = e.getPlayer();
            refillItemInHand(p, itemInHand, false);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) throws InterruptedException {
        if (!e.isBlockInHand() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.FARMLAND) {
            Player p = e.getPlayer();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (itemInHand.getAmount() <= 1) {
                refillItemInHand(p, itemInHand, true);
            }
        }
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent e) {
        final ItemStack brokenItem = e.getBrokenItem();
        final Player p = e.getPlayer();
        refillItemInHand(p, brokenItem, false);
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        final ItemStack eatenItem = e.getItem();
        final Player p = e.getPlayer();
        if (eatenItem.getAmount() <= 1) {
            refillItemInHand(p, eatenItem, false);
        }
    }

    private void refillItemInHand(Player p, ItemStack im, boolean addOne){
        if (p.getInventory().contains(im.getType())) {
            final ItemStack[] contents = p.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                // check if content types are equal (also on different block variants)
                if (contents[i] != null && im.getType() == contents[i].getType() &&
                        im.getData() == contents[i].getData() &&
                        p.getInventory().getHeldItemSlot() != i) {
                    // for playerinteractevent
                    if (addOne) contents[i].setAmount(contents[i].getAmount() + 1);
                    p.getInventory().setItemInMainHand(contents[i]);
                    p.getInventory().setItem(i, null);
                    break;
                }
            }
        }
    }
}
