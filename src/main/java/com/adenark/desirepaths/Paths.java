package com.adenark.desirepaths;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Paths implements Listener {
    private int stepCounter;
    private final int STEPS_PER_CHECK;
    private final double PATH_CONVERSION_RATE;

    private void convertToDirt (Block block) {
        switch (block.getType()) {
            case GRASS_BLOCK:
                block.setType(Material.DIRT);
                break;
            case FARMLAND:
                block.setType(Material.DIRT);
                break;
            case PODZOL:
                block.setType(Material.COARSE_DIRT);
                break;
            case DIRT:
                block.setType(Material.COARSE_DIRT);
        }
    }

    private void convertToGravel (Block block) {
        switch (block.getType()) {
            case GRASS_BLOCK:
                block.setType(Material.DIRT);
                break;
            case FARMLAND:
                block.setType(Material.DIRT);
                break;
            case PODZOL:
                block.setType(Material.GRAVEL);
                break;
            case DIRT:
                block.setType(Material.GRAVEL);
        }
    }

    private void convertBlock (Block block) {
        if (DesirePaths.BIOMES_TO_GENERATE_GRAVEL.contains(block.getBiome().toString())) {
            convertToGravel(block);
        } else {
            convertToDirt(block);
        }
    }
    
    public Paths () {
        this.PATH_CONVERSION_RATE = 0.5;
        this.STEPS_PER_CHECK = 10;
        this.stepCounter = 0;
    }
    
    Random pathRandom = new Random();
    
    @EventHandler
    public void onPlayerMoveEvent (PlayerMoveEvent event) {
        if (stepCounter > STEPS_PER_CHECK) {
            stepCounter = 0;
            if (!event.getTo().getBlock().equals(event.getFrom().getBlock()) && !event.getPlayer().isSneaking() && !event.getPlayer().isSwimming()) {
                Block blockUnder = event.getFrom().getBlock().getRelative(BlockFace.DOWN);
                blockUnder.getBiome();
                if (!event.getPlayer().isSprinting() && pathRandom.nextDouble() < PATH_CONVERSION_RATE) {
                    convertBlock(blockUnder);
                } else if (event.getPlayer().isSprinting() && pathRandom.nextDouble() < PATH_CONVERSION_RATE * 1.5) {
                    convertBlock(blockUnder);
                }
            }
        } else {
            stepCounter++;
        }
    }
}
