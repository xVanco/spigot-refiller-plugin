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
        Player p = e.getPlayer();
        boolean inMainHand = inMainHand(e.getBlockPlaced().getType(), p);
        ItemStack itemInHand = e.getItemInHand();
        if (itemInHand.getType().isBlock() && itemInHand.getAmount() <= 1) {
            refillItemInHand(p, itemInHand, false, inMainHand);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) throws InterruptedException {
        if (!e.isBlockInHand() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.FARMLAND) {
            Player p = e.getPlayer();
            boolean inMainHand = inMainHand(e.getMaterial(), p);
            ItemStack itemInHand = null;
            if (inMainHand) itemInHand = p.getInventory().getItemInMainHand();
            else itemInHand = p.getInventory().getItemInMainHand();
            if (itemInHand.getAmount() <= 1) {
                refillItemInHand(p, itemInHand, true, inMainHand);
            }
        }
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent e) {
        final ItemStack brokenItem = e.getBrokenItem();
        final Player p = e.getPlayer();
        refillItemInHand(p, brokenItem, false, false);
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        final ItemStack eatenItem = e.getItem();
        final Player p = e.getPlayer();
        boolean inMainHand = inMainHand(eatenItem.getType(), p);
        if (eatenItem.getAmount() <= 1) {
            refillItemInHand(p, eatenItem, false, inMainHand);
        }
    }

    private void refillItemInHand(Player p, ItemStack im, boolean addOne, boolean inMainHand){
        if (p.getInventory().contains(im.getType())) {
            final ItemStack[] contents = p.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                // check if content types are equal (also on different block variants)
                if (contents[i] != null && im.getType() == contents[i].getType() &&
                        im.getData() == contents[i].getData() &&
                        p.getInventory().getHeldItemSlot() != i) {
                    // for playerinteractevent
                    if (addOne) contents[i].setAmount(contents[i].getAmount() + 1);
                    if (inMainHand) p.getInventory().setItemInMainHand(contents[i]);
                    else p.getInventory().setItemInOffHand(contents[i]);
                    p.getInventory().setItem(i, null);
                    break;
                }
            }
        }
    }

    private boolean inMainHand(Material m, Player p) {
        return m == p.getInventory().getItemInMainHand().getType();
    }
}
